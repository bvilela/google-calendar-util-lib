# Google Calendar Util Lib

### :dart: Quality Status
[![build](https://github.com/bvilela/google-calendar-util-lib/actions/workflows/maven_ci_cd.yml/badge.svg?branch=master)](https://github.com/bvilela/google-calendar-util-lib/actions/workflows/maven_ci_cd.yml)
[![publish](https://github.com/bvilela/google-calendar-util-lib/actions/workflows/maven_ci_cd_publish.yml/badge.svg)](https://github.com/bvilela/google-calendar-util-lib/actions/workflows/maven_ci_cd_publish.yml)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=bvilela_google-calendar-util-lib&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=bvilela_google-calendar-util-lib)
<!-- [![Coverage](https://sonarcloud.io/api/project_badges/measure?project=bvilela_google-calendar-util-lib&metric=coverage)](https://sonarcloud.io/summary/new_code?id=bvilela_google-calendar-util-lib) -->

### :bar_chart: Repository Statistics
[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=bvilela_google-calendar-util-lib&metric=ncloc)](https://sonarcloud.io/summary/new_code?id=bvilela_google-calendar-util-lib)
![GitHub repo size](https://img.shields.io/github/repo-size/bvilela/google-calendar-util-lib)
![GitHub language count](https://img.shields.io/github/languages/count/bvilela/google-calendar-util-lib)
![GitHub open issues](https://img.shields.io/github/issues-raw/bvilela/google-calendar-util-lib)
![GitHub open pull requests](https://img.shields.io/github/issues-pr/bvilela/google-calendar-util-lib)


## :mag_right: Summary
Java Lib with useful services for Google Calendar API.


## :computer: Technologies
* Maven
* Java 1.8
* [Lombok](https://projectlombok.org/)
* [Google Calendar API](https://developers.google.com/calendar/api)
* Spring Contex
* Static Code Analysis: [SonarCloud](https://sonarcloud.io/)


## :rocket: GitHub Actions
* Analyze SonarCloud
* Build with Maven (branch master)
* Publish on GitHub Packages (tag/release)


## :hammer_and_wrench: Lib Features
Read and create events on Google Calendar.

## :gear: Add dependency in your project
To include this dependency in you project, you have to do three things.

1. Add as dependency in your `pom.xml`:
```xml
<dependency>
    <groupId>com.bvilela.lib</groupId>
    <artifactId>google-calendar-util-lib</artifactId>
    <version>0.0.1</version>
</dependency>
```

2. Add the GitHub repository in your `pom.xml`:
```xml
<repositories>
	<repository>
		<id>github</id>
		<name>GitHub</name>
		<url>https://maven.pkg.github.com/bvilela/google-calendar-util-lib</url>
		<releases>
			<enabled>true</enabled>
		</releases>
		<snapshots>
			<enabled>true</enabled>
		</snapshots>
	</repository>
</repositories>
```

3. Add the authentication to the Package Registry in your global `settings.xml`: `USER_HOME\.m2\settings.xml`
```xml
<servers>
    <server>
        <id>github</id>
        <username>YOUR_USERNAME</username>
        <password>YOUR_AUTH_TOKEN</password>
    </server>
</servers>
```
Replace the `YOUR_USERNAME` with your GitHub login name.

Replace the `YOUR_AUTH_TOKEN` with a generated GitHub Personal Access Token (PAT):

> *GitHub > Settings > Developer Settings > Personal access tokens > Generate new token*. 
> 
> The token needs at least the **`read:packages`** scope.
>
> :exclamation: Otherwise you will get a Not authorized exception.


## :key: Create ClientId and ClientSecret on Google Cloud Platform
For generate ClientId and ClientSecret, see these documentations:

* [Java Quickstart](https://developers.google.com/calendar/api/quickstart/java)
* [Create a Google Cloud project](https://developers.google.com/workspace/guides/create-project)
* [Create access credentials](https://developers.google.com/workspace/guides/create-credentials)
  * Create an OAuth client ID: App Type Desktop;
  * Download the json file;
  * Rename the file to `google-credentials.json`;
  * Copy the file to `resources` folder;
  
> * Remember to add your gmail in `Test Users` in your Project in Google Cloud Console
> * Add following scopes in your app:
>   * `https://www.googleapis.com/auth/calendar`
>   * `https://www.googleapis.com/auth/calendar.events`


## :bulb: How to Use this Lib in your project
In your Application Class add:
```java
@SpringBootApplication
@ComponentScan({"<your-path>", "com.bvilela.lib"})
public class Application {

}
```
This make your Spring Application recognise the Spring Services of lib.

To create a event use:
```java
import com.bvilela.lib.enuns.ColorEnum;
import com.bvilela.lib.model.CalendarEvent;
import com.bvilela.lib.service.GoogleCalendarService;

@Autowired
private GoogleCalendarService calendarService;

CalendarEvent dto = CalendarEvent.builder()
		.setSummary("My Event Title")
		.setLocation("My Event Location")
		.setDescription("My Event Description")
		.setDateTimeStart(LocalDateTime.of(2022, 6, 20, 20, 0))
		.setDateTimeEnd(LocalDateTime.of(2022, 6, 20, 21, 0))
		.setColor(ColorEnum.SALVIA) // Default: ColorEnum.PADRAO
		.setTimeZone("America/Sao_Paulo") // Default: "America/Sao_Paulo"
		.build();

calendarService.createEvent(dto);
// or
calendarService.createEvents(List.of(dto1, dto2...));
```

## :memo: Enabled Default Log

To enable default logging of events sent to Google Calendar, follow these steps:
1. Add `com.bvilela.lib.google.calendar.logging.event=true` in your `application.properties`, as below:
```properties
### Logging ###
com.bvilela.lib.google.calendar.logging.event=true
```

2. Use the correct methods:
```java
import org.slf4j.Logger;
import com.bvilela.lib.model.CalendarEvent;

calendarService.createEvent(calendarEventDto, log); // log is a org.slf4j.Logger instance
// or
calendarService.createEvents(listCalendarEventDto, log); // log is a org.slf4j.Logger instance
```

**Example Default Log Output**
```text
Sending Event to Google Calendar...
CalendarEvent[Summary=myEventTitle, Dates=(2022-07-19T21:30 - 2022-07-19T22:00), Color=SALVIA]
```

[⬆ Voltar ao topo](#google-calendar-util-lib)<br>
