package in.socyal.sc.api.user.response;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import in.socyal.sc.api.response.GenericResponse;

public class SearchUserResponse extends GenericResponse implements Serializable {
	private static final long serialVersionUID = 1L;
	private List<String> userNames;

	public List<String> getUserNames() {
		if (userNames == null) {
			return Collections.emptyList();
		}
		return userNames;
	}

	public void setUserNames(List<String> userNames) {
		this.userNames = userNames;
	}
}
