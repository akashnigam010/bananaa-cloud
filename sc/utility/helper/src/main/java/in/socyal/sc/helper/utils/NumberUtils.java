package in.socyal.sc.helper.utils;

import org.springframework.stereotype.Component;

/**
 * The Class NumberUtils.
 */
@Component
public class NumberUtils {

	/**
	 * Round to two decimal places.
	 *
	 * @param value
	 *            the value
	 * @return the double
	 */
	public double roundToTwoDecimalPlaces(double value) {
		return Math.round(value * 100.0) / 100.0;
	}

	/**
	 * Round the value to the next closest value
	 *
	 * Ex : 1205.1 will be converted to 1205
	 *
	 * 1245.67 will be converted to 1246
	 *
	 * @param value
	 *            the value
	 * @return the int
	 */
	public int round(double value) {
		return (int) Math.round(value);
	}
}
