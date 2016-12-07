package in.socyal.sc.core.mapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Component;

import in.socyal.sc.api.merchant.response.LocationResponse;
import in.socyal.sc.api.merchant.response.MerchantDetailsResponse;
import in.socyal.sc.api.merchant.response.MerchantDto;
import in.socyal.sc.api.merchant.response.MerchantListResponse;
import in.socyal.sc.api.merchant.response.SearchMerchantResponse;

@Component
public class MerchantServiceMapper {

	public MerchantListResponse mapMerchantList(int id1, int id2, int id3, int id4, int id5, int id6) {
		MerchantListResponse to = new MerchantListResponse();
		List<MerchantDto> merchants = new ArrayList<>();
		MerchantDto merchant1 = new MerchantDto();
		merchant1.setId(id1);
		merchant1.setCheckins(56);
		merchant1.setDistance(5.45);
		merchant1.setImageUrl("http://www.whitebay.in/images/fusion9.png");
		merchant1.setIsOpen(Boolean.TRUE);
		merchant1.setName("Fusion 9");
		merchant1.setRating(4.2);
		merchant1.setShortAddress("Hitech City, Hyderabad");
		MerchantDto merchant2 = new MerchantDto();
		merchant2.setId(id2);
		merchant2.setCheckins(32);
		merchant2.setDistance(6.20);
		merchant2.setImageUrl("http://www.whitebay.in/images/heartcupcafecoffee.png");
		merchant2.setIsOpen(Boolean.TRUE);
		merchant2.setName("Heartcup Cafe Coffee");
		merchant2.setRating(3.8);
		merchant2.setShortAddress("Madhapur, Hyderabad");
		MerchantDto merchant3 = new MerchantDto();
		merchant3.setId(id3);
		merchant3.setCheckins(32);
		merchant3.setDistance(6.20);
		merchant3.setImageUrl("http://www.whitebay.in/images/heartcupcafecoffee.png");
		merchant3.setIsOpen(Boolean.TRUE);
		merchant3.setName("Via Milano");
		merchant3.setRating(3.8);
		merchant3.setShortAddress("Jubilee Hills, Hyderabad");
		MerchantDto merchant4 = new MerchantDto();
		merchant4.setId(id4);
		merchant4.setCheckins(32);
		merchant4.setDistance(6.20);
		merchant4.setImageUrl("http://www.whitebay.in/images/heartcupcafecoffee.png");
		merchant4.setIsOpen(Boolean.TRUE);
		merchant4.setName("Soda Bottle Opener Wala");
		merchant4.setRating(3.8);
		merchant4.setShortAddress("Jubilee Hills, Hyderabad");
		MerchantDto merchant5 = new MerchantDto();
		merchant5.setId(id5);
		merchant5.setCheckins(32);
		merchant5.setDistance(6.20);
		merchant5.setImageUrl("http://www.whitebay.in/images/heartcupcafecoffee.png");
		merchant5.setIsOpen(Boolean.TRUE);
		merchant5.setName("Free Flow");
		merchant5.setRating(3.8);
		merchant5.setShortAddress("Jubilee Hills, Hyderabad");
		MerchantDto merchant6 = new MerchantDto();
		merchant6.setId(id6);
		merchant6.setCheckins(32);
		merchant6.setDistance(6.20);
		merchant6.setImageUrl("http://www.whitebay.in/images/heartcupcafecoffee.png");
		merchant6.setIsOpen(Boolean.TRUE);
		merchant6.setName("Exotica");
		merchant6.setRating(3.8);
		merchant6.setShortAddress("Banjara Hills, Hyderabad");
		merchants.add(merchant1);
		merchants.add(merchant2);
		merchants.add(merchant3);
		merchants.add(merchant4);
		merchants.add(merchant5);
		merchants.add(merchant6);
		to.setMerchants(merchants);
		return to;
	}

	public MerchantDetailsResponse mapMerchantDetails() {
		MerchantDetailsResponse to = new MerchantDetailsResponse();
		to.setAverageCost(200.00);
		to.setCheckins(24);
		List<String> cuisines = new ArrayList<>();
		cuisines.add("Continental");
		cuisines.add("Indian");
		cuisines.add("Thai");
		to.setCuisines(cuisines);
		to.setDistance(3.46);
		to.setId(121);
		to.setImageUrl("http://www.whitebay.in/images/heartcupcafecoffee.png");
		to.setIsOpen(Boolean.TRUE);
		to.setOpenTime("10:30 AM to 12 Midnight");
		LocationResponse location = new LocationResponse();
		location.setLatitude(76.234567);
		location.setLongitude(78.398762);
		to.setLocation(location);
		to.setLongAddress(
				"3rd Floor 136/137, Ancis Eco Grand, Near Wipro Lake, Nanakramguda, Financial District, Hitech City, Hyderabad");
		to.setName("Fusion 9");
		to.setShortAddress("Hitech City, Hyderabad");
		to.setType(Collections.singletonList("Fine Dining"));
		to.setRating(4.6);
		return to;
	}

	public SearchMerchantResponse mapSearchMerchantResponse() {
		SearchMerchantResponse to = new SearchMerchantResponse();
		List<MerchantDto> merchants = new ArrayList<>();
		MerchantDto merchant1 = new MerchantDto();
		merchant1.setId(3241);
		merchant1.setName("Fusion 9");
		merchant1.setShortAddress("Hitech City, Hyderabad");
		MerchantDto merchant2 = new MerchantDto();
		merchant2.setId(2345);
		merchant2.setName("Heartcup Cafe Coffee");
		merchant2.setShortAddress("Madhapur, Hyderabad");
		merchants.add(merchant1);
		merchants.add(merchant2);
		to.setMerchants(merchants);
		return to;
	}
}
