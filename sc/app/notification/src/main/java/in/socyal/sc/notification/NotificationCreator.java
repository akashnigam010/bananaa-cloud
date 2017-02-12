package in.socyal.sc.notification;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import in.socyal.sc.api.checkin.dto.CheckinDto;
import in.socyal.sc.api.login.request.Data;
import in.socyal.sc.api.login.request.NotificationRequest;
import in.socyal.sc.api.user.dto.UserDto;
import in.socyal.sc.persistence.CheckinDao;
import in.socyal.sc.persistence.MerchantLoginDao;
import in.socyal.sc.persistence.UserDao;

@Component
public class NotificationCreator {
	private static final String ID = "id";
	private static final String REWARD_MESSAGE = "rewardMessage";
	private static final String MERCHANT_ID = "merchantId";
	private static final String MERCHANT_NAME = "merchantName";
	private static final String SHORT_ADDRESS = "shortAddress";
	private static final String REDIRECT_TO = "redirectTo";
	private static final String BUSINESS_DETAIL_SCREEN = "2";
	private static final String CUSTOMER_INTERMEDIATEL_SCREEN = "1";
	private static final String CUSTOMER_FEEDBACK_POPUP = "4";
	private static final String CUSTOMER_REWARD_POPUP = "5";

	@Autowired
	MerchantLoginDao merchantLoginDao;
	@Autowired
	UserDao userDao;
	@Autowired
	CheckinDao checkinDao;

	public NotificationRequest createCheckinNotificationToMerchant(Integer checkinId, Integer userId,
			Integer merchantId) {
		NotificationRequest notificationRequest = new NotificationRequest();
		UserDto user = userDao.fetchUser(userId);
		notificationRequest.setTitle(user.getName() + " has checked in");
		notificationRequest.setBody("Tap to see the details and confirm the checkin");
		Data checkinData = new Data(ID, checkinId.toString());
		Data redirectData = new Data(REDIRECT_TO, BUSINESS_DETAIL_SCREEN);
		notificationRequest.getData().add(checkinData);
		notificationRequest.getData().add(redirectData);
		List<String> registrationIds = merchantLoginDao.getRegistrationIdsForMerchant(merchantId);
		notificationRequest.setDeviceTokens(registrationIds);
		return notificationRequest;
	}

	public NotificationRequest createSubmitFeedbackNotificationToMerchant(CheckinDto checkin) {
		NotificationRequest notificationRequest = new NotificationRequest();
		notificationRequest.setTitle(checkin.getUser().getName() + " has provided feedback");
		notificationRequest.setBody("Tap to see the details");
		Data checkinData = new Data(ID, checkin.getId().toString());
		Data redirectData = new Data(REDIRECT_TO, BUSINESS_DETAIL_SCREEN);
		notificationRequest.getData().add(checkinData);
		notificationRequest.getData().add(redirectData);
		List<String> registrationIds = merchantLoginDao.getRegistrationIdsForMerchant(checkin.getMerchantId());
		notificationRequest.setDeviceTokens(registrationIds);
		return notificationRequest;
	}

	public NotificationRequest createApproveCheckinNotificationToCustomer(CheckinDto checkin) {
		NotificationRequest notificationRequest = new NotificationRequest();
		notificationRequest.setTitle("Yay! your checkin is confirmed");
		notificationRequest.setBody("Tap to see the details");
		Data redirectData = new Data(REDIRECT_TO, CUSTOMER_INTERMEDIATEL_SCREEN);
		notificationRequest.getData().add(redirectData);
		notificationRequest.getDeviceTokens().add(userDao.getRegistrationIdForUser(checkin.getUser().getId()));
		return notificationRequest;
	}

	public NotificationRequest createCancelCheckinNotificationToCustomer(CheckinDto checkin) {
		NotificationRequest notificationRequest = new NotificationRequest();
		notificationRequest.setTitle("Uh oh! your checkin was cancelled");
		notificationRequest.setBody("Tap to see the details");
		Data redirectData = new Data(REDIRECT_TO, CUSTOMER_INTERMEDIATEL_SCREEN);
		notificationRequest.getData().add(redirectData);
		notificationRequest.getDeviceTokens().add(userDao.getRegistrationIdForUser(checkin.getUser().getId()));
		return notificationRequest;
	}

	public NotificationRequest createSubmitRewardNotificationToCustomer(CheckinDto checkin) {
		NotificationRequest notificationRequest = new NotificationRequest();
		notificationRequest.setTitle("Hi there! you won a surprise gift");
		notificationRequest.setBody("Tap to know more");
		Data redirectData = new Data(REDIRECT_TO, CUSTOMER_REWARD_POPUP);
		Data checkinData = new Data(ID, checkin.getId().toString());
		Data rewardMessageData = new Data(REWARD_MESSAGE, checkin.getRewardMessage());
		notificationRequest.getData().add(redirectData);
		notificationRequest.getData().add(checkinData);
		notificationRequest.getData().add(rewardMessageData);
		notificationRequest.getDeviceTokens().add(userDao.getRegistrationIdForUser(checkin.getUser().getId()));
		return notificationRequest;
	}

	public NotificationRequest createAskFeedbackNotificationToCustomer(CheckinDto checkin) {
		NotificationRequest notificationRequest = new NotificationRequest();
		notificationRequest.setTitle("Liked or not ?");
		notificationRequest.setBody("Please share your experience");
		Data redirectData = new Data(REDIRECT_TO, CUSTOMER_FEEDBACK_POPUP);
		Data checkinData = new Data(ID, checkin.getId().toString());
		Data merchantIdData = new Data(MERCHANT_ID, checkin.getMerchantId().toString());
		Data merchantNameData = new Data(MERCHANT_NAME, checkin.getMerchantQrMapping().getMerchant().getName());
		Data shortAddressData = new Data(SHORT_ADDRESS,
				checkin.getMerchantQrMapping().getMerchant().getAddress().getLocality().getShortAddress());
		notificationRequest.getData().add(redirectData);
		notificationRequest.getData().add(checkinData);
		notificationRequest.getData().add(merchantIdData);
		notificationRequest.getData().add(merchantNameData);
		notificationRequest.getData().add(shortAddressData);
		notificationRequest.getDeviceTokens().add(userDao.getRegistrationIdForUser(checkin.getUser().getId()));
		return notificationRequest;
	}
}
