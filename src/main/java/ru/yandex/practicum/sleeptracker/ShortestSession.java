package ru.yandex.practicum.sleeptracker;

import java.util.List;
import java.util.function.Function;

public class ShortestSession implements Function<List<SleepingSession>, String> {
    @Override
    public String apply(List<SleepingSession> sessions) {
        long shortest = sessions.stream()
                .mapToLong(SleepingSession::getDurationMinutes)
                .min()
                .orElse(0);
        return String.format("Самая короткая сессия сна длилась: %d минут", shortest);
    }
}
