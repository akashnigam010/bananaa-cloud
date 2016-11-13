package in.socyal.sc.helper.security.jwt;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class JwtTokenHelperImpl implements JwtTokenHelper {
	@Override
	public String getUserName() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		AuthenticatedUser userDetails = (AuthenticatedUser) authentication.getPrincipal();
		return userDetails.getUsername();
	}
}
