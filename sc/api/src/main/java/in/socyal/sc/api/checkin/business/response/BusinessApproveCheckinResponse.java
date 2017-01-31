package in.socyal.sc.api.checkin.business.response;

import java.io.Serializable;

import in.socyal.sc.api.checkin.response.UserDetailsResponse;
import in.socyal.sc.api.feedback.response.FeedbackDetailsResponse;
import in.socyal.sc.api.response.GenericResponse;
import in.socyal.sc.api.type.CheckinStatusType;
import in.socyal.sc.api.type.FeedbackStatusType;
import in.socyal.sc.api.type.RewardStatusType;

public class BusinessApproveCheckinResponse extends GenericResponse implements Serializable {
	private static final long serialVersionUID = 1L;
	private UserDetailsResponse user;
	private Integer cardNumber;
	private CheckinStatusType checkinStatus;
	private String cancelMessage;
	private RewardStatusType rewardStatus;
	private String rewardMessage;
	private FeedbackStatusType feedbackStatus;
	private FeedbackDetailsResponse feedbackDetails;

	public UserDetailsResponse getUser() {
		return user;
	}

	public void setUser(UserDetailsResponse user) {
		this.user = user;
	}

	public Integer getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(Integer cardNumber) {
		this.cardNumber = cardNumber;
	}

	public CheckinStatusType getCheckinStatus() {
		return checkinStatus;
	}

	public void setCheckinStatus(CheckinStatusType checkinStatus) {
		this.checkinStatus = checkinStatus;
	}

	public String getCancelMessage() {
		return cancelMessage;
	}

	public void setCancelMessage(String cancelMessage) {
		this.cancelMessage = cancelMessage;
	}

	public RewardStatusType getRewardStatus() {
		return rewardStatus;
	}

	public void setRewardStatus(RewardStatusType rewardStatus) {
		this.rewardStatus = rewardStatus;
	}

	public String getRewardMessage() {
		return rewardMessage;
	}

	public void setRewardMessage(String rewardMessage) {
		this.rewardMessage = rewardMessage;
	}

	public FeedbackStatusType getFeedbackStatus() {
		return feedbackStatus;
	}

	public void setFeedbackStatus(FeedbackStatusType feedbackStatus) {
		this.feedbackStatus = feedbackStatus;
	}

	public FeedbackDetailsResponse getFeedbackDetails() {
		return feedbackDetails;
	}

	public void setFeedbackDetails(FeedbackDetailsResponse feedbackDetails) {
		this.feedbackDetails = feedbackDetails;
	}
}
