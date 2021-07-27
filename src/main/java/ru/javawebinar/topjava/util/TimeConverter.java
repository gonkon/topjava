package ru.javawebinar.topjava.util;

import org.springframework.core.convert.converter.Converter;

import java.time.LocalTime;

public class TimeConverter implements Converter<String, LocalTime> {

    @Override
    public LocalTime convert(String s) {
        return LocalTime.parse(s);
    }
}
