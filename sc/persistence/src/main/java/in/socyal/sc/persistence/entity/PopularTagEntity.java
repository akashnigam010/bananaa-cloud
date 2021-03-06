package in.socyal.sc.persistence.entity;

import in.socyal.sc.persistence.type.BucketUrlType;

public class PopularTagEntity {
	private Long merchants;
	private String name;
	private String nameId;
	private String thumbnail;

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
		return BucketUrlType.URL_PREFIX.getUrlPrefix() + thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}
}