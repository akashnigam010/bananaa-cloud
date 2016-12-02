package in.socyal.sc.persistence.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import in.socyal.sc.api.merchant.dto.AddressDto;
import in.socyal.sc.api.merchant.dto.ContactDto;
import in.socyal.sc.api.merchant.dto.MerchantDto;
import in.socyal.sc.persistence.entity.AddressEntity;
import in.socyal.sc.persistence.entity.ContactEntity;
import in.socyal.sc.persistence.entity.MerchantEntity;

@Component
public class MerchantDaoMapper {
	public void map(List<MerchantEntity> from, List<MerchantDto> to) {
		for (MerchantEntity entity : from) {
			MerchantDto dto = new MerchantDto();
			dto.setId(entity.getId());
			dto.setImageUrl(entity.getImageUrl());
			dto.setName(entity.getName());
			dto.setOpenTime(entity.getOpenTime());
			dto.setCloseTime(entity.getCloseTime());
			dto.setRating(entity.getRating());
			AddressDto addressDto = new AddressDto();
			map(entity.getAddress(), addressDto);
			dto.setAddress(addressDto);
			ContactDto contactDto = new ContactDto();
			map(entity.getContact(), contactDto);
			dto.setContact(contactDto);
			dto.setCheckins(entity.getCheckins());
			to.add(dto);
		}
	}

	public void map(ContactEntity from, ContactDto to) {
		to.setEmail(from.getEmail());
		to.setId(from.getId());
		to.setMobile(from.getMobile());
		to.setTelephone(from.getTelephone());
	}

	public void map(AddressEntity from, AddressDto to) {
		to.setAddressLine1(from.getAddressLine1());
		to.setAddressLine2(from.getAddressLine2());
		to.setCity(from.getCity());
		to.setCountry(from.getCountry());
		to.setId(from.getId());
		to.setLatitude(from.getLatitude());
		to.setLongitude(from.getLongitude());
		to.setState(from.getState());
		to.setZip(from.getZip());
	}
	
	
	
}
