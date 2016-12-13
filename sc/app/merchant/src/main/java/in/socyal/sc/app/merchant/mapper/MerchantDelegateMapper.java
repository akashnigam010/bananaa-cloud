package in.socyal.sc.app.merchant.mapper;

import org.springframework.stereotype.Component;

import in.socyal.sc.api.merchant.dto.AddressDto;
import in.socyal.sc.api.merchant.dto.ContactDto;
import in.socyal.sc.api.merchant.dto.GetMerchantListRequestDto;
import in.socyal.sc.api.merchant.dto.MerchantDto;
import in.socyal.sc.api.merchant.request.GetMerchantListRequest;
import in.socyal.sc.api.merchant.request.SaveMerchantDetailsRequest;

@Component
public class MerchantDelegateMapper {
	public void map(GetMerchantListRequest request, GetMerchantListRequestDto requestDto) {
		requestDto.setLatitude(request.getLocation().getLatitude());
		requestDto.setLongitude(request.getLocation().getLongitude());
		requestDto.setPage(request.getPage());
	}

	public void map(SaveMerchantDetailsRequest from, MerchantDto to) {
		AddressDto address = new AddressDto();
		address.setAddress(from.getAddress());
		address.setLocality(from.getLocality());
		address.setCity(from.getCity());
		address.setCountry(from.getCountry());
		address.setLatitude(from.getLatitude());
		address.setLongitude(from.getLongitude());
		address.setState(from.getState());
		address.setZip(from.getZip());
		to.setAddress(address);
		to.setCheckins(0);
		to.setCloseTime(from.getCloseTime());
		ContactDto contact = new ContactDto();
		contact.setEmail(from.getEmail());
		contact.setMobile(from.getMobile());
		contact.setTelephone(from.getTelephone());
		to.setContact(contact);
		to.setCuisines(from.getCuisines());
		to.setImageUrl(from.getImageUrl());
		to.setName(from.getMerchantName());
		to.setOpenTime(from.getOpenTime());
		to.setRating(from.getRating());
	}
}
