package functions;

import ru.yandex.practicum.sleeptracker.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.SleepingSession;

import java.util.List;
import java.util.function.Function;

public class AverageTimeSession implements Function<List<SleepingSession>, SleepAnalysisResult<Double>> {
    @Override
    public SleepAnalysisResult<Double> apply(List<SleepingSession> sessions) {
        double avg = sessions.stream()
                .mapToDouble(SleepingSession::getDurationMinutes)
                .average()
                .orElse(0);

        return new SleepAnalysisResult<>(String.format("В среднем сессии сна длятся: %.0f минут", avg), Math.floor(avg));
    }
}
