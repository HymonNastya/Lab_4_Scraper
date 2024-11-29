package lab3.cpp_lab4_javafx_app;

import javafx.application.Platform;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.util.concurrent.*;

public class ScraperController {

    @FXML
    TableView<String> urlTable;
    @FXML
    TableColumn<String, String> urlColumn;
    @FXML
    TextField urlField;
    @FXML
    TextField threadField;
    @FXML
    VBox threadPanelContainer;
    @FXML
    Label mainThreadStatusLabel;
    @FXML
    Label totalTimeLabel;

    private final BlockingQueue<String> urlQueue = new LinkedBlockingQueue<>();
    private ExecutorService threadPool;
    private CompletionService<ScraperResult> completionService;
    volatile boolean running = false;

    @FXML
    private void initialize() {
        // Initialize the URL table
        urlColumn.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue()));
    }

    @FXML
    void onAddUrl() {
        String url = urlField.getText().trim();
        if (!url.isEmpty()) {
            urlQueue.offer(url);
            Platform.runLater(() -> urlTable.getItems().add(url));
            urlField.clear();
        }
    }

    @FXML
    void onStartScraping() {
        if (running) return;
        running = true;
        mainThreadStatusLabel.setText("Main Thread Status: Running");
        threadPanelContainer.getChildren().clear();

        urlQueue.offer("https://www.wikipedia.org");
        Platform.runLater(() -> urlTable.getItems().add("https://www.wikipedia.org"));
        urlQueue.offer("https://www.google.com");
        Platform.runLater(() -> urlTable.getItems().add("https://www.google.com"));
        urlQueue.offer("https://www.github.com");
        Platform.runLater(() -> urlTable.getItems().add("https://www.github.com"));
        urlQueue.offer("https://www.stackoverflow.com");
        Platform.runLater(() -> urlTable.getItems().add("https://www.stackoverflow.com"));
        urlQueue.offer("https://www.oracle.com");
        Platform.runLater(() -> urlTable.getItems().add("https://www.oracle.com"));
        urlQueue.offer("https://www.reddit.com");
        Platform.runLater(() -> urlTable.getItems().add("https://www.reddit.com"));
        urlQueue.offer("https://www.medium.com");
        Platform.runLater(() -> urlTable.getItems().add("https://www.medium.com"));
        urlQueue.offer("https://www.bbc.com");
        Platform.runLater(() -> urlTable.getItems().add("https://www.bbc.com"));
        urlQueue.offer("https://www.cnn.com");
        Platform.runLater(() -> urlTable.getItems().add("https://www.cnn.com"));
        urlQueue.offer("https://www.w3schools.com");
        Platform.runLater(() -> urlTable.getItems().add("https://www.w3schools.com"));

        int threadCount;
        try {
            threadCount = Integer.parseInt(threadField.getText().trim());
        } catch (NumberFormatException e) {
            threadCount = 5; // Default value
        }

        threadPool = Executors.newFixedThreadPool(threadCount);
        completionService = new ExecutorCompletionService<>(threadPool);
        long startTime = System.currentTimeMillis();

        for (int i = 0; i < threadCount; i++) {
            threadPool.submit(() -> {
                while (running) {
                    try {
                        String url = urlQueue.poll(1, TimeUnit.SECONDS);
                        if (url != null) {
                            ScraperTask task = new ScraperTask(url);
                            completionService.submit(task);
                        } else {

                            break;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        threadPool.submit(() -> {
            try {
                while (running || !urlQueue.isEmpty()) {
                    Future<ScraperResult> future = completionService.poll(5, TimeUnit.SECONDS);
                    if (future != null) {
                        ScraperResult result = future.get();
                        Platform.runLater(() -> updateUI(result));
                    }
                    else {
                        running = false;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                threadPool.shutdown();

                long totalTime = System.currentTimeMillis() - startTime;
                running = false;
                Platform.runLater(() -> {
                    mainThreadStatusLabel.setText("Main Thread Status: Completed");
                    totalTimeLabel.setText(totalTime + " ms");
                });
            }
        });
    }


    @FXML
    void onStopScraping() {
        running = false;
        if (threadPool != null) {
            threadPool.shutdownNow();
        }
        mainThreadStatusLabel.setText("Main Thread Status: Stopped");
    }

    void updateUI(ScraperResult result) {
        VBox threadPanel = new VBox();
        threadPanel.getChildren().add(new Label("Thread: " + result.getThreadName()));
        threadPanel.getChildren().add(new Label("URL: " + result.getUrl()));
        threadPanel.getChildren().add(new Label("Status: " + result.getStatus()));
        threadPanel.getChildren().add(new Label("Data Size: " + result.getDataSize()));
        threadPanel.getChildren().add(new Label("Execution Time: " + result.getExecutionTime() + " ms"));
        threadPanel.setStyle("-fx-border-color: black; -fx-padding: 5; -fx-background-color: #f0f0f0; -fx-margin: 5;");
        threadPanelContainer.getChildren().add(threadPanel);
    }
}
