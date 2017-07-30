package in.socyal.sc.api.manage.request;

import java.util.ArrayList;
import java.util.List;

public class UpdateItemRequest {
	private Integer id;
	private String name;
	private List<Tag> cuisines;
	private List<Tag> suggestions;
	private String imageUrl;
	private String thumbnail;
	private Boolean isActive;

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

	public List<Tag> getCuisines() {
		return cuisines;
	}

	public void setCuisines(List<Tag> cuisines) {
		this.cuisines = cuisines;
	}

	public List<Tag> getSuggestions() {
		return suggestions;
	}

	public void setSuggestions(List<Tag> suggestions) {
		this.suggestions = suggestions;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public List<Integer> getSuggestionIds() {
		List<Integer> list = new ArrayList<>();
		for (Tag tag : this.getSuggestions()) {
			list.add(tag.getId());
		}
		return list;
	}

	public List<Integer> getCuisineIds() {
		List<Integer> list = new ArrayList<>();
		for (Tag tag : this.getCuisines()) {
			list.add(tag.getId());
		}
		return list;
	}
}
