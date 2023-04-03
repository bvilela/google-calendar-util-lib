package br.com.bvilela.lib.enuns;

import lombok.Getter;

public enum ColorEnum {

	PADRAO("0"),
	LAVANDA("1"),
	SALVIA("2"),
	UVA("3"),
	FLAMINGO("4"),
	BANANA("5"),
	TANGERINA("6"),
	PAVAO("7"),
	GRAFITE("8"),
	MIRTILO("9"),
	MANJERICAO("10"),
	TOMATE("11");
	
	@Getter
	private String colorId;
	
	ColorEnum(String colorId) {
		this.colorId = colorId;
	}
	
}
