package functions;

import ru.yandex.practicum.sleeptracker.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.SleepingSession;

import java.time.LocalTime;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class UserType implements Function<List<SleepingSession>, SleepAnalysisResult<String>> {
    @Override
    public SleepAnalysisResult<String> apply(List<SleepingSession> sessions) {

        if (sessions.isEmpty()) {
            return new SleepAnalysisResult<>("Нет зарегистрированных сессий", null);
        }

        List<SleepingSession> onlyNightSessions = sessions.stream()
                .filter(session -> session.getStartTime().toLocalTime().isBefore(LocalTime.of(6, 0))
                        || !session.getStartTime().toLocalDate().equals(session.getEndTime().toLocalDate()))
                .collect(Collectors.toList());


        long isOwl = onlyNightSessions.stream()
                .filter(session -> session.getStartTime().toLocalTime().isAfter(LocalTime.of(23, 0))
                        && session.getEndTime().toLocalTime().isAfter(LocalTime.of(9, 0)))
                .count();
        long isLark = onlyNightSessions.stream()
                .filter(session -> session.getStartTime().toLocalTime().isBefore(LocalTime.of(22, 0))
                        && session.getEndTime().toLocalTime().isBefore(LocalTime.of(7, 0)))
                .count();
        long isPigeon = onlyNightSessions.size() - (isOwl + isLark);

        if (isOwl > isLark && isOwl > isPigeon) {
            return new SleepAnalysisResult<>("Ваш хронотип: Сова", "Сова");
        } else if (isLark > isOwl && isLark > isPigeon) {
            return new SleepAnalysisResult<>("Ваш хронотип: Жаворонок", "Жаворонок");
        } else {
            return new SleepAnalysisResult<>("Ваш хронотип: Голубь", "Голубь");
        }

    }
}
