package in.socyal.sc.api.merchant.response;

public class User {
	private Integer id;
	private String name;
	private Integer recommendations;
	private String imageUrl;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getRecommendations() {
		return recommendations;
	}

	public void setRecommendations(Integer recommendations) {
		this.recommendations = recommendations;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
}
