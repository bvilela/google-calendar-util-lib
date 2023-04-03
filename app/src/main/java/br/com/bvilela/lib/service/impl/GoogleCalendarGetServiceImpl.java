package br.com.bvilela.lib.service.impl;

import br.com.bvilela.lib.auth.Authentication;
import br.com.bvilela.lib.config.ConfigLib;
import br.com.bvilela.lib.exception.GoogleCalendarLibException;
import br.com.bvilela.lib.service.GoogleCalendarGetService;
import br.com.bvilela.lib.utils.Utils;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GoogleCalendarGetServiceImpl implements GoogleCalendarGetService {

    private final ConfigLib config;

    @Override
    @SneakyThrows
    public List<Event> getEvents(int maxResults) {
        Calendar calendarService = Authentication.getService(config.getPathCredentials());

        DateTime now = new DateTime(System.currentTimeMillis());
        Events events = calendarService
                .events()
                .list("primary")
                .setMaxResults(maxResults)
                .setTimeMin(now)
                .setOrderBy("startTime")
                .setSingleEvents(true)
                .execute();

        return events.getItems();
    }

    @Override
    @SneakyThrows
    public List<Event> getEvents(LocalDateTime dateTimeStart, LocalDateTime dateTimeEnd, String title) {
        Calendar calendarService = Authentication.getService(config.getPathCredentials());

        DateTime timeMin = Utils.convertLocalDateTimeToDateTime(dateTimeStart);
        DateTime timeMax = Utils.convertLocalDateTimeToDateTime(dateTimeEnd);

        Events events = calendarService
                .events()
                .list("primary")
                .setTimeMin(timeMin)
                .setTimeMax(timeMax)
                .set("Summary", title)
                .setOrderBy("startTime")
                .setSingleEvents(true)
                .execute();

        return events.getItems();
    }
}
