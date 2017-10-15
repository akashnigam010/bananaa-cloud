package in.socyal.sc.api.user.request;

import java.util.List;

public class SavePreferencesRequest {
	private Integer vegnonvegId;
	private List<Integer> cuisinePreferences;
	private List<Integer> suggestionPreferences;

	public Integer getVegnonvegId() {
		return vegnonvegId;
	}

	public void setVegnonvegId(Integer vegnonvegId) {
		this.vegnonvegId = vegnonvegId;
	}

	public List<Integer> getCuisinePreferences() {
		return cuisinePreferences;
	}

	public void setCuisinePreferences(List<Integer> cuisinePreferences) {
		this.cuisinePreferences = cuisinePreferences;
	}

	public List<Integer> getSuggestionPreferences() {
		return suggestionPreferences;
	}

	public void setSuggestionPreferences(List<Integer> suggestionPreferences) {
		this.suggestionPreferences = suggestionPreferences;
	}
}
