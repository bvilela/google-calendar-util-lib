# Google Calendar Util Lib

### :dart: Quality Status
[![build](https://github.com/bvilela/google-calendar-util-lib/actions/workflows/maven_ci_cd.yml/badge.svg?branch=master)](https://github.com/bvilela/google-calendar-util-lib/actions/workflows/maven_ci_cd.yml)
[![publish](https://github.com/bvilela/google-calendar-util-lib/actions/workflows/maven_ci_cd_publish.yml/badge.svg)](https://github.com/bvilela/google-calendar-util-lib/actions/workflows/maven_ci_cd_publish.yml)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=bvilela_google-calendar-util-lib&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=bvilela_google-calendar-util-lib)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=bvilela_google-calendar-util-lib&metric=coverage)](https://sonarcloud.io/summary/new_code?id=bvilela_google-calendar-util-lib)

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


## :key: Create ClientId and ClientSecret on Google Cloud Plataform
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


[⬆ Voltar ao topo](#google-calendar-util-lib)<br>
