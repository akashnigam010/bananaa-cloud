package in.socyal.sc.helper.crypto;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import org.springframework.stereotype.Component;

/**
 * @author JavaDigest
 *
 */
@Component
public class KeyUtil {

	/**
	 * String to hold name of the encryption algorithm.
	 */
	public static final String RSA_ALGORITHM = "RSA";

	/**
	 * Replace with project location. TODO : Replace hard coded file location to
	 * any specific location.
	 */
	public static final String PUBLIC_KEY_FILE = "C:/Users/svalapi/Desktop/ppvt/public.txt";

	public static final String PRIVATE_KEY_FILE = "C:/Users/svalapi/Desktop/ppvt/private.txt";

	/**
	 * Generate key which contains a pair of private and public key using 1024
	 * bytes. Store the set of keys in Prvate.key and Public.key files.
	 *
	 * @throws NoSuchAlgorithmException
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public static void generateKey() {
		try {
			final KeyPairGenerator keyGen = KeyPairGenerator.getInstance(RSA_ALGORITHM);
			keyGen.initialize(1024);
			final KeyPair key = keyGen.generateKeyPair();

			File privateKeyFile = new File(PRIVATE_KEY_FILE);
			File publicKeyFile = new File(PUBLIC_KEY_FILE);

			// Create files to store public and private key
			if (privateKeyFile.getParentFile() != null) {
				privateKeyFile.getParentFile().mkdirs();
			}
			privateKeyFile.createNewFile();

			if (publicKeyFile.getParentFile() != null) {
				publicKeyFile.getParentFile().mkdirs();
			}
			publicKeyFile.createNewFile();

			// Saving the Public key in a file
			ObjectOutputStream publicKeyOS = new ObjectOutputStream(new FileOutputStream(publicKeyFile));
			// publicKeyOS.writeObject(key.getPublic());
			publicKeyOS.writeUTF(new String(Base64.getEncoder().encode(key.getPublic().getEncoded())));
			publicKeyOS.close();

			// Saving the Private key in a file
			ObjectOutputStream privateKeyOS = new ObjectOutputStream(new FileOutputStream(privateKeyFile));
			privateKeyOS.writeUTF(new String(Base64.getEncoder().encode(key.getPrivate().getEncoded())));
			privateKeyOS.close();

			System.out.println("Public key : " + new String(Base64.getEncoder().encode(key.getPublic().getEncoded())));
			System.out
					.println("Private key : " + new String(Base64.getEncoder().encode(key.getPrivate().getEncoded())));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * The method checks if the pair of public and private key has been
	 * generated.
	 *
	 * @return flag indicating if the pair of keys were generated.
	 */
	public static boolean areKeysPresent() {

		File privateKey = new File(PRIVATE_KEY_FILE);
		File publicKey = new File(PUBLIC_KEY_FILE);

		if (privateKey.exists() && publicKey.exists()) {
			return true;
		}
		return false;
	}

	/**
	 * Sample example to test. call validatekeys() from any main class to test
	 *
	 * byte[] encryptedValue = HashUtil.encrypt(String);
	 *
	 * Decryption.decryptMessage(encryptedValue);
	 *
	 * @return true if keys have created
	 */
	public boolean validateKeys(String stringToEncrypt) {

		/*
		 * Check if the pair of public and private pins exists
		 *
		 * method:generateKey() Method generates a pair of keys using the RSA
		 * algorithm and stores it in their respective files
		 */
		if (!areKeysPresent()) {
			System.out.println("Generating Keys..");
			generateKey();
		} else {
			System.out.println("Using old keys..");
		}
		Encryption encrypt = new Encryption();

		Decryption decrypt = new Decryption();
		decrypt.decryptMessage(encrypt.encrypt(stringToEncrypt));
		return true;
	}

}
