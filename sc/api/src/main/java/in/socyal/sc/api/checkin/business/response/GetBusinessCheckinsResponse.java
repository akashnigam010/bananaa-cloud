package in.socyal.sc.api.checkin.business.response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import in.socyal.sc.api.response.GenericResponse;

public class GetBusinessCheckinsResponse extends GenericResponse implements Serializable {
	private static final long serialVersionUID = 1L;
	private String date;
	private Integer checkinCount;
	private List<BusinessCheckin> checkins;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Integer getCheckinCount() {
		return checkinCount;
	}

	public void setCheckinCount(Integer checkinCount) {
		this.checkinCount = checkinCount;
	}

	public List<BusinessCheckin> getCheckins() {
		if (this.checkins == null) {
			this.checkins = new ArrayList<>();
		}
		return checkins;
	}

	public void setCheckins(List<BusinessCheckin> checkins) {
		this.checkins = checkins;
	}
}
