package in.socyal.sc.app.merchant.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import in.socyal.sc.api.merchant.dto.GetMerchantListRequestDto;
import in.socyal.sc.api.merchant.dto.MerchantDto;
import in.socyal.sc.api.merchant.dto.TrendingMerchantResultDto;
import in.socyal.sc.api.merchant.request.GetMerchantListRequest;
import in.socyal.sc.api.merchant.request.SaveMerchantDetailsRequest;
import in.socyal.sc.api.merchant.response.GetTrendingMerchantsResponse;
import in.socyal.sc.api.merchant.response.TrendingMerchant;

@Component
public class MerchantDelegateMapper {
	public void map(GetMerchantListRequest request, GetMerchantListRequestDto requestDto) {
		requestDto.setLatitude(request.getLocation().getLatitude());
		requestDto.setLongitude(request.getLocation().getLongitude());
		requestDto.setPage(request.getPage());
	}

	public void map(SaveMerchantDetailsRequest from, MerchantDto to) {
//		AddressDto address = new AddressDto();
//		address.setAddress(from.getAddress());
//		LocalityDto locality = new LocalityDto();
//		locality.setId(from.getLocalityId());
//		address.setLocality(locality);
//		address.setCity(from.getCity());
//		address.setCountry(from.getCountry());
//		address.setLatitude(from.getLatitude());
//		address.setLongitude(from.getLongitude());
//		address.setState(from.getState());
//		address.setZip(from.getZip());
//		to.setAddress(address);
//		to.setCheckins(0);
//		to.setCloseTime(from.getCloseTime());
//		ContactDto contact = new ContactDto();
//		contact.setEmail(from.getEmail());
//		contact.setMobile(from.getMobile());
//		contact.setTelephone(from.getTelephone());
//		to.setContact(contact);
//		to.setCuisines(from.getCuisines());
//		to.setImageUrl(from.getImageUrl());
//		to.setName(from.getMerchantName());
//		to.setOpenTime(from.getOpenTime());
//		to.setRating(from.getRating());
	}

	public void map(List<TrendingMerchantResultDto> result, GetTrendingMerchantsResponse response) {
		List<TrendingMerchant> merchants = new ArrayList<>();
		for (TrendingMerchantResultDto dto : result) {
			TrendingMerchant trendingMerchant = new TrendingMerchant();
			MerchantDto merchant = dto.getMerchant();
			trendingMerchant.setId(merchant.getId());
			trendingMerchant.setImageUrl(merchant.getImageUrl());
			trendingMerchant.setName(merchant.getName());
			trendingMerchant.setNameId(merchant.getNameId());
			trendingMerchant.setRecommendations(dto.getRecommendations());
			trendingMerchant.setShortAddress(merchant.getAddress().getLocality().getShortAddress());
			trendingMerchant.setMerchantUrl(merchant.getMerchantUrl());
			merchants.add(trendingMerchant);
		}
		
		response.setMerchants(merchants);
	}
}
