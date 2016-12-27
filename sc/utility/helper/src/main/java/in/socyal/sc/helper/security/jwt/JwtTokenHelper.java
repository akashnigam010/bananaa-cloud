package in.socyal.sc.helper.security.jwt;

import java.util.List;

public interface JwtTokenHelper {
	public String getUserName();
	public List<String> getRoles();
}
