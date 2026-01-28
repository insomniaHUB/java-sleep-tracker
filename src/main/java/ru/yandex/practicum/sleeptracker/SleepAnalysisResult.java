package ru.yandex.practicum.sleeptracker;

public class SleepAnalysisResult<T> {

    private String str;
    private T value;

    public SleepAnalysisResult(String str, T value) {
        this.str = str;
        this.value = value;
    }

    public String getStr() {
        return str;
    }

    public T getValue() {
        return value;
    }
}
