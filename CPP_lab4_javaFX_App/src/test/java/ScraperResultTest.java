package lab3.cpp_lab4_javafx_app;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ScraperResultTest {

    @Test
    void testScraperResultInitialization() {
        // Arrange: створення тестових даних
        String url = "https://example.com";
        String threadName = "Thread-1";
        String status = "Success";
        int dataSize = 2048;
        long executionTime = 500;

        // Act: створення об'єкта ScraperResult
        ScraperResult result = new ScraperResult(url, threadName, status, dataSize, executionTime);

        // Assert: перевірка, чи правильно ініціалізувалися всі поля
        assertEquals(url, result.getUrl(), "URL повинен відповідати переданому значенню");
        assertEquals(threadName, result.getThreadName(), "ThreadName повинен відповідати переданому значенню");
        assertEquals(status, result.getStatus(), "Status повинен відповідати переданому значенню");
        assertEquals(dataSize, result.getDataSize(), "DataSize повинен відповідати переданому значенню");
        assertEquals(executionTime, result.getExecutionTime(), "ExecutionTime повинен відповідати переданому значенню");
    }

    @Test
    void testGetUrl() {
        // Arrange
        ScraperResult result = new ScraperResult(
                "https://test.com", "Thread-1", "Success", 1000, 300
        );

        // Assert
        assertEquals("https://test.com", result.getUrl(), "Метод getUrl() повинен повернути правильний URL");
    }

    @Test
    void testGetThreadName() {
        // Arrange
        ScraperResult result = new ScraperResult(
                "https://test.com", "Thread-2", "Success", 1000, 300
        );

        // Assert
        assertEquals("Thread-2", result.getThreadName(), "Метод getThreadName() повинен повернути правильну назву потоку");
    }

    @Test
    void testGetStatus() {
        // Arrange
        ScraperResult result = new ScraperResult(
                "https://test.com", "Thread-1", "Failed", 0, 500
        );

        // Assert
        assertEquals("Failed", result.getStatus(), "Метод getStatus() повинен повернути правильний статус");
    }

    @Test
    void testGetDataSize() {
        // Arrange
        ScraperResult result = new ScraperResult(
                "https://test.com", "Thread-1", "Success", 5000, 300
        );

        // Assert
        assertEquals(5000, result.getDataSize(), "Метод getDataSize() повинен повернути правильний розмір даних");
    }

    @Test
    void testGetExecutionTime() {
        // Arrange
        ScraperResult result = new ScraperResult(
                "https://test.com", "Thread-1", "Success", 1000, 1500
        );

        // Assert
        assertEquals(1500, result.getExecutionTime(), "Метод getExecutionTime() повинен повернути правильний час виконання");
    }
}
