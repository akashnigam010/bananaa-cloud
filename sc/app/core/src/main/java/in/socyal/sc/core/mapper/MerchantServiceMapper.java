package in.socyal.sc.core.mapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Component;

import in.socyal.sc.api.merchant.response.GetMerchantListResponse;
import in.socyal.sc.api.merchant.response.LocationResponse;
import in.socyal.sc.api.merchant.response.MerchantDetailsResponse;
import in.socyal.sc.api.merchant.response.MerchantResponse;
import in.socyal.sc.api.merchant.response.SearchMerchantResponse;

@Component
public class MerchantServiceMapper {

	public GetMerchantListResponse mapMerchantList() {
		GetMerchantListResponse to = new GetMerchantListResponse();
		List<MerchantResponse> merchants = new ArrayList<>();
		MerchantResponse merchant1 = new MerchantResponse();
		merchant1.setId(3241);
		merchant1.setCheckins(56);
		merchant1.setDistance(5.45);
		merchant1.setImageUrl("http://www.whitebay.in/images/fusion9.png");
		merchant1.setIsOpen(Boolean.TRUE);
		merchant1.setName("Fusion 9");
		merchant1.setRating(4.2);
		merchant1.setShortAddress("Hitech City, Hyderabad");
		MerchantResponse merchant2 = new MerchantResponse();
		merchant2.setId(2345);
		merchant2.setCheckins(32);
		merchant2.setDistance(6.20);
		merchant2.setImageUrl("http://www.whitebay.in/images/heartcupcafecoffee.png");
		merchant2.setIsOpen(Boolean.TRUE);
		merchant2.setName("Heartcup Cafe Coffee");
		merchant2.setRating(3.8);
		merchant2.setShortAddress("Madhapur, Hyderabad");
		merchants.add(merchant1);
		merchants.add(merchant2);
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
		to.setOpenTime(10.30);
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
		List<MerchantResponse> merchants = new ArrayList<>();
		MerchantResponse merchant1 = new MerchantResponse();
		merchant1.setId(3241);
		merchant1.setName("Fusion 9");
		merchant1.setShortAddress("Hitech City, Hyderabad");
		MerchantResponse merchant2 = new MerchantResponse();
		merchant2.setId(2345);
		merchant2.setName("Heartcup Cafe Coffee");
		merchant2.setShortAddress("Madhapur, Hyderabad");
		merchants.add(merchant1);
		merchants.add(merchant2);
		to.setMerchants(merchants);
		return to;
	}
}
