package in.socyal.sc.api.merchant.response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import in.socyal.sc.api.response.GenericResponse;

public class MerchantListForTagResponse extends GenericResponse implements Serializable {
	private static final long serialVersionUID = 1L;
	private List<MerchantDetails> merchants;
	private String tagName;
	private String location;
	private Integer page;

	public List<MerchantDetails> getMerchants() {
		if (merchants == null) {
			merchants = new ArrayList<>();
		}
		return merchants;
	}

	public void setMerchants(List<MerchantDetails> merchants) {
		this.merchants = merchants;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}
}
