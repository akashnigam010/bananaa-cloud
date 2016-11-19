package in.socyal.sc.api.personnel.response;

import in.socyal.sc.api.response.GenericResponse;

@SuppressWarnings("rawtypes")
public class BarAvailabilityResponse extends GenericResponse {
	private static final long serialVersionUID = 1L;

	private Boolean isBarAvailable;

	public Boolean isBarAvailable() {
		return isBarAvailable;
	}

	public void setIsBarAvailable(Boolean isBarAvailable) {
		this.isBarAvailable = isBarAvailable;
	}
}
