package in.socyal.sc.api.checkin.business.response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import in.socyal.sc.api.checkin.response.TaggedUserResponse;
import in.socyal.sc.api.checkin.response.UserDetailsResponse;
import in.socyal.sc.api.feedback.response.FeedbackDetailsResponse;
import in.socyal.sc.api.type.CheckinStatusType;

public class BusinessCheckin implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private UserDetailsResponse user;
	private List<TaggedUserResponse> taggedUsers;
	private String rewardMessage;
	private Double rating;
	private Long timestamp;
	private String card;
	private CheckinStatusType checkinStatus;
	private FeedbackDetailsResponse feedbackDetails;

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
		if (this.taggedUsers == null) {
			this.taggedUsers = new ArrayList<>();
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

	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	public String getCard() {
		return card;
	}

	public void setCard(String card) {
		this.card = card;
	}

	public CheckinStatusType getCheckinStatus() {
		return checkinStatus;
	}

	public void setCheckinStatus(CheckinStatusType checkinStatus) {
		this.checkinStatus = checkinStatus;
	}

	public FeedbackDetailsResponse getFeedbackDetails() {
		return feedbackDetails;
	}

	public void setFeedbackDetails(FeedbackDetailsResponse feedbackDetails) {
		this.feedbackDetails = feedbackDetails;
	}
}
