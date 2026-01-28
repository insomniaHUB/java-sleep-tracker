package functions;

import ru.yandex.practicum.sleeptracker.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.SleepingSession;

import java.time.LocalTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class SleeplessNights implements Function<List<SleepingSession>, SleepAnalysisResult<Long>> {

    private final int END_OF_NIGHT_TIME = 6;

    @Override
    public SleepAnalysisResult<Long> apply(List<SleepingSession> sessions) {

        if (sessions.isEmpty()) {
            return new SleepAnalysisResult<>("Нет зарегистрированных сессий", null);
        }

        List<SleepingSession> onlyNightSessions = sessions.stream()
                .filter(session -> session.getStartTime().toLocalTime().isBefore(LocalTime.of(6, 0))
                        || !session.getStartTime().toLocalDate().equals(session.getEndTime().toLocalDate()))
                .collect(Collectors.toList());

        if (Period.between(sessions.getFirst().getStartTime().toLocalDate(),
                sessions.getLast().getEndTime().toLocalDate()).getDays() == 0) {
            long countSleeplessNights = onlyNightSessions.stream()
                    .filter(session -> session.getStartTime().toLocalTime().getHour() > END_OF_NIGHT_TIME)
                    .count();
            return new SleepAnalysisResult<>(String.format("Бессонных ночей: %d", countSleeplessNights), countSleeplessNights);
        } else {
            long countSleeplessNights = ChronoUnit.DAYS.between(
                    sessions.getFirst().getStartTime().toLocalDate(),
                    sessions.getLast().getEndTime().toLocalDate()) - sessions.stream().filter(session ->
                            session.getStartTime().toLocalDate().isBefore(session.getEndTime().toLocalDate())
                                    || session.getStartTime().getHour() < END_OF_NIGHT_TIME)
                    .count();
            return new SleepAnalysisResult<>(String.format("Бессонных ночей: %d", countSleeplessNights), countSleeplessNights);
        }
    }
}
