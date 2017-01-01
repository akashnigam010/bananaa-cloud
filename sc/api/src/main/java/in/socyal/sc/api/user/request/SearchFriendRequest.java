package in.socyal.sc.api.user.request;

import java.io.Serializable;

public class SearchFriendRequest implements Serializable {
	private static final long serialVersionUID = 1L;
	private String searchString;

	public String getSearchString() {
		return searchString;
	}

	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}
}
