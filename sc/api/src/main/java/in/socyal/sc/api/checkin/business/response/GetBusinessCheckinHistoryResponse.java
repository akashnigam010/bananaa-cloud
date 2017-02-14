package in.socyal.sc.api.checkin.business.response;

import java.io.Serializable;
import java.util.List;

import in.socyal.sc.api.response.GenericResponse;

public class GetBusinessCheckinHistoryResponse extends GenericResponse implements Serializable {
	private static final long serialVersionUID = 1L;
	private List<BusinessCheckin> checkins;

	public List<BusinessCheckin> getCheckins() {
		return checkins;
	}

	public void setCheckins(List<BusinessCheckin> checkins) {
		this.checkins = checkins;
	}
}
