package ru.javawebinar.topjava.convert;


import org.springframework.core.convert.converter.Converter;
import ru.javawebinar.topjava.util.DateTimeUtil;

import java.time.LocalDate;

public class DateConverter implements Converter<String, LocalDate> {

    @Override
    public LocalDate convert(String s) {
        return DateTimeUtil.parseLocalDate(s);
    }
}
