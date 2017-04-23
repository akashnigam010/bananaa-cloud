package in.socyal.sc.api.merchant.response;

public class Recommendation  {
	private Integer id;
	private Integer itemId;
	private String name;
	private String description;
	private Integer totalRcmdns;
	private String thumbnail;
	private String timeDiff;

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String review) {
		this.description = review;
	}

	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer dishId) {
		this.itemId = dishId;
	}

	public Integer getTotalRcmdns() {
		return totalRcmdns;
	}

	public void setTotalRcmdns(Integer totalRcmdns) {
		this.totalRcmdns = totalRcmdns;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String imageUrl) {
		this.thumbnail = imageUrl;
	}

	public String getTimeDiff() {
		return timeDiff;
	}

	public void setTimeDiff(String timeDiff) {
		this.timeDiff = timeDiff;
	}
}
