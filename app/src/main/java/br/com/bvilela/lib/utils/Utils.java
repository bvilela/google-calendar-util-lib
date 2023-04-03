package br.com.bvilela.lib.utils;

import br.com.bvilela.lib.exception.GoogleCalendarLibException;
import br.com.bvilela.lib.model.CalendarEvent;
import com.google.api.client.util.DateTime;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Utils {

    public static DateTime convertLocalDateTimeToDateTime(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:SS'-03:00'");
        String date = dateTime.format(formatter);
        return new DateTime(date);
    }

    public static void validateDto(CalendarEvent dto) throws GoogleCalendarLibException {
        if (Objects.isNull(dto.getSummary())) {
            throw new GoogleCalendarLibException("Summary is a required field!");
        }

        if (Objects.isNull(dto.getDateTimeStart())) {
            throw new GoogleCalendarLibException("DateTimeStart is a required field!");
        }

        if (Objects.isNull(dto.getDateTimeEnd())) {
            throw new GoogleCalendarLibException("DateTimeEnd is a required field!");
        }
    }
}
