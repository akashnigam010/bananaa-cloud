package in.socyal.sc.api.checkin.dto;

public class CheckinFilterCriteria {
	private Boolean mapQrDetails;
	private Boolean mapUser;
	private Boolean mapTaggedUsers;

	public CheckinFilterCriteria() {
		this.mapQrDetails = Boolean.FALSE;
		this.mapUser = Boolean.FALSE;
		this.mapTaggedUsers = Boolean.FALSE;
	}

	public CheckinFilterCriteria(Boolean mapQrDetails, Boolean mapUser, Boolean mapTaggedUsers) {
		this.mapQrDetails = mapQrDetails;
		this.mapUser = mapUser;
		this.mapTaggedUsers = mapTaggedUsers;		
	}
	
	public Boolean getMapQrDetails() {
		return mapQrDetails;
	}

	public void setMapQrDetails(Boolean mapQrDetails) {
		this.mapQrDetails = mapQrDetails;
	}

	public Boolean getMapTaggedUsers() {
		return mapTaggedUsers;
	}

	public void setMapTaggedUsers(Boolean mapTaggedUsers) {
		this.mapTaggedUsers = mapTaggedUsers;
	}

	public Boolean getMapUser() {
		return mapUser;
	}

	public void setMapUser(Boolean mapUser) {
		this.mapUser = mapUser;
	}
}
