package in.socyal.sc.api.location.response;

import java.util.List;

import in.socyal.sc.api.merchant.dto.LocalityDto;
import in.socyal.sc.api.response.GenericResponse;

public class GetLocalitiesResponse extends GenericResponse {
	private static final long serialVersionUID = 1L;
	List<LocalityDto> localities;

	public List<LocalityDto> getLocalities() {
		return localities;
	}

	public void setLocalities(List<LocalityDto> localities) {
		this.localities = localities;
	}
}
