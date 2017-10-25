package in.socyal.sc.api.item.response;

import java.util.List;

import in.socyal.sc.api.response.GenericResponse;
import in.socyal.sc.api.user.dto.UserTagPreference;

public class SearchTagResponse extends GenericResponse {
	private static final long serialVersionUID = 1L;
	private List<UserTagPreference> searchItems;

	public List<UserTagPreference> getSearchItems() {
		return searchItems;
	}

	public void setSearchItems(List<UserTagPreference> searchItems) {
		this.searchItems = searchItems;
	}
}
