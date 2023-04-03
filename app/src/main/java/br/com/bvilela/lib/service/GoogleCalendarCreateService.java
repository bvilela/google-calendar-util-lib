package br.com.bvilela.lib.service;

import java.io.IOException;
import java.util.List;

import br.com.bvilela.lib.exception.GoogleCalendarLibException;
import br.com.bvilela.lib.model.CalendarEvent;
import org.slf4j.Logger;

public interface GoogleCalendarCreateService {

	void createEvent(CalendarEvent dto) throws IOException, GoogleCalendarLibException;
	void createEvent(CalendarEvent dto, Logger log) throws IOException, GoogleCalendarLibException;
	
	void createEvents(List<CalendarEvent> list) throws IOException, GoogleCalendarLibException;
	void createEvents(List<CalendarEvent> list, Logger log) throws IOException, GoogleCalendarLibException;
	
}
