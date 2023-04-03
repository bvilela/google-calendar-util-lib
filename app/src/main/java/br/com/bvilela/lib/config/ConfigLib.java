package br.com.bvilela.lib.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class ConfigLib {

    @Value("${com.bvilela.lib.google.calendar.logging.event:false}")
    private boolean showLog;

    @Value("${com.bvilela.lib.google.calendar.path.credentials:#{null}}")
    private String pathCredentials;

}
