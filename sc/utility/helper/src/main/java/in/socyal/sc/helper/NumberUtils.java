package in.socyal.sc.helper;

import org.springframework.stereotype.Component;

@Component
public class NumberUtils {

	public Float toFloatOneDecimal(Double d) {
		Double newD = Math.round(d * 10.0) / 10.0;
		return newD.floatValue();
	}
}
