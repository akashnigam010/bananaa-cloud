package in.socyal.sc.helper.utils;

import static java.lang.Math.abs;
import static java.lang.Math.min;
import static java.lang.Math.pow;
import static java.lang.Math.random;
import static java.lang.Math.round;
import static org.apache.commons.lang3.StringUtils.leftPad;

public class RandomStringUtil {
	private static final int RANDOM_PASSWORD_LENGTH = 10;
	private static final int RANDOM_KEY_LENGTH = 15;

	public static String generateRandomPassword() {
		return generateRandomString(RANDOM_PASSWORD_LENGTH);
	}

	public static String generateRandomPublicKey() {
		return generateRandomString(RANDOM_KEY_LENGTH);
	}

	public static String generateRandomPrivateKey() {
		return generateRandomString(RANDOM_KEY_LENGTH);
	}

	private static String generateRandomString(int length) {
		StringBuffer sb = new StringBuffer();
		for (int i = length; i > 0; i -= 12) {
			int n = min(12, abs(i));
			sb.append(leftPad(Long.toString(round(random() * pow(36, n)), 36), n, '0'));
		}
		return sb.toString();
	}
}