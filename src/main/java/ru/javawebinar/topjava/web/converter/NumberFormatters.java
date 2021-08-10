package ru.javawebinar.topjava.web.converter;

import org.springframework.format.Formatter;

import java.text.ParseException;
import java.util.Locale;

public class NumberFormatters {
    public static class IntegerFormatter implements Formatter<Integer> {
        @Override
        public Integer parse(String s, Locale locale) throws ParseException {
            return Integer.parseInt(s);
        }

        @Override
        public String print(Integer integer, Locale locale) {
            return integer.toString();
        }
    }
}
