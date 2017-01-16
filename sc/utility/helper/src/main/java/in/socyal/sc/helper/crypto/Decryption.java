package in.socyal.sc.helper.crypto;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

import javax.crypto.Cipher;

import org.springframework.stereotype.Component;

@Component
public class Decryption {
	/**
	 * String to hold the name of the private key file.
	 */
	public static final String PRIVATE_KEY_FILE = "C:/Users/svalapi/Desktop/ppvt/private.txt";
	public static final String ALGORITHM = "RSA";

	public String decryptMessage(byte[] cipherText) {
		// Decrypt the cipher text using the private key.
		PrivateKey privateKey = null;
		ObjectInputStream inputStream = null;
		try {
			inputStream = new ObjectInputStream(new FileInputStream(PRIVATE_KEY_FILE));
			// privateKey = (PrivateKey) inputStream.readObject();
			byte[] byteKey = Base64.getDecoder().decode(inputStream.readUTF().getBytes());
			PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(byteKey);
			KeyFactory kf = KeyFactory.getInstance(ALGORITHM);
			privateKey = kf.generatePrivate(keySpec);
			final String plainText = decrypt(cipherText, privateKey);
			System.out.println("Decrypted: " + plainText);
			return plainText;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	/**
	 * Decrypt text using private key.
	 *
	 * @param text
	 *            :encrypted text
	 * @param key
	 *            :The private key
	 * @return plain text
	 * @throws java.lang.Exception
	 */
	public String decrypt(byte[] text, PrivateKey key) {
		byte[] dectyptedText = null;
		try {
			final Cipher cipher = Cipher.getInstance(ALGORITHM);

			// decrypt the text using the private key
			cipher.init(Cipher.DECRYPT_MODE, key);
			dectyptedText = cipher.doFinal(text);

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return new String(dectyptedText);
	}
}
