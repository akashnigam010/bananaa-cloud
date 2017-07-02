package in.socyal.sc.api.merchant.response;

import java.util.List;

import in.socyal.sc.api.item.response.TagShortDetails;
import in.socyal.sc.api.response.GenericResponse;

public class GlobalSearchResponse extends GenericResponse {
	private static final long serialVersionUID = 1L;
	private List<MerchantShortDetails> merchants;
	private List<TagShortDetails> cuisines;
	private List<TagShortDetails> suggestions;

	public List<MerchantShortDetails> getMerchants() {
		return merchants;
	}

	public void setMerchants(List<MerchantShortDetails> merchants) {
		this.merchants = merchants;
	}

	public List<TagShortDetails> getCuisines() {
		return cuisines;
	}

	public void setCuisines(List<TagShortDetails> cuisines) {
		this.cuisines = cuisines;
	}

	public List<TagShortDetails> getSuggestions() {
		return suggestions;
	}

	public void setSuggestions(List<TagShortDetails> suggestions) {
		this.suggestions = suggestions;
	}
}
