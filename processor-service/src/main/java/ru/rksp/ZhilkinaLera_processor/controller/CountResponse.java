package ru.rksp.ZhilkinaLera.processor.dto;

import java.time.LocalDateTime;

public class CountResponse {
    private Long count;
    private LocalDateTime timestamp;

    public CountResponse() {}

    public CountResponse(Long count) {
        this.count = count;
        this.timestamp = LocalDateTime.now();
    }

    // Геттеры и сеттеры
    public Long getCount() { return count; }
    public void setCount(Long count) { this.count = count; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}