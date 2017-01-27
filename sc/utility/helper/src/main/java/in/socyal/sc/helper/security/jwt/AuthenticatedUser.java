package in.socyal.sc.helper.security.jwt;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class AuthenticatedUser implements UserDetails {
	String userId;
	String userName;
	String token;
	List<GrantedAuthority> authorityList;

	private static final long serialVersionUID = 1L;

	public AuthenticatedUser(String userId, String userName, String token, List<GrantedAuthority> authorityList) {
		this.userId = userId;
		this.userName = userName;
		this.token = token;
		this.authorityList = authorityList;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorityList;
	}

	@Override
	public String getPassword() {
		return "";
	}

	@Override
	public String getUsername() {
		return userName;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public String toString() {
		return "AuthenticatedUser [userId=" + userId + ", userName=" + userName + ", authorityList=" + authorityList
				+ "]";
	}
}
