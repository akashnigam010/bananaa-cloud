package in.socyal.sc.api.checkin.response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Checkin implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private UserDetailsResponse user;
	private List<TaggedUserResponse> taggedUsers;
	private String rewardMessage;
	private Double rating;
	private Date timestamp;
	private Integer likeCount;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public UserDetailsResponse getUser() {
		return user;
	}

	public void setUser(UserDetailsResponse user) {
		this.user = user;
	}

	public List<TaggedUserResponse> getTaggedUsers() {
		if (taggedUsers == null) {
			return new ArrayList<>();
		}
		
		return taggedUsers;
	}

	public void setTaggedUsers(List<TaggedUserResponse> taggedUsers) {
		this.taggedUsers = taggedUsers;
	}

	public String getRewardMessage() {
		return rewardMessage;
	}

	public void setRewardMessage(String rewardMessage) {
		this.rewardMessage = rewardMessage;
	}

	public Double getRating() {
		return rating;
	}

	public void setRating(Double rating) {
		this.rating = rating;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public Integer getLikeCount() {
		return likeCount;
	}

	public void setLikeCount(Integer likeCount) {
		this.likeCount = likeCount;
	}
}
