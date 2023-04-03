package br.com.bvilela.lib.service.impl;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import br.com.bvilela.lib.config.ConfigLib;
import br.com.bvilela.lib.service.GoogleCalendarGetService;
import br.com.bvilela.lib.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import br.com.bvilela.lib.auth.Authentication;
import br.com.bvilela.lib.exception.GoogleCalendarLibException;
import br.com.bvilela.lib.model.CalendarEvent;
import br.com.bvilela.lib.service.GoogleCalendarCreateService;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;

@Service
@RequiredArgsConstructor
public class GoogleCalendarCreateServiceImpl implements GoogleCalendarCreateService {

	private final ConfigLib config;

	private final GoogleCalendarGetService getService;

	private Logger log;
	
	@Override
	public void createEvent(CalendarEvent dto) throws IOException, GoogleCalendarLibException {
		createEvent(dto, null);
	}

	@Override
	public void createEvent(CalendarEvent dto, Logger log) throws IOException, GoogleCalendarLibException {
		this.log = log;
		Calendar service = Authentication.getService(config.getPathCredentials());
		
		Utils.validateDto(dto);

		List<Event> events = getService.getEvents(dto.getDateTimeStart(), dto.getDateTimeEnd(), dto.getSummary());
		if (!events.isEmpty()) {
			if (showLog()) {
				log.info("Event not Send to Google Calendar. Event already exists in Calendar");
				log.info("{}", dto.toStringSummary());
			}
			return;
		}

		Event event = createCalendarModelEvent(dto);

		if (showLog()) {
			log.info("Sending Event to Google Calendar...");
			log.info("{}", dto.toStringSummary());
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

	private boolean showLog() {
		return config.isShowLog() && Objects.nonNull(log);
	}

	private static Event createCalendarModelEvent(CalendarEvent dto) {
		// @formatter:off
		Event event = new Event()
				.setSummary(dto.getSummary())
				.setLocation(dto.getLocation())
				.setDescription(dto.getDescription());

		EventDateTime start = new EventDateTime()
				.setDateTime(Utils.convertLocalDateTimeToDateTime(dto.getDateTimeStart()))
				.setTimeZone(dto.getTimeZone());
		event.setStart(start);

		EventDateTime end = new EventDateTime()
				.setDateTime(Utils.convertLocalDateTimeToDateTime(dto.getDateTimeEnd()))
				.setTimeZone(dto.getTimeZone());
		event.setEnd(end);

		Event.Reminders reminders = new Event.Reminders()
				.setUseDefault(false);
		event.setReminders(reminders);

		event.setColorId(dto.getColor().getColorId());
		// @formatter:on
		return event;
	}
}
