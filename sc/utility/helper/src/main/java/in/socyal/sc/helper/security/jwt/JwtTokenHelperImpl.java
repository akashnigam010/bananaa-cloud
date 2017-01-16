package in.socyal.sc.helper.security.jwt;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
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
	
	@SuppressWarnings("unchecked")
	@Override
	public List<String> getRoles() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		AuthenticatedUser userDetails = (AuthenticatedUser) authentication.getPrincipal();
		List<GrantedAuthority> authorities = (List<GrantedAuthority>) userDetails.getAuthorities();
		if (authorities == null || authorities.isEmpty()) {
			return Collections.EMPTY_LIST;
		}
		List<String> roles = new ArrayList<>();
		for (GrantedAuthority authority : authorities) {
			roles.add(authority.getAuthority());
		}
		return roles;
	}
}
