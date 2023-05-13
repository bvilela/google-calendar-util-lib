package br.com.bvilela.lib.model;

import java.time.LocalDateTime;

import br.com.bvilela.lib.enuns.ColorEnum;

import br.com.bvilela.lib.exception.GoogleCalendarLibException;
import lombok.Builder;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.ToString;

@ToString
@Getter
@Builder(setterPrefix = "set")
public class CalendarEvent {

    private String summary;
    private String location;
    private String description;
    private LocalDateTime dateTimeStart;
    private LocalDateTime dateTimeEnd;

    @Builder.Default private String timeZone = "America/Sao_Paulo";

    @Builder.Default private ColorEnum color = ColorEnum.PADRAO;

    public String toStringSummary() {
        return String.format(
                "CalendarEvent[Summary=%s, Dates=(%s - %s), Color=%s]",
                this.summary, this.dateTimeStart, this.dateTimeEnd, this.color);
    }

    @SneakyThrows
    public void validate() {
        if (this.summary == null || this.summary.isEmpty() || this.summary.trim().isEmpty()) {
            throw new GoogleCalendarLibException("Summary is a required field!");
        }

        if (this.dateTimeStart == null) {
            throw new GoogleCalendarLibException("DateTimeStart is a required field!");
        }

        if (this.dateTimeEnd == null) {
            throw new GoogleCalendarLibException("DateTimeEnd is a required field!");
        }
    }
}
