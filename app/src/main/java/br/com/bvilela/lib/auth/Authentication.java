package br.com.bvilela.lib.auth;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import br.com.bvilela.lib.exception.GoogleCalendarLibException;
import org.springframework.core.io.ClassPathResource;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;

public final class Authentication {

	public static final String APPLICATION_NAME = "My Google Calendar Application";
	// If modifying these scopes, delete your previously saved tokens folder
	private static final List<String> SCOPES = Collections.singletonList(CalendarScopes.CALENDAR);
	private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
	private static final String TOKENS_DIRECTORY_PATH = "tokens";

	public static Calendar getService(String pathCredentials) throws GoogleCalendarLibException {
		try {
			final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
			return new Calendar.Builder(httpTransport, JSON_FACTORY, getCredentials(httpTransport, pathCredentials))
					.setApplicationName(APPLICATION_NAME).build();
			
		} catch (GeneralSecurityException | IOException e) {
			throw new GoogleCalendarLibException("Error to get Google Calendar Service");
		}
	}

	private static Credential getCredentials(final NetHttpTransport httpTransport, 
			final String pathCredentials) throws GoogleCalendarLibException {

		GoogleClientSecrets clientSecrets = getClientSecrets(pathCredentials);

		try {
			// @formatter:off
			// Build flow and trigger user authorization request.
			GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow
					.Builder(httpTransport, JSON_FACTORY, clientSecrets, SCOPES)
					.setDataStoreFactory(new FileDataStoreFactory(new File(TOKENS_DIRECTORY_PATH)))
					.build();
			// @formatter:on

			LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
			return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
			
		} catch (Exception e) {
			throw new GoogleCalendarLibException("Error get Google Api Client OAuth Credential", e.getMessage());
		}
	}

	private static GoogleClientSecrets getClientSecrets(final String pathCredentials) throws GoogleCalendarLibException {

		InputStream in = null;
		try {
			
			if (Objects.isNull(pathCredentials)) {
				Path path = Paths.get("google-credentials.json");
				in = new ClassPathResource(path.toString()).getInputStream();	
			} else {
				Path path = Paths.get(pathCredentials, "google-credentials.json");
				in = new FileInputStream(path.toString());
			}
			
		} catch (IOException e) {
			throw new GoogleCalendarLibException("Resource not found: google-credentials.json", e.getMessage());
		}

		try {
			return GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
		} catch (IOException e) {
			throw new GoogleCalendarLibException("Error to get GoogleClientSecrets", e.getMessage());
		}
	}

}
