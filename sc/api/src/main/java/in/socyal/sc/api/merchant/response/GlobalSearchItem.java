package in.socyal.sc.api.merchant.response;

import in.socyal.sc.api.type.SearchType;

public class GlobalSearchItem {
	private String nameId;
	private String name;
	private String shortAddress;
	private String merchantUrl;
	private SearchType type;
	
	public GlobalSearchItem(SearchType type) {
		this.type = type;
	}

	public String getNameId() {
		return nameId;
	}

	public void setNameId(String nameId) {
		this.nameId = nameId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getShortAddress() {
		return shortAddress;
	}

	public void setShortAddress(String shortAddress) {
		this.shortAddress = shortAddress;
	}

	public String getMerchantUrl() {
		return merchantUrl;
	}

	public void setMerchantUrl(String merchantUrl) {
		this.merchantUrl = merchantUrl;
	}

	public SearchType getType() {
		return type;
	}

	public void setType(SearchType type) {
		this.type = type;
	}
}
