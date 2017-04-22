package in.socyal.sc.api.manage.response;

import java.util.ArrayList;
import java.util.List;

import in.socyal.sc.api.cuisine.dto.CuisineDto;
import in.socyal.sc.api.response.GenericResponse;

public class GetCuisinesResponse extends GenericResponse {
	private static final long serialVersionUID = 1L;
	private List<CuisineDto> cuisines;

	public List<CuisineDto> getCuisines() {
		if (this.cuisines == null) {
			this.cuisines = new ArrayList<>();
		}
		return cuisines;
	}

	public void setCuisines(List<CuisineDto> cuisines) {
		this.cuisines = cuisines;
	}

}
