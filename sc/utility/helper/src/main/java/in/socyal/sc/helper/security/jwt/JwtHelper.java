package in.socyal.sc.helper.security.jwt;

import java.security.InvalidKeyException;
import java.security.SignatureException;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.jboss.logging.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;

import com.google.common.collect.Lists;
import com.google.gson.JsonObject;

import in.socyal.sc.api.helper.exception.BusinessException;
import in.socyal.sc.api.type.RoleType;
import in.socyal.sc.api.type.error.GenericErrorCodeType;
import net.oauth.jsontoken.JsonToken;
import net.oauth.jsontoken.JsonTokenParser;
import net.oauth.jsontoken.crypto.HmacSHA256Signer;
import net.oauth.jsontoken.crypto.HmacSHA256Verifier;
import net.oauth.jsontoken.crypto.SignatureAlgorithm;
import net.oauth.jsontoken.crypto.Verifier;
import net.oauth.jsontoken.discovery.VerifierProvider;
import net.oauth.jsontoken.discovery.VerifierProviders;

@Configuration
@PropertySource(value = {"classpath:bananaa-application.properties"})
public class JwtHelper {
	private static final Logger LOG = Logger.getLogger(JwtHelper.class);
	private static final String SIGNING_KEY = "LongAndHardToGuessValueWithSpecialCharacters";
	private static String userTokenExpiry;
	private static String guestTokenExpiry;
	private static String merchantTokenExpiry;
	private static String audience;
	private static String issuer;
	
	@Value("${jwt.token.user.expiry}")
	public void setUserTokenExpiry(String expiry) {
		JwtHelper.userTokenExpiry = expiry;
	}
	
	
	@Value("${jwt.token.guest.expiry}")
	public void setGuestTokenExpiry(String expiry) {
		JwtHelper.guestTokenExpiry = expiry;
	}
	
	@Value("${jwt.token.merchant.expiry}")
	public void setMerchantTokenExpiry(String expiry) {
		JwtHelper.merchantTokenExpiry = expiry;
	}
	
	@Value("${jwt.audience}")
	public void setAudience(String audience) {
		JwtHelper.audience = audience;
	}
	
	@Value("${jwt.issuer}")
	public void setIssuer(String issuer) {
		JwtHelper.issuer = issuer;
	}

	/**
	 * Creates a JSON Web Token which is digitally signed token and contains a
	 * payload (e.g. userId to identify the user). The signing key is secret
	 * which ensures that the token is authentic and has not been modified.
	 * Using a JWT eliminates the need to store authentication session
	 * information in a database.
	 * 
	 * @param userId
	 * @param durationInDays
	 * @return
	 * @throws BusinessException 
	 */
	public static String createJsonWebTokenForUser(String userId) throws BusinessException {
		// Current time and signing algorithm
		Calendar cal = Calendar.getInstance();
		HmacSHA256Signer signer;
		try {
			signer = new HmacSHA256Signer(issuer, null, SIGNING_KEY.getBytes());
		} catch (InvalidKeyException e) {
			LOG.error("Exception occured while creating JWT token ", e);
			throw new BusinessException(GenericErrorCodeType.GENERIC_ERROR);
		}
		// Configure JSON token
		JsonToken token = new net.oauth.jsontoken.JsonToken(signer);
		token.setAudience(audience);
		token.setIssuedAt(new org.joda.time.Instant(cal.getTimeInMillis()));
		token.setExpiration(new org.joda.time.Instant(cal.getTimeInMillis() + 1000L * Long.valueOf(userTokenExpiry)));
		// Configure request object, which provides information of the item
		JsonObject request = new JsonObject();
		request.addProperty("userId", userId);
		request.addProperty("role", RoleType.USER.getRole());
		JsonObject payload = token.getPayloadAsJsonObject();
		payload.add("info", request);
		try {
			return token.serializeAndSign();
		} catch (SignatureException e) {
			LOG.error("Exception occured while serializing and signing JWT token ", e);
			throw new BusinessException(GenericErrorCodeType.GENERIC_ERROR);
		}
	}
	
	public static String createJsonWebTokenForGuest() throws BusinessException {
		// Current time and signing algorithm
		Calendar cal = Calendar.getInstance();
		HmacSHA256Signer signer;
		try {
			signer = new HmacSHA256Signer(issuer, null, SIGNING_KEY.getBytes());
		} catch (InvalidKeyException e) {
			LOG.error("Exception occured while creating JWT token ", e);
			throw new BusinessException(GenericErrorCodeType.GENERIC_ERROR);
		}
		// Configure JSON token
		JsonToken token = new net.oauth.jsontoken.JsonToken(signer);
		token.setAudience(audience);
		token.setIssuedAt(new org.joda.time.Instant(cal.getTimeInMillis()));
		token.setExpiration(new org.joda.time.Instant(cal.getTimeInMillis() + 1000L * Long.valueOf(userTokenExpiry)));
		// Configure request object, which provides information of the item
		JsonObject request = new JsonObject();
		request.addProperty("role", RoleType.GUEST.getRole());
		JsonObject payload = token.getPayloadAsJsonObject();
		payload.add("info", request);
		try {
			return token.serializeAndSign();
		} catch (SignatureException e) {
			LOG.error("Exception occured while serializing and signing JWT token ", e);
			throw new BusinessException(GenericErrorCodeType.GENERIC_ERROR);
		}
	}
	
	public static String createJsonWebTokenForMerchant(String deviceId, String merchantId) throws BusinessException {
		// Current time and signing algorithm
		Calendar cal = Calendar.getInstance();
		HmacSHA256Signer signer;
		try {
			signer = new HmacSHA256Signer(issuer, null, SIGNING_KEY.getBytes());
		} catch (InvalidKeyException e) {
			LOG.error("Exception occured while creating JWT token ", e);
			throw new BusinessException(GenericErrorCodeType.GENERIC_ERROR);
		}
		// Configure JSON token
		JsonToken token = new net.oauth.jsontoken.JsonToken(signer);
		token.setAudience(audience);
		token.setIssuedAt(new org.joda.time.Instant(cal.getTimeInMillis()));
		token.setExpiration(new org.joda.time.Instant(cal.getTimeInMillis() + 1000L * Long.valueOf(userTokenExpiry)));
		// Configure request object, which provides information of the item
		JsonObject request = new JsonObject();
		request.addProperty("deviceId", deviceId);
		request.addProperty("merchantId", merchantId);
		request.addProperty("role", RoleType.MERCHANT.getRole());
		JsonObject payload = token.getPayloadAsJsonObject();
		payload.add("info", request);
		try {
			return token.serializeAndSign();
		} catch (SignatureException e) {
			LOG.error("Exception occured while serializing and signing JWT token ", e);
			throw new BusinessException(GenericErrorCodeType.GENERIC_ERROR);
		}
	}

	/**
	 * Verifies a JSON Web Token's validity and extracts the userId and other
	 * information from it.
	 * 
	 * @param token
	 * @return
	 * @throws BusinessException 
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
				LOG.error("Exception occured while verifying and deserializing JWT token ", e);
				throw new AuthenticationCredentialsNotFoundException("Token expired! Kindly login again");
			}
			JsonObject payload = jt.getPayloadAsJsonObject();
			TokenInfo tokenInfo = new TokenInfo();
			String issuer = payload.getAsJsonPrimitive("iss").getAsString();
			String roleString = payload.getAsJsonObject("info").getAsJsonPrimitive("role").getAsString();
			if (issuer.equals(issuer) && StringUtils.isNotBlank(roleString)) {
				if (RoleType.USER == RoleType.getRole(roleString)) {
					String userIdString = payload.getAsJsonObject("info").getAsJsonPrimitive("userId").getAsString();
					tokenInfo.setUserId(userIdString);
					tokenInfo.setRole(roleString);
					tokenInfo.setIssued(new DateTime(payload.getAsJsonPrimitive("iat").getAsLong()));
					tokenInfo.setExpires(new DateTime(payload.getAsJsonPrimitive("exp").getAsLong()));
					return tokenInfo;
				} else if (RoleType.GUEST == RoleType.getRole(roleString)) {
					tokenInfo.setRole(roleString);
					tokenInfo.setIssued(new DateTime(payload.getAsJsonPrimitive("iat").getAsLong()));
					tokenInfo.setExpires(new DateTime(payload.getAsJsonPrimitive("exp").getAsLong()));
					return tokenInfo;
				//this condition is for setting deviceId and merchantId in JWT token
				} else {
					String deviceIdString = payload.getAsJsonObject("info").getAsJsonPrimitive("deviceId").getAsString();
					String merchantIdString = payload.getAsJsonObject("info").getAsJsonPrimitive("merchantId").getAsString();
					tokenInfo.setDeviceId(deviceIdString);
					tokenInfo.setMerchantId(merchantIdString);
					tokenInfo.setRole(roleString);
					tokenInfo.setIssued(new DateTime(payload.getAsJsonPrimitive("iat").getAsLong()));
					tokenInfo.setExpires(new DateTime(payload.getAsJsonPrimitive("exp").getAsLong()));
					return tokenInfo;
				}
			}
			return null;
		} catch (InvalidKeyException e) {
			LOG.error("Exception occured for invalid key while verifying JWT token ", e);
			throw new AuthenticationCredentialsNotFoundException("Token expired! Kindly login again");
		}
	}
	
	public static void main(String args[]) throws BusinessException {
		System.out.println(createJsonWebTokenForUser("5"));
	}
	
	/*public static String createJWT(String id, String issuer, String subject, long ttlMillis) {

	// The JWT signature algorithm we will be using to sign the token
	SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

	long nowMillis = System.currentTimeMillis();
	Date now = new Date(nowMillis);

	// We will sign our JWT with our ApiKey secret
	byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary("Sample");
	Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

	// Let's set the JWT Claims
	JwtBuilder builder = Jwts.builder().setId(id).setIssuedAt(now).setSubject(subject).setIssuer(issuer)
			.signWith(signatureAlgorithm, signingKey);

	// if it has been specified, let's add the expiration
	if (ttlMillis >= 0) {
		long expMillis = nowMillis + ttlMillis;
		Date exp = new Date(expMillis);
		builder.setExpiration(exp);
	}

	// Builds the JWT and serializes it to a compact, URL-safe string
	return builder.compact();
}

public static void parseJWT(String jwt) {

	// This line will throw an exception if it is not a signed JWS (as
	// expected)
	Claims claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary("Sample")).parseClaimsJws(jwt)
			.getBody();
	System.out.println("ID: " + claims.getId());
	System.out.println("Subject: " + claims.getSubject());
	System.out.println("Issuer: " + claims.getIssuer());
	System.out.println("Expiration: " + claims.getExpiration());
}*/
}
