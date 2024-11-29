package lab3.cpp_lab4_javafx_app;

public class ScraperResult {
    private final String url;
    private final String threadName;
    private final String status;
    private final int dataSize;
    private final long executionTime;

    public ScraperResult(String url, String threadName, String status, int dataSize, long executionTime) {
        this.url = url;
        this.threadName = threadName;
        this.status = status;
        this.dataSize = dataSize;
        this.executionTime = executionTime;
    }

    public String getUrl() {
        return url;
    }

    public String getThreadName() {
        return threadName;
    }

    public String getStatus() {
        return status;
    }

    public int getDataSize() {
        return dataSize;
    }

    public long getExecutionTime() {
        return executionTime;
    }
}
