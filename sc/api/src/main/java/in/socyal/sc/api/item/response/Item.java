package in.socyal.sc.api.item.response;

public class Item {
	private Integer id;
	private String nameId;
	private String name;
	private String thumbnail;
	private Integer recommendations;
	private String itemUrl;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String imageUrl) {
		this.thumbnail = imageUrl;
	}

	public Integer getRecommendations() {
		return recommendations;
	}

	public void setRecommendations(Integer recommendations) {
		this.recommendations = recommendations;
	}

	public String getItemUrl() {
		return itemUrl;
	}

	public void setItemUrl(String itemUrl) {
		this.itemUrl = itemUrl;
	}
}
