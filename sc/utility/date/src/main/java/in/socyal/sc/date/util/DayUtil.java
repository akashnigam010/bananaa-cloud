package in.socyal.sc.date.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import in.socyal.sc.date.type.DateFormatType;

/**
 * CwfDayUtil - contains day time related methods
 * 
 */
@Component
public class DayUtil {
	@Autowired
	Clock cwfClock;

	/**
	 * Returns todays calendar object
	 * 
	 * @return
	 */
	public Calendar today() {
		return clearTime(cwfClock.cal());
	}

	public boolean equals(Calendar cal, Object obj) {
		if (!(obj instanceof Calendar)) {
			return false;
		}
		return (compare(cal, (Calendar) obj) == 0);
	}

	public int compare(Calendar cal, Calendar other) {
		return clearTime(cal).compareTo(clearTime(other));
	}

	/**
	 * Returns difference between two dates
	 * 
	 * @param cal
	 * @param other
	 * @return
	 */
	public int daysBetween(Calendar cal, Calendar other) {
		Calendar day1 = clearTime(cal);
		Calendar day2 = clearTime(other);
		long duration = day2.getTimeInMillis() - day1.getTimeInMillis();
		return (int) TimeUnit.DAYS.convert(duration, TimeUnit.MILLISECONDS);
	}

	public Calendar clearTime(Calendar cal) {
		return (cal == null) ? cal : setClearTime((Calendar) cal.clone());
	}

	/**
	 * Returns abs difference between two dates
	 * 
	 * @param cal
	 * @param other
	 * @return
	 */
	public int daysBetweenAbs(Calendar cal, Calendar other) {
		return Math.abs(daysBetween(cal, other));
	}

	/**
	 * Returns True, if other date is future date
	 * 
	 * @param cal
	 * @param other
	 * @return
	 */
	public boolean isAfter(Calendar cal, Calendar other) {
		return compare(cal, other) > 0;
	}

	/**
	 * Returns True if given calendar is current date
	 * 
	 * @param cal
	 * @return boolean
	 */
	public boolean isToday(Calendar cal) {
		return compare(cal, cwfClock.cal()) == 0;
	}

	public Calendar setClearTime(Calendar cal) {
		if (cal != null) {
			cal.clear(Calendar.DST_OFFSET);
			cal.clear(Calendar.ZONE_OFFSET);
			cal.clear(Calendar.MILLISECOND);
			cal.clear(Calendar.SECOND);
			cal.clear(Calendar.MINUTE);
			cal.clear(Calendar.HOUR_OF_DAY);
			cal.clear(Calendar.HOUR);
			cal.clear(Calendar.AM_PM);
		}
		return cal;
	}

	public String formatDate(Calendar unFormattedDate, DateFormatType format) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(format.getValue());
		return dateFormat.format(unFormattedDate.getTime());
	}
	
	public String formatDate(Date unFormattedDate, DateFormatType format) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(format.getValue());
		return dateFormat.format(unFormattedDate.getTime());
	}
	
	public Date parseDate(String dateStr, DateFormatType format) throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat(format.getValue());
		return dateFormat.parse(dateStr);
	}
	
	/**
	 * This method formats calendar instance to set HH:MM:SS as 00:00:00
	 * @param cal
	 * @return
	 */
	public static Calendar initialTimeOfDate(Calendar cal) {
        cal.set(Calendar.HOUR_OF_DAY, 00);
        cal.set(Calendar.MINUTE, 00);
        cal.set(Calendar.SECOND, 00);
        cal.set(Calendar.MILLISECOND, 00);
        return cal;
    }
}
