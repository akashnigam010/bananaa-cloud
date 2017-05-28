package in.socyal.sc.date.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Component;

import in.socyal.sc.date.type.DateFormatType;

@Component
public class DateTimeUtil {

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
}
