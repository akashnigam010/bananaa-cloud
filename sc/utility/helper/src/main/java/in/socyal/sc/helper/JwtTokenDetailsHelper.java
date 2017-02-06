package in.socyal.sc.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import in.socyal.sc.api.type.RoleType;
import in.socyal.sc.helper.security.jwt.JwtTokenHelper;

@Component
public class JwtTokenDetailsHelper {
	@Autowired JwtTokenHelper jwtHelper;
	
	public RoleType getRole() {
		RoleType jwtRole = null;
		for (String role : jwtHelper.getRoles()) {
			jwtRole = RoleType.getRole(role);
		}
		return jwtRole;
	}
	
	public boolean isUserLoggedIn() {
		for (String role : jwtHelper.getRoles()) {
			RoleType roleType = RoleType.getRole(role);
			if (RoleType.USER == roleType) {
				return Boolean.TRUE;
			} 
		}
		
		return Boolean.FALSE;
	}
	
	public boolean isGuestLoggedIn() {
		for (String role : jwtHelper.getRoles()) {
			RoleType roleType = RoleType.getRole(role);
			if (RoleType.GUEST == roleType) {
				return Boolean.TRUE;
			} 
		}
		
		return Boolean.FALSE;
	}
	
	public boolean isMerchantLoggedIn() {
		for (String role : jwtHelper.getRoles()) {
			RoleType roleType = RoleType.getRole(role);
			if (RoleType.MERCHANT == roleType) {
				return Boolean.TRUE;
			} 
		}
		
		return Boolean.FALSE;
	}
	
	public Integer getCurrentUserId() {
		return Integer.valueOf(jwtHelper.getUserName());
	}
	
	public Integer getCurrentMerchantId() {
		return Integer.valueOf(jwtHelper.getMerchantId());
	}
}
