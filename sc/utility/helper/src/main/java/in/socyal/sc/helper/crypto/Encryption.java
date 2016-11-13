package in.socyal.sc.helper.crypto;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.Cipher;

import org.springframework.stereotype.Component;

@Component
public class Encryption {

	public static final String ALGORITHM = "RSA";

	public static final String PUBLIC_KEY_FILE = "C:/Users/svalapi/Desktop/ppvt/public.txt";

	public byte[] encrypt(String originalText) {
		ObjectInputStream inputStream = null;
		PublicKey publicKey = null;
		try {
			// Encrypt the string using the public key
			inputStream = new ObjectInputStream(new FileInputStream(PUBLIC_KEY_FILE));
			byte[] byteKey = Base64.getDecoder().decode(inputStream.readUTF().getBytes());
			X509EncodedKeySpec X509publicKey = new X509EncodedKeySpec(byteKey);
			KeyFactory kf = KeyFactory.getInstance(ALGORITHM);
			publicKey = kf.generatePublic(X509publicKey);
			final byte[] cipherText = encrypt(originalText, publicKey);
			System.out.println("Encrypted: " + new String(Base64.getEncoder().encode(cipherText)));
			return cipherText;
		} catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException e) {
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
	 * Encrypt the plain text using public key.
	 *
	 * @param text
	 *            : original plain text
	 * @param key
	 *            :The public key
	 * @return Encrypted text
	 * @throws java.lang.Exception
	 */
	public byte[] encrypt(String text, PublicKey key) {
		byte[] cipherText = null;
		try {
			// get an RSA cipher object and print the provider
			final Cipher cipher = Cipher.getInstance(ALGORITHM);
			// encrypt the plain text using the public key
			cipher.init(Cipher.ENCRYPT_MODE, key);
			cipherText = cipher.doFinal(text.getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cipherText;
	}
}
