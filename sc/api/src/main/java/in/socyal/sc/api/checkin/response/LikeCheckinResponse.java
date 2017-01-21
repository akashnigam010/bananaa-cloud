package in.socyal.sc.api.checkin.response;

import java.io.Serializable;

import in.socyal.sc.api.response.GenericResponse;

public class LikeCheckinResponse extends GenericResponse implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer likeCount;

	public Integer getLikeCount() {
		return likeCount;
	}

	public void setLikeCount(Integer likeCount) {
		this.likeCount = likeCount;
	}
}
