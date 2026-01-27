package ru.yandex.practicum.sleeptracker;

import java.util.List;
import java.util.function.Function;

public class AverageTimeSession implements Function<List<SleepingSession>, String> {
    @Override
    public String apply(List<SleepingSession> sessions) {
        double avg = sessions.stream()
                .mapToDouble(SleepingSession::getDurationMinutes)
                .average()
                .orElse(0);

        return String.format("В среднем сессии сна длятся: %.0f минут", avg);
    }
}
