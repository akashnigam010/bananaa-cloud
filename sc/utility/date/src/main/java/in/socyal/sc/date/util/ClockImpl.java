package in.socyal.sc.date.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import org.springframework.stereotype.Component;

import in.socyal.sc.date.type.DateFormatType;

@Component
public class ClockImpl implements Clock {
	private static final String IST_TIMEZONE = "IST";
	synchronized void init() {
		// initTimeZone();
		// TODO : init some time zones here
	}

	@Override
	public long millis() {
		return System.currentTimeMillis();
	}

	@Override
	public TimeZone getTimeZone() {
		return null;
	}

	@Override
	public Calendar cal() {
		TimeZone.setDefault(TimeZone.getTimeZone(IST_TIMEZONE));
		return cal(TimeZone.getDefault(), Locale.getDefault());
	}
	
	@Override
	public Calendar cal(TimeZone timeZone, Locale locale) {
		return Calendar.getInstance(timeZone, locale);
	}
	
	@Override
	public String getCurrentDateTime() {
		SimpleDateFormat dateFormat = new SimpleDateFormat(DateFormatType.ISO_DATE_FORMAT.getByFormat());
		return dateFormat.format(cal().getTime());
	}

	@Override
	public Date getCurrentDate() {
		return cal().getTime();
	}
}
