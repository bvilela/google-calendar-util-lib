package br.com.bvilela.lib.service;

import br.com.bvilela.lib.auth.Authentication;
import br.com.bvilela.lib.config.ConfigLib;
import br.com.bvilela.lib.exception.GoogleCalendarLibException;
import br.com.bvilela.lib.model.CalendarEvent;
import br.com.bvilela.lib.service.impl.GoogleCalendarCreateServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;

class GoogleCalendarCreateServiceImplTest {

    @InjectMocks private GoogleCalendarCreateServiceImpl service;

    @Mock private ConfigLib config;

    @Mock private Authentication authentication;

    @Mock private GoogleCalendarGetService googleCalendarGetService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        service =
                new GoogleCalendarCreateServiceImpl(
                        config, authentication, googleCalendarGetService);
    }

    @DisplayName("Event without Summary, return Exception")
    @ParameterizedTest(name = "Summary is \"{0}\"")
    @NullAndEmptySource
    @ValueSource(strings = {"  "})
    void createEventWithouSummaryException(String summary) {
        CalendarEvent dto = CalendarEvent.builder().setSummary(summary).build();
        GoogleCalendarLibException exception =
                Assertions.assertThrows(
                        GoogleCalendarLibException.class, () -> service.createEvent(dto));
        Assertions.assertEquals("Summary is a required field!", exception.getMessage());
    }

    @Test
    @DisplayName("Event without DateTimeStart, return Exception")
    void createEventWithouDateTimeStartException() {
        CalendarEvent dto = CalendarEvent.builder().setSummary("summary").build();
        GoogleCalendarLibException exception =
                Assertions.assertThrows(
                        GoogleCalendarLibException.class, () -> service.createEvent(dto));
        Assertions.assertEquals("DateTimeStart is a required field!", exception.getMessage());
    }

    @Test
    @DisplayName("Event without DateTimeEnd, return Exception")
    void createEventWithouDateTimeEndException() {
        CalendarEvent dto =
                CalendarEvent.builder()
                        .setSummary("summary")
                        .setDateTimeStart(LocalDateTime.now())
                        .build();
        GoogleCalendarLibException exception =
                Assertions.assertThrows(
                        GoogleCalendarLibException.class, () -> service.createEvent(dto));
        Assertions.assertEquals("DateTimeEnd is a required field!", exception.getMessage());
    }
}
