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
	/**
	 * Maps MerchantEntity List to MerchantDto List
	 */
	public void map(List<MerchantEntity> from, List<MerchantDto> to) {
		for (MerchantEntity entity : from) {
			MerchantDto dto = new MerchantDto();
			map(entity, dto);
			to.add(dto);
		}
	}
	
	/**
	 * Maps MerchantEntity to MerchantDto
	 */
	public void map(MerchantEntity entity, MerchantDto dto) {
		dto.setId(entity.getId());
		dto.setImageUrl(entity.getImageUrl());
		dto.setName(entity.getName());
		dto.setOpenTime(entity.getOpenTime());
		dto.setCloseTime(entity.getCloseTime());
		dto.setRating(entity.getRating());
		if (entity.getAddress() != null) {
			AddressDto addressDto = new AddressDto();
			map(entity.getAddress(), addressDto);
			dto.setAddress(addressDto);
		}
		
		if (entity.getContact() != null) {
			ContactDto contactDto = new ContactDto();
			map(entity.getContact(), contactDto);
			dto.setContact(contactDto);
		}
		dto.setCheckins(entity.getCheckins());
		dto.setCuisines(entity.getCuisines());
	}

	public void map(ContactEntity from, ContactDto to) {
		to.setEmail(from.getEmail());
		to.setId(from.getId());
		to.setMobile(from.getMobile());
		to.setTelephone(from.getTelephone());
	}

	public void map(AddressEntity from, AddressDto to) {
		to.setAddress(from.getAddress());
		to.setLocality(from.getLocality());
		to.setCity(from.getCity());
		to.setCountry(from.getCountry());
		to.setId(from.getId());
		to.setLatitude(from.getLatitude());
		to.setLongitude(from.getLongitude());
		to.setState(from.getState());
		to.setZip(from.getZip());
	}

	public void map(MerchantDto from, MerchantEntity to) {
		to.setId(from.getId());
		to.setImageUrl(from.getImageUrl());
		to.setName(from.getName());
		to.setOpenTime(from.getOpenTime());
		to.setCloseTime(from.getCloseTime());
		to.setRating(from.getRating());
		if (from.getAddress() != null) {
			AddressEntity address = new AddressEntity();
			map(from.getAddress(), address);
			to.setAddress(address);
		}
		
		if (from.getContact() != null) {
			ContactEntity contact = new ContactEntity();
			map(from.getContact(), contact);
			to.setContact(contact);
		}
		to.setCheckins(from.getCheckins());
		to.setCuisines(from.getCuisines());
	}
	
	private void map(ContactDto from, ContactEntity to) {
		to.setEmail(from.getEmail());
		to.setId(from.getId());
		to.setMobile(from.getMobile());
		to.setTelephone(from.getTelephone());
	}

	private void map(AddressDto from, AddressEntity to) {
		to.setAddress(from.getAddress());
		to.setLocality(from.getLocality());
		to.setCity(from.getCity());
		to.setCountry(from.getCountry());
		to.setId(from.getId());
		to.setLatitude(from.getLatitude());
		to.setLongitude(from.getLongitude());
		to.setState(from.getState());
		to.setZip(from.getZip());
	}
	
	
	
}
