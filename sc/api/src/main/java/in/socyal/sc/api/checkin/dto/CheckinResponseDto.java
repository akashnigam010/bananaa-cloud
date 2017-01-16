package in.socyal.sc.api.checkin.dto;

import java.util.Date;
import java.util.List;

public class CheckinResponseDto {
	private Integer id;
	private UserDetailsDto user;
	private List<TaggedUserDto> taggedUsers;
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

	public UserDetailsDto getUser() {
		return user;
	}

	public void setUser(UserDetailsDto user) {
		this.user = user;
	}

	public List<TaggedUserDto> getTaggedUsers() {
		return taggedUsers;
	}

	public void setTaggedUsers(List<TaggedUserDto> taggedUsers) {
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
