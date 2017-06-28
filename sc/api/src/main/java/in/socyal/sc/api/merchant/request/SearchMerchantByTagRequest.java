package in.socyal.sc.api.merchant.request;

import in.socyal.sc.api.type.TagType;

public class SearchMerchantByTagRequest {
	private String nameId;
	private TagType type;
	private Integer page;
	private String localityNameId;

	public String getNameId() {
		return nameId;
	}

	public void setNameId(String nameId) {
		this.nameId = nameId;
	}

	public TagType getType() {
		return type;
	}

	public void setType(TagType type) {
		this.type = type;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public String getLocalityNameId() {
		return localityNameId;
	}

	public void setLocalityNameId(String localityNameId) {
		this.localityNameId = localityNameId;
	}
}
