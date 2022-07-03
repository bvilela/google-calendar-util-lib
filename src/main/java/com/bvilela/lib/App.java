package com.bvilela.lib;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class App {
	
	public static void main(String[] args) {
		System.out.println("Init!");
		
		CalendarQuickstart calendar = new CalendarQuickstart();
		try {
//			calendar.call();
			calendar.createEvent();
		} catch (IOException | GeneralSecurityException e) {
			e.printStackTrace();
		}
		
		System.out.println("Finish!");
	}
	
}
