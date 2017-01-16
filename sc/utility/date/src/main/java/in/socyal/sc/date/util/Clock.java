package in.socyal.sc.date.util;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public interface Clock {
	long millis();
	TimeZone getTimeZone();
	Calendar cal();
	Calendar cal(TimeZone timeZone, Locale locale);
	String getCurrentDateTime();
	Date getCurrentDate();
}
