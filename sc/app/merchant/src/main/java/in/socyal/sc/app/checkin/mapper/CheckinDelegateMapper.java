package in.socyal.sc.app.checkin.mapper;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import in.socyal.sc.api.checkin.business.response.BusinessCheckin;
import in.socyal.sc.api.checkin.business.response.BusinessCheckinDetailsResponse;
import in.socyal.sc.api.checkin.business.response.GetBusinessCheckinHistoryResponse;
import in.socyal.sc.api.checkin.business.response.GetBusinessCheckinsResponse;
import in.socyal.sc.api.checkin.dto.CheckinDto;
import in.socyal.sc.api.checkin.dto.CheckinTaggedUserDto;
import in.socyal.sc.api.checkin.response.Checkin;
import in.socyal.sc.api.checkin.response.FeedsResponse;
import in.socyal.sc.api.checkin.response.TaggedUserResponse;
import in.socyal.sc.api.checkin.response.UserDetailsResponse;
import in.socyal.sc.api.feedback.dto.FeedbackDto;
import in.socyal.sc.api.feedback.response.FeedbackDetailsResponse;
import in.socyal.sc.api.type.CheckinStatusType;
import in.socyal.sc.api.type.FeedbackStatusType;
import in.socyal.sc.api.user.dto.UserDto;

@Component
public class CheckinDelegateMapper {
	private static final String CHECKIN_CANCELLED_BY_USER = "This checkin has been cancelled by the customer";
	private static final String CHECKIN_CANCELLED_BY_MERCHANT = "This checkin has been cancelled by the restaurant";

	public BusinessCheckinDetailsResponse mapBusinessCheckinDetails(CheckinDto checkin, Integer userCheckinCount) {
		BusinessCheckinDetailsResponse response = new BusinessCheckinDetailsResponse();
		UserDto user = checkin.getUser();
		UserDetailsResponse userDetails = new UserDetailsResponse();
		userDetails.setId(user.getId());
		userDetails.setImageUrl(user.getImageUrl());
		userDetails.setName(user.getName());
		userDetails.setUserCheckins(userCheckinCount);
		response.setUser(userDetails);
		response.setCardNumber(checkin.getMerchantQrMapping().getCardId());
		response.setCheckinStatus(checkin.getStatus());
		if (CheckinStatusType.USER_CANCELLED == checkin.getStatus()) {
			response.setCancelMessage(CHECKIN_CANCELLED_BY_USER);
		} else if (CheckinStatusType.MERCHANT_CANCELLED == checkin.getStatus()) {
			response.setCancelMessage(CHECKIN_CANCELLED_BY_MERCHANT);
		}
		response.setRewardStatus(checkin.getRewardStatus());
		response.setRewardMessage(checkin.getRewardMessage());
		FeedbackDto feedback = checkin.getFeedback();
		response.setFeedbackStatus(feedback.getStatus());
		response.setFeedbackDetails(mapFeedbackResponse(feedback));
		return response;
	}

	public void map(List<CheckinDto> from, FeedsResponse to, Map<Integer, Integer> userCheckinMap) {
		List<Checkin> checkins = new ArrayList<>();
		for (CheckinDto dto : from) {
			Checkin checkinResponse = new Checkin();
			checkinResponse.setId(dto.getId());
			checkinResponse.setLikeCount(dto.getLikeCount());
			checkinResponse.setMerchantId(dto.getMerchantId());
			checkinResponse.setMerchantName(dto.getMerchantQrMapping().getMerchant().getName());
			// Set Rating given by customer
			checkinResponse.setRating(calculateRatingFromFeedback(dto.getFeedback()));
			checkinResponse.setRewardMessage(dto.getRewardMessage());
			checkinResponse.setTaggedUsers(getTaggedUserResponse(dto.getTaggedUsers()));
			checkinResponse.setTimestamp(dto.getCheckinDateTime().getTimeInMillis());
			checkinResponse.setUser(getUserDetailsResponse(dto.getUser(), userCheckinMap));
			checkinResponse.setHasLiked(dto.isLiked());
			checkins.add(checkinResponse);
		}
		to.setCheckins(checkins);
	}

	private List<TaggedUserResponse> getTaggedUserResponse(List<CheckinTaggedUserDto> list) {
		List<TaggedUserResponse> taggedUserResponse = new ArrayList<>();
		for (CheckinTaggedUserDto taggedUserDto : list) {
			TaggedUserResponse taggedUser = new TaggedUserResponse();
			taggedUser.setId(taggedUserDto.getId());
			taggedUser.setName(taggedUserDto.getUser().getName());
			taggedUserResponse.add(taggedUser);
		}
		return taggedUserResponse;
	}

	private UserDetailsResponse getUserDetailsResponse(UserDto userDto, Map<Integer, Integer> userCheckinMap) {
		UserDetailsResponse userDetailsResponse = new UserDetailsResponse();
		Integer userId = userDto.getId();
		userDetailsResponse.setId(userId);
		userDetailsResponse.setImageUrl(userDto.getImageUrl());
		userDetailsResponse.setName(userDto.getName());
		if (userCheckinMap != null) {
			userDetailsResponse.setUserCheckins(userCheckinMap.get(userId));
		}

		return userDetailsResponse;
	}

	public void map(List<CheckinDto> from, GetBusinessCheckinsResponse response, Map<Integer, Integer> userCheckinMap) {
		BusinessCheckin to = null;
		for (CheckinDto dto : from) {
			to = new BusinessCheckin();
			to.setId(dto.getId());
			to.setUser(getUserDetailsResponse(dto.getUser(), userCheckinMap));
			to.setTaggedUsers(getTaggedUserResponse(dto.getTaggedUsers()));
			if (StringUtils.isNotEmpty(dto.getRewardMessage())) {
				to.setRewardMessage(dto.getRewardMessage());
			}
			to.setTimestamp(dto.getCheckinDateTime().getTimeInMillis());
			to.setCard(dto.getMerchantQrMapping().getCardId());
			to.setCheckinStatus(dto.getStatus());
			response.getCheckins().add(to);
		}
	}

	public void map(List<CheckinDto> from, GetBusinessCheckinHistoryResponse response) {
		List<BusinessCheckin> checkins = new ArrayList<>();
		BusinessCheckin bCheckin = null;
		for (CheckinDto dto : from) {
			bCheckin = new BusinessCheckin();
			bCheckin.setId(dto.getId());
			bCheckin.setCheckinStatus(dto.getStatus());
			bCheckin.setCard(dto.getMerchantQrMapping().getCardId());
			bCheckin.setFeedbackDetails(mapFeedbackResponse(dto.getFeedback()));
			bCheckin.setRewardMessage(dto.getRewardMessage());
			bCheckin.setTaggedUsers(getTaggedUserResponse(dto.getTaggedUsers()));
			bCheckin.setTimestamp(dto.getCheckinDateTime().getTimeInMillis());
			bCheckin.setUser(getUserDetailsResponse(dto.getUser(), null));
			checkins.add(bCheckin);
		}
		response.setCheckins(checkins);

	}

	private Double calculateRatingFromFeedback(FeedbackDto feedback) {
		Double rating = null;
		if (feedback.getAmbienceRating() != null && feedback.getServiceRating() != null
				&& feedback.getFoodRating() != null) {
			rating = ((feedback.getFoodRating() + feedback.getAmbienceRating() + feedback.getServiceRating()) / 3);
			DecimalFormat format = new DecimalFormat("0.0");
			return Double.valueOf(format.format(rating));
		}

		return rating;
	}

	public FeedbackDetailsResponse mapFeedbackResponse(FeedbackDto feedback) {
		FeedbackDetailsResponse feedbackDetails = null;
		if (FeedbackStatusType.RECEIVED == feedback.getStatus()) {
			feedbackDetails = new FeedbackDetailsResponse();
			feedbackDetails.setFoodRating(feedback.getFoodRating() != null ? feedback.getFoodRating().toString() : "NA");
			feedbackDetails.setAmbienceRating(
					feedback.getAmbienceRating() != null ? feedback.getAmbienceRating().toString() : "NA");
			feedbackDetails
					.setServiceRating(feedback.getServiceRating() != null ? feedback.getServiceRating().toString() : "NA");
		}
		
		return feedbackDetails;
	}
}
