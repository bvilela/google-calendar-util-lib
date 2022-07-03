package com.bvilela.lib.service;

import java.io.IOException;
import java.util.List;

import com.bvilela.lib.exception.GoogleCalendarLibException;
import com.google.api.services.calendar.model.Event;

public interface GoogleCalendarService {

	public List<Event> getEvents(int maxResults) throws IOException, GoogleCalendarLibException;
	
	public void createEvent() throws IOException, GoogleCalendarLibException;
	
}
