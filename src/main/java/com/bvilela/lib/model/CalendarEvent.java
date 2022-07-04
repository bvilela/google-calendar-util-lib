package com.bvilela.lib.model;

import java.time.LocalDateTime;

import com.bvilela.lib.enuns.ColorEnum;

import lombok.Builder;
import lombok.Getter;

@Builder(setterPrefix = "set")
@Getter
public class CalendarEvent {

	private String summary;
	private String location;
	private String description;
	private LocalDateTime dateTimeStart;
	private LocalDateTime dateTimeEnd;

	@Builder.Default
	private String timeZone = "America/Sao_Paulo";
	
	@Builder.Default
	private ColorEnum color = ColorEnum.PADRAO;
}
