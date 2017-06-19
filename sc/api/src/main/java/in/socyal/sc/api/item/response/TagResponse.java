package in.socyal.sc.api.item.response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import in.socyal.sc.api.response.GenericResponse;

public class TagResponse extends GenericResponse implements Serializable {
	private static final long serialVersionUID = 1L;
	private List<Tag> tags;

	public List<Tag> getTags() {
		if (this.tags == null) {
			this.tags = new ArrayList<>();
		}
		return tags;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}
}
