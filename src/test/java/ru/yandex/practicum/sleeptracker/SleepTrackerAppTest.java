package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SleepTrackerAppTest {

    static List<SleepingSession> sessions = new ArrayList<>();
    List<SleepingSession> sessionList = new ArrayList<>();

    @BeforeAll
    static void fillSessionList() {
        try (BufferedReader reader = new BufferedReader(new FileReader("src\\main\\resources\\sleep_log.txt", StandardCharsets.UTF_8))) {
            reader.lines()
                    .map(SleepTrackerApp::addSleepingSessionToList)
                    .forEach(sessions::add);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    @Test
    void totalSessionsTest() {
        Assertions.assertEquals("Общее количество сессий сна: 0", new TotalSession().apply(sessionList));
        Assertions.assertEquals("Общее количество сессий сна: 13", new TotalSession().apply(sessions));
    }

    @Test
    void shortestSessionTest() {
        Assertions.assertEquals("Самая короткая сессия сна длилась: 45 минут", new ShortestSession().apply(sessions));
        Assertions.assertEquals("Самая короткая сессия сна длилась: 0 минут", new ShortestSession().apply(sessionList));
    }

    @Test
    void longestSessionTest() {
        Assertions.assertEquals("Самая длинная сессия сна длилась: 500 минут", new LongestSession().apply(sessions));
        Assertions.assertEquals("Самая длинная сессия сна длилась: 0 минут", new LongestSession().apply(sessionList));
    }

    @Test
    void averageTimeSessionTest() {
        Assertions.assertEquals("В среднем сессии сна длятся: 345 минут", new AverageTimeSession().apply(sessions));
        Assertions.assertEquals("В среднем сессии сна длятся: 0 минут", new AverageTimeSession().apply(sessionList));
    }

    @Test
    void badSleepSessionTest() {
        Assertions.assertEquals("Количество сессий с плохим качеством сна: 2", new BadSleepSession().apply(sessions));
        Assertions.assertEquals("Количество сессий с плохим качеством сна: 0", new BadSleepSession().apply(sessionList));
    }

    @Test
    void sleeplessNightTest() {
        List<SleepingSession> goodSessionList = new ArrayList<>();
        SleepingSession goodSession = new SleepingSession(LocalDateTime.of(2026, 1, 10, 23, 0),
                LocalDateTime.of(2026, 1, 11, 3, 0), "BAD");
        goodSessionList.add(goodSession);


        Assertions.assertEquals("Бессонных ночей: 20", new SleeplessNights().apply(sessions));
        Assertions.assertEquals("Бессонных ночей: 0", new SleeplessNights().apply(goodSessionList));
        Assertions.assertEquals("Нет зарегистрированных сессий", new SleeplessNights().apply(sessionList));

    }

    @Test
    void userTypeTest() {
        List<SleepingSession> owlSessionList = new ArrayList<>();
        SleepingSession owlSession = new SleepingSession(LocalDateTime.of(2026, 1, 10, 23, 30),
                LocalDateTime.of(2026, 1, 11, 10, 0), "NORMAL");
        owlSessionList.add(owlSession);
        List<SleepingSession> larkSessionList = new ArrayList<>();
        SleepingSession larkSession = new SleepingSession(LocalDateTime.of(2026, 1, 10, 21, 30),
                LocalDateTime.of(2026, 1, 11, 6, 0), "NORMAL");
        larkSessionList.add(larkSession);

        Assertions.assertEquals("Ваш хронотип: Голубь", new UserType().apply(sessions));
        Assertions.assertEquals("Ваш хронотип: Сова", new UserType().apply(owlSessionList));
        Assertions.assertEquals("Ваш хронотип: Жаворонок", new UserType().apply(larkSessionList));
        Assertions.assertEquals("Нет зарегистрированных сессий", new UserType().apply(sessionList));
    }

}