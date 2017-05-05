package in.socyal.sc.api.merchant.response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import in.socyal.sc.api.response.GenericResponse;

public class StoriesResponse extends GenericResponse implements Serializable {
	private static final long serialVersionUID = 1L;
	private List<Story> stories;

	public List<Story> getStories() {
		if (this.stories == null) {
			this.stories = new ArrayList<>();
		}
		return stories;
	}

	public void setStories(List<Story> stories) {
		this.stories = stories;
	}
}
