package in.socyal.sc.api.merchant.request;

import in.socyal.sc.api.type.TagType;

public class SearchMerchantByTagRequest {
	private Integer id;
	private TagType type;
	private Integer page;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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
}
