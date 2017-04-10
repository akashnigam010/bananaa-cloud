package in.socyal.sc.api.manage.response;

import java.util.ArrayList;
import java.util.List;

import in.socyal.sc.api.response.GenericResponse;
import in.socyal.sc.api.suggestion.dto.SuggestionDto;

public class GetSuggestionsResponse extends GenericResponse {
	private static final long serialVersionUID = 1L;
	private List<SuggestionDto> suggestions;

	public List<SuggestionDto> getSuggestions() {
		if (this.suggestions == null) {
			this.suggestions = new ArrayList<>();
		}
		return suggestions;
	}

	public void setSuggestions(List<SuggestionDto> suggestions) {
		this.suggestions = suggestions;
	}
}
