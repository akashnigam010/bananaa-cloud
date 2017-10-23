package in.socyal.sc.api.location.dto;

import java.io.Serializable;
import java.util.List;

public class CityDto implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String name;
	private String nameId;
	private List<LocalityDto> localities;

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

	public String getNameId() {
		return nameId;
	}

	public void setNameId(String nameId) {
		this.nameId = nameId;
	}

	public List<LocalityDto> getLocalities() {
		return localities;
	}

	public void setLocalities(List<LocalityDto> localities) {
		this.localities = localities;
	}
}
