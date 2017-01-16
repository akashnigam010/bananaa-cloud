package in.socyal.sc.helper.hash;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashUtil {

	private static String ENCRYPTION_TYPE = "SHA-256";

	/**
	 * Return Hashed code for given string Creating MessageDigest instance for
	 * 'SHA-265' type
	 *
	 * @param textToBeHashed
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public static String hash(String textToBeHashed) throws NoSuchAlgorithmException {
		MessageDigest message = MessageDigest.getInstance(ENCRYPTION_TYPE);
		message.update(textToBeHashed.getBytes());
		byte data[] = message.digest();
		StringBuffer encrypted = new StringBuffer();
		for (int each = 0; each < data.length; each++) {
			encrypted.append(Integer.toString((data[each] & 0xff) + 0x100, 16).substring(1));
		}
		return encrypted.toString();
	}
}
