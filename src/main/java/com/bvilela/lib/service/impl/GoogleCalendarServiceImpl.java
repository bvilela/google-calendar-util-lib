package com.bvilela.lib.service.impl;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bvilela.lib.auth.Authentication;
import com.bvilela.lib.exception.GoogleCalendarLibException;
import com.bvilela.lib.model.CalendarEvent;
import com.bvilela.lib.service.GoogleCalendarService;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.model.Events;

@Service
public class GoogleCalendarServiceImpl implements GoogleCalendarService {
	
	@Value("${com.bvilela.lib.google.calendar.logging.event:false}")
	private boolean showLog;
	
	@Value("${com.bvilela.lib.google.calendar.path.credentials:#{null}}")
	private String pathCredentials;

	@Override
	public List<Event> getEvents(int maxResults) throws IOException, GoogleCalendarLibException {
		Calendar service = Authentication.getService(pathCredentials);

		DateTime now = new DateTime(System.currentTimeMillis());
		Events events = service.events().list("primary").setMaxResults(maxResults).setTimeMin(now)
				.setOrderBy("startTime").setSingleEvents(true).execute();

		return events.getItems();
	}
	
	@Override
	public void createEvent(CalendarEvent dto) throws IOException, GoogleCalendarLibException {
		createEvent(dto, null);
	}

	@Override
	public void createEvent(CalendarEvent dto, Logger log) throws IOException, GoogleCalendarLibException {
		Calendar service = Authentication.getService(pathCredentials);
		
		validate(dto);

		// @formatter:off
		Event event = new Event()
			.setSummary(dto.getSummary())
    	    .setLocation(dto.getLocation())
			.setDescription(dto.getDescription());

		EventDateTime start = new EventDateTime()
			.setDateTime(convertLocalDateTimeToDateTime(dto.getDateTimeStart()))
			.setTimeZone(dto.getTimeZone());
		event.setStart(start);

		EventDateTime end = new EventDateTime()
			.setDateTime(convertLocalDateTimeToDateTime(dto.getDateTimeEnd()))
			.setTimeZone(dto.getTimeZone());
		event.setEnd(end);

		Event.Reminders reminders = new Event.Reminders()
			.setUseDefault(false);
		event.setReminders(reminders);

		event.setColorId(dto.getColor().getColorId());
		// @formatter:on
		
		if (showLog && Objects.nonNull(log)) {
			log.info("Sending Event to Google Calendar...");
			String msg = dto.toStringSummary();
			log.info("{}", msg);
		}

		String calendarId = "primary";
		service.events().insert(calendarId, event).execute();
	}

	@Override
	public void createEvents(List<CalendarEvent> list) throws IOException, GoogleCalendarLibException {
		createEvents(list, null);
	}

	@Override
	public void createEvents(List<CalendarEvent> list, Logger log) throws IOException, GoogleCalendarLibException {
		for (CalendarEvent calendarEvent : list) {
			createEvent(calendarEvent, log);
		}
	}
	
	private DateTime convertLocalDateTimeToDateTime(LocalDateTime dateTime) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:SS'-03:00'");
		String date = dateTime.format(formatter);
		return new DateTime(date);
	}
	
	private void validate(CalendarEvent dto) throws GoogleCalendarLibException {
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
