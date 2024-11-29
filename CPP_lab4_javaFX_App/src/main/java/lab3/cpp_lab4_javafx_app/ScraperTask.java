package lab3.cpp_lab4_javafx_app;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Callable;

public class ScraperTask implements Callable<ScraperResult> {

    private final String url;

    public ScraperTask(String url) {
        this.url = url;
    }

    @Override
    public ScraperResult call() {
        long startTime = System.currentTimeMillis();
        System.out.println(Thread.currentThread().getName() + " started.");
        String threadName = Thread.currentThread().getName();

        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                StringBuilder content = new StringBuilder();
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        content.append(line);
                    }
                }

                long executionTime = System.currentTimeMillis() - startTime;
                return new ScraperResult(url, threadName, "Success", content.length(), executionTime);
            } else {
                return new ScraperResult(url, threadName, "Failed: " + responseCode, 0, 0);
            }
        } catch (Exception e) {
            long executionTime = System.currentTimeMillis() - startTime;
            return new ScraperResult(url, threadName, "Error: " + e.getMessage(), 0, executionTime);
        }
    }
}


