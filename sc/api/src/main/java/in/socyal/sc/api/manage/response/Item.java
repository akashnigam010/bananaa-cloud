package in.socyal.sc.api.manage.response;

import java.util.List;

import in.socyal.sc.api.manage.request.Tag;

public class Item {
	private Integer id;
	private String name;
	private Integer vegnonveg;
	private List<Tag> suggestions;
	private List<Tag> cuisines;
	private String thumbnail;
	private String image;

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

	public List<Tag> getSuggestions() {
		return suggestions;
	}

	public void setSuggestions(List<Tag> suggestions) {
		this.suggestions = suggestions;
	}

	public List<Tag> getCuisines() {
		return cuisines;
	}

	public void setCuisines(List<Tag> cuisines) {
		this.cuisines = cuisines;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Integer getVegnonveg() {
		return vegnonveg;
	}

	public void setVegnonveg(Integer vegnonveg) {
		this.vegnonveg = vegnonveg;
	}
}
