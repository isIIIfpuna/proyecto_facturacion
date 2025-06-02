package com.fpuna.py.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class MethodUtils {

    public static String convertLocalDateToString(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return date.format(formatter);
    }
}

