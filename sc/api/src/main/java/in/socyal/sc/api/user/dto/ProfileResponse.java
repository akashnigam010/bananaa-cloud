package in.socyal.sc.api.user.dto;

import in.socyal.sc.api.response.GenericResponse;

public class ProfileResponse extends GenericResponse {
	private static final long serialVersionUID = 1L;
	private Profile profile;

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}
}
