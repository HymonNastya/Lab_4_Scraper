package lab3.cpp_lab4_javafx_app;

import javafx.application.Platform;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ScraperControllerTest {

    private ScraperController controller;
    @BeforeAll
    static void initToolkit() {
        // Ініціалізація JavaFX Toolkit
        Platform.startup(() -> {});
    }

    @BeforeEach
    void setUp() {
        controller = new ScraperController();

        // Імітація JavaFX-компонентів
        controller.urlField = new TextField();
        controller.threadField = new TextField();
        controller.urlTable = new TableView<>();
        controller.urlColumn = new TableColumn<>();
        controller.mainThreadStatusLabel = new Label();
        controller.threadPanelContainer = new VBox();
        controller.totalTimeLabel = new Label();
    }


    @Test
    void testOnAddUrl() {
        Platform.runLater(() -> {
            // Встановлюємо URL у текстове поле
            controller.urlField.setText("https://example.com");

            // Викликаємо метод додавання URL
            controller.onAddUrl();
        });

        // Очікуємо завершення подій JavaFX
        waitForRunLater();

        // Перевіряємо, чи URL додано в таблицю
        assertEquals(1, controller.urlTable.getItems().size(), "URL повинен бути доданий у таблицю");
        assertEquals("https://example.com", controller.urlTable.getItems().get(0), "URL повинен збігатися");
    }

    private void waitForRunLater() {
        try {
            // Даємо JavaFX Thread час завершити обробку подій
            Thread.sleep(500); // Можна збільшити час, якщо операції займають більше часу
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testOnStartScraping() {
        // Ініціалізація значень для тестування
        controller.urlField.setText("https://example.com");
        controller.onAddUrl(); // Додаємо URL до списку

        controller.threadField.setText("3"); // Встановлюємо кількість потоків

        // Викликаємо метод для запуску скрейпінгу
        controller.onStartScraping();

        // Перевіряємо статус
        assertTrue(controller.running, "Скрейпінг повинен бути запущений");
        assertEquals("Main Thread Status: Running", controller.mainThreadStatusLabel.getText(), "Статус основного потоку повинен бути 'Running'");
    }

    @Test
    void testOnStopScraping() {
        // Стартуємо скрейпінг
        controller.running = true;

        // Зупиняємо скрейпінг
        controller.onStopScraping();

        // Перевіряємо, чи статус змінено
        assertEquals("Main Thread Status: Stopped", controller.mainThreadStatusLabel.getText(), "Статус основного потоку повинен бути 'Stopped'");
        assertFalse(controller.running, "Флаг 'running' повинен бути встановлений в false");
    }

    @Test
    void testUpdateUI() {
        // Створюємо тестовий результат скрейпінгу
        ScraperResult result = new ScraperResult(
                "https://example.com",
                "Thread-1",
                "Success",
                2048,
                500
        );

        // Викликаємо метод оновлення UI
        controller.updateUI(result);

        // Перевіряємо, чи панель оновлена з правильними даними
        assertEquals(1, controller.threadPanelContainer.getChildren().size(), "Контейнер панелей повинен містити одну панель");
        VBox panel = (VBox) controller.threadPanelContainer.getChildren().get(0);

        // Перевіряємо, чи панель містить правильну інформацію
        Label threadLabel = (Label) panel.getChildren().get(0);
        assertEquals("Thread: Thread-1", threadLabel.getText(), "Ім'я потоку повинно збігатися");

        Label urlLabel = (Label) panel.getChildren().get(1);
        assertEquals("URL: https://example.com", urlLabel.getText(), "URL повинен збігатися");

        Label statusLabel = (Label) panel.getChildren().get(2);
        assertEquals("Status: Success", statusLabel.getText(), "Статус повинен збігатися");

        Label dataSizeLabel = (Label) panel.getChildren().get(3);
        assertEquals("Data Size: 2048", dataSizeLabel.getText(), "Розмір даних повинен збігатися");

        Label executionTimeLabel = (Label) panel.getChildren().get(4);
        assertEquals("Execution Time: 500 ms", executionTimeLabel.getText(), "Час виконання повинен збігатися");
    }
}
