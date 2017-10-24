package in.socyal.sc.api.manage.response;

import in.socyal.sc.api.response.GenericResponse;

public class MerchantFlagsResponse extends GenericResponse {
	private static final long serialVersionUID = 1L;
	private Boolean isActive;
	private Boolean canEdit;

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Boolean getCanEdit() {
		return canEdit;
	}

	public void setCanEdit(Boolean canEdit) {
		this.canEdit = canEdit;
	}
}
