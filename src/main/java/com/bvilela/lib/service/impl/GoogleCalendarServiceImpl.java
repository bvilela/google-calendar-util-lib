package com.bvilela.lib.service.impl;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.bvilela.lib.auth.Authentication;
import com.bvilela.lib.exception.GoogleCalendarLibException;
import com.bvilela.lib.service.GoogleCalendarService;
import com.bvilela.lib.utils.Colors;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.model.Events;

@Service
public class GoogleCalendarServiceImpl implements GoogleCalendarService {

	@Override
	public List<Event> getEvents(int maxResults) throws IOException, GoogleCalendarLibException {
		Calendar service = Authentication.getService();

		DateTime now = new DateTime(System.currentTimeMillis());
		Events events = service.events().list("primary").setMaxResults(maxResults).setTimeMin(now)
				.setOrderBy("startTime").setSingleEvents(true).execute();
		
		return events.getItems();
	}

	@Override
	public void createEvent() throws IOException, GoogleCalendarLibException {
		Calendar service = Authentication.getService();

		Event event = new Event().setSummary("TESTE")
//    	    .setLocation("800 Howard St., San Francisco, CA 94103")
				.setDescription("A chance to hear more about Google's developer products.");

		EventDateTime start = new EventDateTime().setDateTime(new DateTime("2022-07-02T23:00:00-03:00"))
				.setTimeZone("America/Sao_Paulo");
		event.setStart(start);

		EventDateTime end = new EventDateTime().setDateTime(new DateTime("2022-07-02T23:30:00-03:00"))
				.setTimeZone("America/Sao_Paulo");
		event.setEnd(end);

		Event.Reminders reminders = new Event.Reminders().setUseDefault(false);
		event.setReminders(reminders);

		event.setColorId(Colors.PADRAO);

		String calendarId = "primary";
		service.events().insert(calendarId, event).execute();
	}
}
