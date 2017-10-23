package in.socyal.sc.api.location.response;

import java.io.Serializable;
import java.util.List;

import in.socyal.sc.api.location.dto.CityDto;
import in.socyal.sc.api.response.GenericResponse;

public class LocalitiesResponse extends GenericResponse implements Serializable {
	private static final long serialVersionUID = 1L;
	List<CityDto> cities;

	public List<CityDto> getCities() {
		return cities;
	}

	public void setCities(List<CityDto> cities) {
		this.cities = cities;
	}
}
