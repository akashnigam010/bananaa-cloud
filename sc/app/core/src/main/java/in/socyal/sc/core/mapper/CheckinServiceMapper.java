package in.socyal.sc.core.mapper;

import java.util.ArrayList;
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

	public void map(List<CheckinResponseDto> from, CheckinResponse to) {
		List<Checkin> checkins = new ArrayList<>();
		//1
		Checkin checkin1 = new Checkin();
		checkin1.setId(23);
		checkin1.setLikeCount(2);
		checkin1.setRating(3.9);
		checkin1.setRewardMessage("Won an Amazon Gift Card worth Rs. 100");
		List<TaggedUserResponse> taggedUsers = new ArrayList<>();
		TaggedUserResponse taggedUser1 = new TaggedUserResponse();
		taggedUser1.setId(2);
		taggedUser1.setName("Uday");
		TaggedUserResponse taggedUser2 = new TaggedUserResponse();
		taggedUser2.setId(32);
		taggedUser2.setName("Sherin Joy");
		taggedUsers.add(taggedUser1);
		taggedUsers.add(taggedUser2);
		checkin1.setTaggedUsers(taggedUsers);
		checkin1.setTimestamp(new Date());
		UserDetailsResponse user = new UserDetailsResponse();
		user.setId(23);
		user.setImageUrl("https://gl.cyl");
		user.setName("Pridhvi Manohar");
		user.setUserCheckins(8);
		checkin1.setUser(user);
		checkins.add(checkin1);
		//2
		Checkin checkin2 = new Checkin();
		checkin2.setId(23);
		checkin2.setLikeCount(2);
		checkin2.setRating(3.9);
		checkin2.setRewardMessage("Won a Free Chocolate goodie worth Rs. 500");
		List<TaggedUserResponse> taggedUsers2 = new ArrayList<>();
		TaggedUserResponse taggedUser3 = new TaggedUserResponse();
		taggedUser3.setId(2);
		taggedUser3.setName("Vijay");
		TaggedUserResponse taggedUser4 = new TaggedUserResponse();
		taggedUser4.setId(32);
		taggedUser4.setName("Lemos Ashley");
		taggedUsers2.add(taggedUser3);
		taggedUsers2.add(taggedUser4);
		checkin2.setTaggedUsers(taggedUsers2);
		checkin2.setTimestamp(new Date());
		UserDetailsResponse user2 = new UserDetailsResponse();
		user2.setId(23);
		user2.setImageUrl("https://ed.cyl");
		user2.setName("Pinto Frida");
		user2.setUserCheckins(8);
		checkin2.setUser(user2);
		checkins.add(checkin2);
		//3
		Checkin checkin3 = new Checkin();
		checkin3.setId(23);
		checkin3.setLikeCount(2);
		checkin3.setRating(3.9);
		checkin3.setRewardMessage("Won a Free Chocolate goodie worth Rs. 500");
		List<TaggedUserResponse> taggedUsers3 = new ArrayList<>();
		TaggedUserResponse taggedUser5 = new TaggedUserResponse();
		taggedUser5.setId(2);
		taggedUser5.setName("Vijay");
		TaggedUserResponse taggedUser6 = new TaggedUserResponse();
		taggedUser6.setId(32);
		taggedUser6.setName("Lemos Ashley");
		taggedUsers3.add(taggedUser5);
		taggedUsers3.add(taggedUser6);
		checkin3.setTaggedUsers(taggedUsers3);
		checkin3.setTimestamp(new Date());
		UserDetailsResponse user3 = new UserDetailsResponse();
		user3.setId(23);
		user3.setImageUrl("https://ed.cyl");
		user3.setName("Pinto Frida");
		user3.setUserCheckins(8);
		checkin3.setUser(user3);
		checkins.add(checkin3);
		to.setCheckins(checkins);
	}
}
