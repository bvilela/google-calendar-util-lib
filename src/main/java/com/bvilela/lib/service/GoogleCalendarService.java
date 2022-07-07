package com.bvilela.lib.service;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;

import com.bvilela.lib.exception.GoogleCalendarLibException;
import com.bvilela.lib.model.CalendarEvent;
import com.google.api.services.calendar.model.Event;

public interface GoogleCalendarService {

	public List<Event> getEvents(int maxResults) throws IOException, GoogleCalendarLibException;
	
	public void createEvent(CalendarEvent dto) throws IOException, GoogleCalendarLibException;
	public void createEvent(CalendarEvent dto, Logger log) throws IOException, GoogleCalendarLibException;
	
	public void createEvents(List<CalendarEvent> list) throws IOException, GoogleCalendarLibException;
	public void createEvents(List<CalendarEvent> list, Logger log) throws IOException, GoogleCalendarLibException;
	
}
