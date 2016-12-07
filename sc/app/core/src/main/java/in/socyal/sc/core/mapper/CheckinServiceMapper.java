package in.socyal.sc.core.mapper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import in.socyal.sc.api.checkin.dto.CheckinResponseDto;
import in.socyal.sc.api.checkin.response.Checkin;
import in.socyal.sc.api.checkin.response.CheckinResponse;
import in.socyal.sc.api.checkin.response.TaggedUserResponse;
import in.socyal.sc.api.checkin.response.UserDetailsResponse;

@Component
public class CheckinServiceMapper {

	public void map(List<CheckinResponseDto> from, CheckinResponse to, int page) {
		List<Checkin> checkins = new ArrayList<>();
		Calendar now = Calendar.getInstance();
		if (page == 1) {
			Date currentDate = now.getTime();

			Calendar minus5HoursCal = now;
			minus5HoursCal.add(Calendar.HOUR, -5);
			Date minus5Hours = minus5HoursCal.getTime();

			Calendar minus13HoursCal = now;
			minus13HoursCal.add(Calendar.HOUR, -13);
			Date minus13Hours = minus13HoursCal.getTime();

			Calendar minus1DayCal = now;
			minus1DayCal.add(Calendar.DATE, -1);
			Date minus1Day = minus1DayCal.getTime();

			Calendar minus2DayCal = now;
			minus1DayCal.add(Calendar.DATE, -2);
			Date minus2Day = minus2DayCal.getTime();

			// 1
			Checkin checkin1 = new Checkin();
			checkin1.setId(1);
			checkin1.setLikeCount(2);
			checkin1.setRating(3.9);
			checkin1.setRewardMessage("Won an Amazon Gift Card worth Rs. 100");
			checkin1.setTaggedUsers(getTaggedUsersResponse(1));
			checkin1.setTimestamp(currentDate);
			checkin1.setUser(getUserDetailsResponse(1));

			// 2
			Checkin checkin2 = new Checkin();
			checkin2.setId(2);
			checkin2.setLikeCount(21);
			checkin2.setRating(3.9);
			checkin2.setRewardMessage("Won an Amazon Gift Card worth Rs. 100");
			checkin2.setTaggedUsers(getTaggedUsersResponse(2));
			checkin2.setTimestamp(minus5Hours);
			checkin2.setUser(getUserDetailsResponse(2));

			// 3
			Checkin checkin3 = new Checkin();
			checkin3.setId(3);
			checkin3.setLikeCount(2);
			checkin3.setRating(3.9);
			checkin3.setRewardMessage("Won an Amazon Gift Card worth Rs. 100");
			checkin3.setTaggedUsers(getTaggedUsersResponse(3));
			checkin3.setTimestamp(minus13Hours);
			checkin3.setUser(getUserDetailsResponse(3));

			// 4
			Checkin checkin4 = new Checkin();
			checkin4.setId(4);
			checkin4.setLikeCount(2);
			checkin4.setRating(3.9);
			checkin4.setRewardMessage("Won an Amazon Gift Card worth Rs. 100");
			checkin4.setTaggedUsers(getTaggedUsersResponse(4));
			checkin4.setTimestamp(minus1Day);
			checkin4.setUser(getUserDetailsResponse(4));

			// 5
			Checkin checkin5 = new Checkin();
			checkin5.setId(5);
			checkin5.setLikeCount(2);
			checkin5.setRating(3.9);
			checkin5.setRewardMessage("Won an Amazon Gift Card worth Rs. 100");
			checkin5.setTaggedUsers(getTaggedUsersResponse(5));
			checkin5.setTimestamp(minus2Day);
			checkin5.setUser(getUserDetailsResponse(5));

			checkins.add(checkin1);
			checkins.add(checkin2);
			checkins.add(checkin3);
			checkins.add(checkin4);
			checkins.add(checkin5);	
			
		}
		if (page == 2) {
			
			Calendar minus20DayCal = now;
			minus20DayCal.add(Calendar.DATE, -20);
			Date minus20Day = minus20DayCal.getTime();

			Calendar minus40DayCal = now;
			minus40DayCal.add(Calendar.DATE, -40);
			Date minus40Day = minus40DayCal.getTime();

			Calendar minus90DayCal = now;
			minus90DayCal.add(Calendar.DATE, -90);
			Date minus90Day = minus90DayCal.getTime();

			Calendar minus400DayCal = now;
			minus400DayCal.add(Calendar.DATE, -400);
			Date minus400Day = minus400DayCal.getTime();
			
			// 6
			Checkin checkin6 = new Checkin();
			checkin6.setId(6);
			checkin6.setLikeCount(25);
			checkin6.setRating(3.9);
			checkin6.setRewardMessage("Won an Amazon Gift Card worth Rs. 100");
			checkin6.setTaggedUsers(getTaggedUsersResponse(6));
			checkin6.setTimestamp(minus20Day);
			checkin6.setUser(getUserDetailsResponse(6));

			// 7
			Checkin checkin7 = new Checkin();
			checkin7.setId(7);
			checkin7.setLikeCount(2);
			checkin7.setRating(3.9);
			checkin7.setRewardMessage("Won an Amazon Gift Card worth Rs. 100");
			checkin7.setTaggedUsers(getTaggedUsersResponse(7));
			checkin7.setTimestamp(minus40Day);
			checkin7.setUser(getUserDetailsResponse(7));

			// 8
			Checkin checkin8 = new Checkin();
			checkin8.setId(8);
			checkin8.setLikeCount(32);
			checkin8.setRating(3.9);
			checkin8.setRewardMessage("Won an Amazon Gift Card worth Rs. 100");
			checkin8.setTaggedUsers(getTaggedUsersResponse(8));
			checkin8.setTimestamp(minus90Day);
			checkin8.setUser(getUserDetailsResponse(8));

			// 9
			Checkin checkin9 = new Checkin();
			checkin9.setId(9);
			checkin9.setLikeCount(2);
			checkin9.setRating(3.9);
			checkin9.setRewardMessage("Won an Amazon Gift Card worth Rs. 100");
			checkin9.setTaggedUsers(getTaggedUsersResponse(9));
			checkin9.setTimestamp(minus400Day);
			checkin9.setUser(getUserDetailsResponse(9));
			
			checkins.add(checkin6);
			checkins.add(checkin7);
			checkins.add(checkin8);
			checkins.add(checkin9);
		}
		
		to.setCheckins(checkins);

	}

	private List<TaggedUserResponse> getTaggedUsersResponse(int id) {
		List<TaggedUserResponse> taggedUsers = new ArrayList<>();
		int defaultId = id * 10;
		TaggedUserResponse taggedUser = new TaggedUserResponse();
		taggedUser.setId(defaultId + 1);
		taggedUser.setName("Vijay");
		TaggedUserResponse taggedUser2 = new TaggedUserResponse();
		taggedUser2.setId(defaultId + 2);
		taggedUser2.setName("Lemos Ashley");
		taggedUsers.add(taggedUser);
		taggedUsers.add(taggedUser2);
		return taggedUsers;
	}

	private UserDetailsResponse getUserDetailsResponse(int id) {
		UserDetailsResponse user = new UserDetailsResponse();
		if (id % 3 == 0) {
			user.setId(id * 11);
			user.setImageUrl("http://www.whitebay.in/images/sanjeevven_sec.png");
			user.setName("Sanjeev Venkatraman");
			user.setUserCheckins(8);
		} else if (id % 3 == 1) {
			user.setId(id * 11 + 1);
			user.setImageUrl("http://www.whitebay.in/images/vikarmchau_sec.png");
			user.setName("Vikram Chaudhary");
			user.setUserCheckins(134);
		} else {
			user.setId(id * 11 + 2);
			user.setImageUrl("http://www.whitebay.in/images/vaibhavgup_sec.png");
			user.setName("Vaibhav Gupta");
			user.setUserCheckins(12);
		}
		return user;
	}
}
