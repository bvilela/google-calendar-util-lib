package br.com.bvilela.lib.service.impl;

import br.com.bvilela.lib.auth.Authentication;
import br.com.bvilela.lib.service.GoogleCalendarGetService;
import br.com.bvilela.lib.utils.DateUtils;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GoogleCalendarGetServiceImpl implements GoogleCalendarGetService {

    private final Authentication authentication;

    @Override
    @SneakyThrows
    public List<Event> getEvents(int maxResults) {
        Calendar calendarService = authentication.getService();

        DateTime now = new DateTime(System.currentTimeMillis());
        Events events =
                calendarService
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
    public List<Event> getEvents(
            LocalDateTime dateTimeStart, LocalDateTime dateTimeEnd, String title) {
        Calendar calendarService = authentication.getService();

        DateTime timeMin = DateUtils.convertLocalDateTimeToDateTime(dateTimeStart);
        DateTime timeMax = DateUtils.convertLocalDateTimeToDateTime(dateTimeEnd);

        Events events =
                calendarService
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
