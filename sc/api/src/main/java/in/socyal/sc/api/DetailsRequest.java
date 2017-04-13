package in.socyal.sc.api;

import java.io.Serializable;

public class DetailsRequest implements Serializable {
	private static final long serialVersionUID = 1L;
	private String nameId;

	public String getNameId() {
		return nameId;
	}

	public void setNameId(String nameId) {
		this.nameId = nameId;
	}
}
