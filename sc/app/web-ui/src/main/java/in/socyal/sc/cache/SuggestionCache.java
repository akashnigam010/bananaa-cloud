package in.socyal.sc.cache;

import org.springframework.stereotype.Component;

import in.socyal.sc.api.suggestion.dto.SuggestionDto;

@Component
public class SuggestionCache {
	// TODO: add caching logic
	public SuggestionDto getSuggestion(String nameId) {
		if (nameId.equalsIgnoreCase("potato")) {
			SuggestionDto suggestion = new SuggestionDto();
			suggestion.setId(1);
			suggestion.setName("Potato");
			suggestion.setNameId("potato");
			return suggestion;
		} else if (nameId.equalsIgnoreCase("potato")) {
			SuggestionDto suggestion = new SuggestionDto();
			suggestion.setId(2);
			suggestion.setName("Chicken");
			suggestion.setNameId("chicken");
			return suggestion;
		} else if (nameId.equalsIgnoreCase("mutton")) {
			SuggestionDto suggestion = new SuggestionDto();
			suggestion.setId(2);
			suggestion.setName("Mutton");
			suggestion.setNameId("mutton");
			return suggestion;
		}
		return null;
	}
}
