package br.com.bvilela.lib.service;

import br.com.bvilela.lib.model.CalendarEvent;

import java.util.List;

public interface GoogleCalendarCreateService {

	void createEvent(CalendarEvent dto);
	void createEvents(List<CalendarEvent> list);
}
