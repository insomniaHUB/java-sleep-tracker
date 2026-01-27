package ru.yandex.practicum.sleeptracker;

import java.time.Duration;
import java.time.LocalDateTime;

public class SleepingSession {
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String quality;
    private Duration duration;

    public SleepingSession(LocalDateTime startTime, LocalDateTime endTime, String quality) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.quality = quality;
        this.duration = Duration.between(startTime, endTime);
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }
    public LocalDateTime getEndTime() {
        return endTime;
    }
    public String getQuality() {
        return quality;
    }
    public long getDurationMinutes() {
        return duration.toMinutes();
    }
}
