package in.socyal.sc.api.item.response;

public class PopularTag {
	private Long merchants;
	private String name;
	private String nameId;
	private String thumbnail;
	private String url;

	public Long getMerchants() {
		return merchants;
	}

	public void setMerchants(Long merchants) {
		this.merchants = merchants;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNameId() {
		return nameId;
	}

	public void setNameId(String nameId) {
		this.nameId = nameId;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}