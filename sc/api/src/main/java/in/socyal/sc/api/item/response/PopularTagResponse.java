package in.socyal.sc.api.item.response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import in.socyal.sc.api.response.GenericResponse;

public class PopularTagResponse extends GenericResponse implements Serializable {
	private static final long serialVersionUID = 1L;
	private List<PopularTag> tags;

	public List<PopularTag> getTags() {
		if (this.tags == null) {
			this.tags = new ArrayList<>();
		}
		return tags;
	}

	public void setTags(List<PopularTag> tags) {
		this.tags = tags;
	}
}
