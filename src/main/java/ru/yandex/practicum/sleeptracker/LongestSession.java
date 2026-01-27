package ru.yandex.practicum.sleeptracker;

import java.util.List;
import java.util.function.Function;

public class LongestSession implements Function<List<SleepingSession>, String> {
    @Override
    public String apply(List<SleepingSession> sessions) {
        long longest = sessions.stream()
                .mapToLong(SleepingSession::getDurationMinutes)
                .max()
                .orElse(0);
        return String.format("Самая длинная сессия сна длилась: %d минут", longest);
    }
}
