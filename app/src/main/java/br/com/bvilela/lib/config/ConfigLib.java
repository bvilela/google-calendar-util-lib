package br.com.bvilela.lib.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class ConfigLib {

    @Value("${bvilela.lib.google.calendar.log.enabled:false}")
    private boolean logEnabled;

    @Value("${bvilela.lib.google.calendar.credentials.path:#{null}}")
    private String credentialsPath;

}
