package cz.cuni.mff.d3s.jdeeco.cloudsimulator.common;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.util.Date;

public class ExtendedDateFormat extends DateFormat {

	private static final long serialVersionUID = -7968577008954400425L;

	private final DateFormat dateFormat;

	public ExtendedDateFormat(DateFormat dateFormat) {
		this.dateFormat = dateFormat;
	}
	
	public ExtendedDateFormat(DateFormat dateFormat, boolean isLenient) {
		this(dateFormat);
		
		this.dateFormat.setLenient(isLenient);
	}
	
	@Override
	public StringBuffer format(Date date, StringBuffer toAppendTo, FieldPosition fieldPosition) {
		return dateFormat.format(date, toAppendTo, fieldPosition);
	}

	@Override
	public Date parse(String source, ParsePosition pos) {
		return dateFormat.parse(source, pos);
	}

	public boolean tryParse(String source, Out<Date> date) {
		try {
			date.set(parse(source));
			return true;
		} catch (Exception e) {
			date.set(null);
			return false;
		}
	}
}
