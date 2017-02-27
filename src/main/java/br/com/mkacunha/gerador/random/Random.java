package br.com.mkacunha.gerador.random;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class Random extends java.util.Random {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public int nextInt(int min, int max) {
		return nextInt((max - min) + 1) + min;
	}

	public BigDecimal nextMonetaryValue(int min, int max) {
		int value = nextInt(min, max);
		int decimal = nextInt(100);

		return new BigDecimal(String.valueOf(value) + "." + String.valueOf(decimal));
	}
	
	public Date date(int minYear, int maxYear) {
		try {
			GregorianCalendar gc = new GregorianCalendar();

			int year = nextInt(minYear, maxYear);

			gc.set(Calendar.YEAR, year);

			int dayOfYear = nextInt(1, gc.getActualMaximum(Calendar.DAY_OF_YEAR));

			gc.set(Calendar.DAY_OF_YEAR, dayOfYear);

			return new SimpleDateFormat("dd/mm/yyyy").parse(
					gc.get(Calendar.DAY_OF_MONTH) + "/" + (gc.get(Calendar.MONTH) + 1) + "/" + gc.get(Calendar.YEAR));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
