package in.socyal.sc.date.util;

import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

@Component
public class TimezoneConfig {

	@PostConstruct
	public void setDefaultTimeZone() {
		TimeZone.setDefault(TimeZone.getTimeZone("IST"));
		System.out.println("Application TimeZone set to IST");
	}
}
