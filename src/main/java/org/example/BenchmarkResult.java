package org.example;

public class BenchmarkResult {
    private final String method;
    private final int iterations;
    private final long timeNanos;

    public BenchmarkResult(String method, int iterations, long timeNanos) {
        this.method = method;
        this.iterations = iterations;
        this.timeNanos = timeNanos;
    }

    public String getMethod() { return method; }
    public int getIterations() { return iterations; }
    public long getTimeNanos() { return timeNanos; }

    public double getTimeMillis() {
        return timeNanos / 1_000_000.0;
    }

    @Override
    public String toString() {
        return String.format("%-20s | %10d | %15.3f мс",
                method, iterations, getTimeMillis());
    }
}
