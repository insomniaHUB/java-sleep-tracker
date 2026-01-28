package ru.yandex.practicum.sleeptracker;

import functions.*;
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
        try (BufferedReader reader = new BufferedReader(new FileReader("sleep_log.txt", StandardCharsets.UTF_8))) {
            reader.lines()
                    .map(SleepTrackerApp::addSleepingSessionToList)
                    .forEach(sessions::add);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    @Test
    void totalSessionsTest() {
        Assertions.assertEquals(0, new TotalSession().apply(sessionList).getValue());
        Assertions.assertEquals(13, new TotalSession().apply(sessions).getValue());
    }

    @Test
    void shortestSessionTest() {
        Assertions.assertEquals(45, new ShortestSession().apply(sessions).getValue());
        Assertions.assertEquals(0, new ShortestSession().apply(sessionList).getValue());
    }

    @Test
    void longestSessionTest() {
        Assertions.assertEquals(500, new LongestSession().apply(sessions).getValue());
        Assertions.assertEquals(0, new LongestSession().apply(sessionList).getValue());
    }

    @Test
    void averageTimeSessionTest() {
        Assertions.assertEquals(345, new AverageTimeSession().apply(sessions).getValue());
        Assertions.assertEquals(0, new AverageTimeSession().apply(sessionList).getValue());
    }

    @Test
    void badSleepSessionTest() {
        Assertions.assertEquals(2, new BadSleepSession().apply(sessions).getValue());
        Assertions.assertEquals(0, new BadSleepSession().apply(sessionList).getValue());
    }

    @Test
    void sleeplessNightTotalTest() {
        Assertions.assertEquals(20, new SleeplessNights().apply(sessions).getValue());
        Assertions.assertNull(new SleeplessNights().apply(sessionList).getValue());
    }

    @Test
    void sleeplessNightAnotherMonthTest() {
        List<SleepingSession> anotherMonthList = new ArrayList<>();

        SleepingSession goodSession = new SleepingSession(
                LocalDateTime.of(2026, 1, 10, 23, 0),
                LocalDateTime.of(2026, 1, 11, 3, 0),
                "BAD"
        );

        SleepingSession oneDaySession = new SleepingSession(
                LocalDateTime.of(2026, 1, 10, 2, 0),
                LocalDateTime.of(2026, 1, 10, 3, 0),
                "BAD"
        );

        SleepingSession anotherMonth = new SleepingSession(
                LocalDateTime.of(2026, 2, 10, 23, 0),
                LocalDateTime.of(2026, 2, 11, 3, 0),
                "BAD"
        );

        anotherMonthList.add(oneDaySession);
        anotherMonthList.add(goodSession);
        anotherMonthList.add(anotherMonth);

        Assertions.assertEquals(29, new SleeplessNights().apply(anotherMonthList).getValue());
    }

    @Test
    void sleeplessNightGoodTest() {
        List<SleepingSession> goodSessionList = new ArrayList<>();
        List<SleepingSession> oneDaySessionList = new ArrayList<>();

        SleepingSession goodSession = new SleepingSession(
                LocalDateTime.of(2026, 1, 10, 23, 0),
                LocalDateTime.of(2026, 1, 11, 3, 0),
                "BAD"
        );

        SleepingSession oneDaySession = new SleepingSession(
                LocalDateTime.of(2026, 1, 10, 2, 0),
                LocalDateTime.of(2026, 1, 10, 3, 0),
                "BAD"
        );


        goodSessionList.add(goodSession);
        oneDaySessionList.add(oneDaySession);

        Assertions.assertEquals(0, new SleeplessNights().apply(goodSessionList).getValue());
        Assertions.assertEquals(0, new SleeplessNights().apply(oneDaySessionList).getValue());

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
        List<SleepingSession> shouldBePigeonSessionList = new ArrayList<>();
        shouldBePigeonSessionList.add(owlSession);
        shouldBePigeonSessionList.add(larkSession);

        Assertions.assertEquals("Голубь", new UserType().apply(sessions).getValue());
        Assertions.assertEquals("Голубь", new UserType().apply(shouldBePigeonSessionList).getValue());
        Assertions.assertEquals("Жаворонок", new UserType().apply(larkSessionList).getValue());
        Assertions.assertEquals("Сова", new UserType().apply(owlSessionList).getValue());
        Assertions.assertNull(new UserType().apply(sessionList).getValue());
    }
}