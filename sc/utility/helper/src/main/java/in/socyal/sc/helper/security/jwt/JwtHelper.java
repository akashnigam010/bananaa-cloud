package in.socyal.sc.helper.security.jwt;

import java.security.InvalidKeyException;
import java.security.SignatureException;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;

import com.google.common.collect.Lists;
import com.google.gson.JsonObject;

import net.oauth.jsontoken.JsonToken;
import net.oauth.jsontoken.JsonTokenParser;
import net.oauth.jsontoken.crypto.HmacSHA256Signer;
import net.oauth.jsontoken.crypto.HmacSHA256Verifier;
import net.oauth.jsontoken.crypto.SignatureAlgorithm;
import net.oauth.jsontoken.crypto.Verifier;
import net.oauth.jsontoken.discovery.VerifierProvider;
import net.oauth.jsontoken.discovery.VerifierProviders;

public class JwtHelper {
	private static final String AUDIENCE = "CantWait Sense Applications";
	private static final String ISSUER = "ASVYS PVT LTD";
	private static final String SIGNING_KEY = "LongAndHardToGuessValueWithSpecialCharacters";

	/**
	 * Creates a JSON Web Token which is digitally signed token and contains a
	 * payload (e.g. userId to identify the user). The signing key is secret
	 * which ensures that the token is authentic and has not been modified.
	 * Using a JWT eliminates the need to store authentication session
	 * information in a database.
	 * 
	 * @param name
	 * @param durationDays
	 * @return
	 */
	public static String createJsonWebToken(String name, String role, Long durationDays) {
		// Current time and signing algorithm
		Calendar cal = Calendar.getInstance();
		HmacSHA256Signer signer;
		try {
			signer = new HmacSHA256Signer(ISSUER, null, SIGNING_KEY.getBytes());
		} catch (InvalidKeyException e) {
			throw new RuntimeException(e);
		}
		// Configure JSON token
		JsonToken token = new net.oauth.jsontoken.JsonToken(signer);
		token.setAudience(AUDIENCE);
		token.setIssuedAt(new org.joda.time.Instant(cal.getTimeInMillis()));
		token.setExpiration(new org.joda.time.Instant(cal.getTimeInMillis() + 1000L * 60L * 60L * 24L * durationDays));
		// Configure request object, which provides information of the item
		JsonObject request = new JsonObject();
		request.addProperty("userId", name);
		request.addProperty("role", role);
		JsonObject payload = token.getPayloadAsJsonObject();
		payload.add("info", request);
		try {
			return token.serializeAndSign();
		} catch (SignatureException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Verifies a JSON Web Token's validity and extracts the userId and other
	 * information from it.
	 * 
	 * @param token
	 * @return
	 * @throws SignatureException
	 * @throws InvalidKeyException
	 */
	public static TokenInfo verifyToken(String token) {
		try {
			final Verifier hmacVerifier = new HmacSHA256Verifier(SIGNING_KEY.getBytes());
			VerifierProvider hmacLocator = new VerifierProvider() {
				@Override
				public List<Verifier> findVerifier(String id, String key) {
					return Lists.newArrayList(hmacVerifier);
				}
			};
			VerifierProviders locators = new VerifierProviders();
			locators.setVerifierProvider(SignatureAlgorithm.HS256, hmacLocator);
			net.oauth.jsontoken.Checker checker = new net.oauth.jsontoken.Checker() {
				@Override
				public void check(JsonObject payload) throws SignatureException {
				}
			};
			// Ignore Audience does not mean that the Signature is ignored
			JsonTokenParser parser = new JsonTokenParser(locators, checker);
			JsonToken jt;
			try {
				jt = parser.verifyAndDeserialize(token);
			} catch (SignatureException e) {
				throw new RuntimeException(e);
			}
			JsonObject payload = jt.getPayloadAsJsonObject();
			TokenInfo tokenInfo = new TokenInfo();
			String issuer = payload.getAsJsonPrimitive("iss").getAsString();
			String userIdString = payload.getAsJsonObject("info").getAsJsonPrimitive("userId").getAsString();
			String roleString = payload.getAsJsonObject("info").getAsJsonPrimitive("role").getAsString();
			if (issuer.equals(ISSUER) && StringUtils.isNotBlank(userIdString) && StringUtils.isNotBlank(roleString)) {
				tokenInfo.setUserId(userIdString);
				tokenInfo.setRole(roleString);
				tokenInfo.setIssued(new DateTime(payload.getAsJsonPrimitive("iat").getAsLong()));
				tokenInfo.setExpires(new DateTime(payload.getAsJsonPrimitive("exp").getAsLong()));
				return tokenInfo;
			}
			return null;
		} catch (InvalidKeyException e1) {
			throw new RuntimeException(e1);
		}
	}
}
