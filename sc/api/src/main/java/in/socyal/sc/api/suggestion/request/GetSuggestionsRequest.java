package in.socyal.sc.api.suggestion.request;

import java.io.Serializable;

public class GetSuggestionsRequest implements Serializable {
	private static final long serialVersionUID = 1L;
	private String match;

	public String getMatch() {
		return match;
	}

	public void setMatch(String match) {
		this.match = match;
	}
}
