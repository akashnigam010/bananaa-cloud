package in.socyal.sc.api.suggestion.response;

import java.io.Serializable;

public class GetSuggestionsResponse implements Serializable {
	private static final long serialVersionUID = 1L;
	private String match;

	public String getMatch() {
		return match;
	}

	public void setMatch(String match) {
		this.match = match;
	}
}
