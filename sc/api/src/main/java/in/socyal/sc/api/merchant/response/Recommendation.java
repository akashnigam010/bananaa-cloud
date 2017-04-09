package in.socyal.sc.api.merchant.response;

import java.io.Serializable;

import in.socyal.sc.api.response.GenericResponse;

public class Recommendation  {
	private Integer id;
	private Integer itemId;
	private String name;
	private String description;

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
}
