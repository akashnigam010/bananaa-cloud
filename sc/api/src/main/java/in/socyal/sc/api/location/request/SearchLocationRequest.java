package in.socyal.sc.api.location.request;

import java.io.Serializable;

public class SearchLocationRequest implements Serializable {
	private static final long serialVersionUID = 1L;
	private String searchString;

	public String getSearchString() {
		return searchString;
	}

	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}
}
