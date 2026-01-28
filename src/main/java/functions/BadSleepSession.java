package functions;

import ru.yandex.practicum.sleeptracker.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.SleepingSession;

import java.util.List;
import java.util.function.Function;

public class BadSleepSession implements Function<List<SleepingSession>, SleepAnalysisResult<Long>> {
    @Override
    public SleepAnalysisResult<Long> apply(List<SleepingSession> sessions) {
        long countBadSession = sessions.stream()
                .filter(session -> "BAD".equals(session.getQuality()))
                .count();
        return new SleepAnalysisResult<>(String.format("Количество сессий с плохим качеством сна: %d", countBadSession),
                countBadSession);
    }
}
