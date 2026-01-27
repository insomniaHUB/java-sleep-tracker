package ru.yandex.practicum.sleeptracker;

import java.util.List;
import java.util.function.Function;

public class BadSleepSession implements Function<List<SleepingSession>, String> {
    @Override
    public String apply(List<SleepingSession> sessions) {
        long countBadSession = sessions.stream()
                .filter(session -> "BAD".equals(session.getQuality()))
                .count();
        return String.format("Количество сессий с плохим качеством сна: %d", countBadSession);
    }
}
