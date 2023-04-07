package br.com.bvilela.lib.service.impl;

import br.com.bvilela.lib.auth.Authentication;
import br.com.bvilela.lib.config.ConfigLib;
import br.com.bvilela.lib.exception.GoogleCalendarLibException;
import br.com.bvilela.lib.model.CalendarEvent;
import br.com.bvilela.lib.service.GoogleCalendarCreateService;
import br.com.bvilela.lib.service.GoogleCalendarGetService;
import br.com.bvilela.lib.utils.Utils;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class GoogleCalendarCreateServiceImpl implements GoogleCalendarCreateService {

	private final ConfigLib config;

	private final GoogleCalendarGetService getService;

	@Override
	@SneakyThrows
	public void createEvent(CalendarEvent dto) {
		validateDto(dto);

		if (checkEventAlreadyExists(dto)) {
			if (config.isLogEnabled()) {
				log.info("Event not Send to Google Calendar. Event already exists in Calendar");
				log.info("{}", dto.toStringSummary());
			}
			return;
		}

		Event event = createCalendarModelEvent(dto);

		sendEventToCalendarAPI(dto, event);
	}

	@Override
	@SneakyThrows
	public void createEvents(List<CalendarEvent> list) {
		list.forEach(this::createEvent);
	}

	@SneakyThrows
	private void validateDto(CalendarEvent dto) {
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

	private boolean checkEventAlreadyExists(CalendarEvent dto) {
		List<Event> events = getService.getEvents(dto.getDateTimeStart(), dto.getDateTimeEnd(), dto.getSummary());
		return !events.isEmpty();
	}

	private Event createCalendarModelEvent(CalendarEvent dto) {
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

		Event.Reminders reminders = new Event
				.Reminders()
				.setUseDefault(false);
		event.setReminders(reminders);

		event.setColorId(dto.getColor().getColorId());
		// @formatter:on
		return event;
	}

	@SneakyThrows
	private void sendEventToCalendarAPI(CalendarEvent calendarEvent, Event event) {
		Calendar service = Authentication.getService(config.getCredentialsPath());

		if (config.isLogEnabled()) {
			log.info("Sending Event to Google Calendar...");
			log.info("{}", calendarEvent.toStringSummary());
		}

		String calendarId = "primary";
		service.events().insert(calendarId, event).execute();
	}

}
