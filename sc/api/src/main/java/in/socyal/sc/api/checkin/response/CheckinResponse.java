package in.socyal.sc.api.checkin.response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import in.socyal.sc.api.response.GenericResponse;

public class CheckinResponse extends GenericResponse implements Serializable {
	private static final long serialVersionUID = 1L;
	private List<Checkin> checkins;

	public List<Checkin> getCheckins() {
		if (checkins == null) {
			return new ArrayList<>();
		}

		return checkins;
	}

	public void setCheckins(List<Checkin> checkins) {
		this.checkins = checkins;
	}
}
