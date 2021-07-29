package ru.javawebinar.topjava.convert;

import org.springframework.core.convert.converter.Converter;
import ru.javawebinar.topjava.util.DateTimeUtil;

import java.time.LocalTime;

public class TimeConverter implements Converter<String, LocalTime> {

    @Override
    public LocalTime convert(String s) {
        return DateTimeUtil.parseLocalTime(s);
    }
}
