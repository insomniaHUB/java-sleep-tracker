package ru.yandex.practicum.sleeptracker;

import java.time.Period;
import java.util.List;
import java.util.function.Function;

public class SleeplessNights implements Function<List<SleepingSession>, String> {
    @Override
    public String apply(List<SleepingSession> sessions) {

        if (sessions.isEmpty()) {
            return "Нет зарегистрированных сессий";
        }

        long countSleeplessNights = sessions.stream()
                .filter(session -> !(session.getStartTime().toLocalDate().isBefore(session.getEndTime().toLocalDate()) ||
                        (session.getStartTime().getHour() < 6) ||
                        session.getEndTime().getHour() < 6))
                .count()
                + (Period.between(sessions.getFirst().getStartTime().toLocalDate(),
                sessions.getLast().getEndTime().toLocalDate()).getDays()
                - sessions.size());


        return String.format("Бессонных ночей: %d", countSleeplessNights);
    }
}
