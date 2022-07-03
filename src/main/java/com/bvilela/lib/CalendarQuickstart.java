package com.bvilela.lib;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.model.Events;

public class CalendarQuickstart {
	private static final String APPLICATION_NAME = "Google Calendar API Java Quickstart";

	// Global instance of the JSON factory.
	private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();

	// Directory to store authorization tokens for this application.
	private static final String TOKENS_DIRECTORY_PATH = "tokens";

	// If modifying these scopes, delete your previously saved tokens/ folder.
	private static final List<String> SCOPES = Collections.singletonList(CalendarScopes.CALENDAR);

	/**
	 * Creates an authorized Credential object.
	 * 
	 * @param HTTP_TRANSPORT The network HTTP Transport.
	 * @return An authorized Credential object.
	 * @throws IOException If the credentials.json file cannot be found.
	 */
	private static Credential getCredentials(final NetHttpTransport httpTransport) throws IOException {
		// Load client secrets.
		final String CREDENTIALS_FILE_PATH = "/credentials.json";
		InputStream in = CalendarQuickstart.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
		if (in == null) {
			throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
		}
		GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

		// Build flow and trigger user authorization request.
		GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(httpTransport, JSON_FACTORY,
				clientSecrets, SCOPES)
						.setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH))).build();

		LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
		return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
	}
	
	private Calendar getCalendarService() throws GeneralSecurityException, IOException {
		// Build a new authorized API client service.
		final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
		Calendar service = new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
				.setApplicationName(APPLICATION_NAME).build();
		return service;
	}

	public void call() throws IOException, GeneralSecurityException {
		Calendar service = getCalendarService();
		
		// List the next 10 events from the primary calendar.
		DateTime now = new DateTime(System.currentTimeMillis());
		Events events = service.events().list("primary").setMaxResults(10).setTimeMin(now).setOrderBy("startTime")
				.setSingleEvents(true).execute();
		List<Event> items = events.getItems();
		if (items.isEmpty()) {
			System.out.println("No upcoming events found.");
		} else {
			System.out.println("Upcoming events");
			for (Event event : items) {
				DateTime start = event.getStart().getDateTime();
				if (start == null) {
					start = event.getStart().getDate();
				}
				System.out.printf("%s (%s)\n", event.getSummary(), start);
			}
		}
	}

	public void createEvent() throws GeneralSecurityException, IOException {
		Calendar service = getCalendarService();

		Event event = new Event().setSummary("TESTE")
//    	    .setLocation("800 Howard St., San Francisco, CA 94103")
			.setDescription("A chance to hear more about Google's developer products.");

		EventDateTime start = new EventDateTime()
			.setDateTime(new DateTime("2022-07-02T23:00:00-03:00"))
			.setTimeZone("America/Sao_Paulo");
		event.setStart(start);

		EventDateTime end = new EventDateTime()
			.setDateTime(new DateTime("2022-07-02T23:30:00-03:00"))
			.setTimeZone("America/Sao_Paulo");
		event.setEnd(end);

		Event.Reminders reminders = new Event.Reminders()
			.setUseDefault(false);
		event.setReminders(reminders);
		
		event.setColorId("0"); // Padrao
//    	event.setColorId("1"); //Lavanda
//    	event.setColorId("2"); //Salvia
//    	event.setColorId("3"); //Uva
//    	event.setColorId("4"); //Flamingo
//    	event.setColorId("5"); //Banana
//    	event.setColorId("6"); //Tangerina
//    	event.setColorId("7"); //Pavão
//    	event.setColorId("8"); //Grafite
//    	event.setColorId("9"); //Mirtilo
//    	event.setColorId("10"); //Manjericão
//    	event.setColorId("11"); //Tomate

		String calendarId = "primary";
		service.events().insert(calendarId, event).execute();
	}
}
