package in.socyal.sc.api.bo.personnel.response;

import java.util.List;

import in.socyal.sc.api.bo.personnel.dto.PersonnelDto;
import in.socyal.sc.api.bo.response.GenericResponse;

public class PersonnelResponse extends GenericResponse {
	private static final long serialVersionUID = 1L;
	private List<PersonnelDto> personnels;

	public List<PersonnelDto> getPersonnels() {
		return personnels;
	}

	public void setPersonnels(List<PersonnelDto> personnels) {
		this.personnels = personnels;
	}
}
