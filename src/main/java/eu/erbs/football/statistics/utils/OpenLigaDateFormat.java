package eu.erbs.football.statistics.utils;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.util.Calendar;
import java.util.Date;

public class OpenLigaDateFormat extends DateFormat {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public StringBuffer format(Date date, StringBuffer toAppendTo, FieldPosition fieldPosition) {
		toAppendTo.append(date.getTime());
		return toAppendTo;
	}

	@Override
	public Date parse(String source, ParsePosition pos) {
		pos.setIndex(-1);
		String[] parts = source.split("[-T:]");
		Calendar calendar = Calendar.getInstance();
		calendar.set(Integer.valueOf(parts[0]), Integer.valueOf(parts[1])-1, Integer.valueOf(parts[2]), Integer.valueOf(parts[3]), Integer.valueOf(parts[4]), 0);
		return calendar.getTime();
	}

}
