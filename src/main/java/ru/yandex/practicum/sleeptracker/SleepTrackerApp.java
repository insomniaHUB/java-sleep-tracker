package ru.yandex.practicum.sleeptracker;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

public class SleepTrackerApp {

    static List<SleepingSession> sessionList = new ArrayList<>();
    private static final List<Function<List<SleepingSession>, String>> analysisFunctions = new ArrayList<>();

    public static void main(String[] args) {
        String pathToLog;
        if (args.length > 0) {
            pathToLog = args[0];
            try (BufferedReader reader = new BufferedReader(new FileReader(pathToLog, StandardCharsets.UTF_8))) {
                reader.lines()
                        .map(SleepTrackerApp::addSleepingSessionToList)
                        .forEach(sessionList::add);

                Stream.of(
                        new TotalSession(),
                        new ShortestSession(),
                        new LongestSession(),
                        new AverageTimeSession(),
                        new BadSleepSession(),
                        new SleeplessNights(),
                        new UserType()
                ).forEach(analysisFunctions::add);

                analysisFunctions.stream()
                        .map(function -> function.apply(sessionList))
                        .forEach(System.out::println);

            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        } else {
            System.out.println("Пожалуйста, укажите путь к файлу с логом сна.");
        }
    }

     public static SleepingSession addSleepingSessionToList(String line) {
        String[] list = line.split(";");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");

        LocalDateTime dateTimeStart = LocalDateTime.parse(list[0], formatter);
        LocalDateTime dateTimeEnd = LocalDateTime.parse(list[1], formatter);

        return new SleepingSession(dateTimeStart, dateTimeEnd, list[2]);
    }

}