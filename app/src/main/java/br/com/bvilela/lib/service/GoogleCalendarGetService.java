package br.com.bvilela.lib.service;

import com.google.api.services.calendar.model.Event;
import lombok.SneakyThrows;

import java.time.LocalDateTime;
import java.util.List;

public interface GoogleCalendarGetService {

    @SneakyThrows
    List<Event> getEvents(int maxResults);

    @SneakyThrows
    List<Event> getEvents(LocalDateTime dateTimeStart, LocalDateTime dateTimeEnd, String title);
}
