package br.com.bvilela.lib.utils;

import com.google.api.client.util.DateTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

class DateUtilsTest {

    @Test
    @DisplayName("Data uma Data válida, retorna um DateTime do Google API - Cenário 1")
    void shouldReturnDateTimeCase1() {
        LocalDateTime now = LocalDateTime.now();
        DateTime dateTime = DateUtils.convertLocalDateTimeToDateTime(now);
        Assertions.assertNotNull(dateTime);
    }

    @Test
    @DisplayName("Data uma Data válida, retorna um DateTime do Google API - Cenário 2")
    void shouldReturnDateTimeCase2() {
        LocalDateTime now = LocalDateTime.of(2022, 10, 5, 4, 30, 25);
        DateTime dateTime = DateUtils.convertLocalDateTimeToDateTime(now);
        String expected = "2022-10-05T04:30:25.000-03:00";
        Assertions.assertEquals(expected, dateTime.toString());
    }
}
