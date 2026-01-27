package ru.yandex.practicum.sleeptracker;

import java.util.List;
import java.util.function.Function;

public class TotalSession implements Function<List<SleepingSession>, String> {
    @Override
    public String apply(List<SleepingSession> sessions) {
        return String.format("Общее количество сессий сна: %d", sessions.size());
    }
}
