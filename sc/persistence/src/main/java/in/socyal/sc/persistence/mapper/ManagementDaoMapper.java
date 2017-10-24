package in.socyal.sc.persistence.mapper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import in.socyal.sc.api.dish.dto.ItemImageDto;
import in.socyal.sc.api.manage.request.AddItemRequest;
import in.socyal.sc.api.manage.request.NewMerchantRequest;
import in.socyal.sc.api.manage.request.Tag;
import in.socyal.sc.api.manage.response.Item;
import in.socyal.sc.persistence.entity.AddressEntity;
import in.socyal.sc.persistence.entity.ContactEntity;
import in.socyal.sc.persistence.entity.CuisineEntity;
import in.socyal.sc.persistence.entity.DishEntity;
import in.socyal.sc.persistence.entity.ItemImageEntity;
import in.socyal.sc.persistence.entity.LocalityEntity;
import in.socyal.sc.persistence.entity.MerchantEntity;
import in.socyal.sc.persistence.entity.SuggestionEntity;
import in.socyal.sc.persistence.entity.TagEntity;

@Component
public class ManagementDaoMapper {

	@Autowired
	DishDaoMapper dishDaoMapper;

	public DishEntity map(AddItemRequest request, MerchantEntity merchant) {
		Calendar cal = Calendar.getInstance();
		DishEntity entity = new DishEntity(cal, cal);
		entity.setName(request.getName());
		entity.setNameId(request.getNameId());
		entity.setVegnonveg(request.getVegnonveg());
		entity.setMerchant(merchant);
		entity.setImageUrl(request.getImageUrl());
		entity.setThumbnail(request.getThumbnail());
		entity.setIsActive(request.getIsActive());
		return entity;
	}

	public List<ItemImageDto> mapItemImages(List<ItemImageEntity> entities) {
		List<ItemImageDto> dtos = new ArrayList<>();
		ItemImageDto dto = null;
		for (ItemImageEntity entity : entities) {
			dto = new ItemImageDto();
			dto.setId(entity.getId());
			dto.setName(entity.getName());
			dto.setUrl(entity.getImageUrl());
			dto.setThumbnail(entity.getThumbnail());
			dtos.add(dto);
		}
		return dtos;
	}

	public List<Item> map(List<DishEntity> entities) {
		List<Item> dishes = new ArrayList<>();
		Item item = null;
		for (DishEntity dish : entities) {
			item = new Item();
			item.setId(dish.getId());
			item.setName(dish.getName());
			item.setVegnonveg(dish.getVegnonveg());
			item.setThumbnail(dish.getThumbnail());
			item.setImage(dish.getImageUrl());
			item.setCuisines(mapCuisines(dish.getCuisines()));
			item.setSuggestions(mapSuggestions(dish.getSuggestions()));
			dishes.add(item);
		}
		return dishes;
	}
	
	public MerchantEntity mapNewMerchant(NewMerchantRequest request, LocalityEntity locality) {
		Calendar calendar = Calendar.getInstance();
		MerchantEntity entity = new MerchantEntity();
		entity.setName(request.getName());
		entity.setNameId(request.getNameId());
		entity.setThumbnail(request.getThumbnail());
		entity.setImageUrl(request.getImageUrl());
		entity.setContact(getContact(request.getPhone(), calendar));
		entity.setAddress(getAddress(request.getAddress(), calendar, locality));
		entity.setAverageCost(request.getAverageCost().doubleValue());
		entity.setType(getType(request.getType()));
		entity.setIsActive(Boolean.FALSE);
		entity.setCanEdit(Boolean.FALSE);
		entity.setCreatedDateTime(calendar);
		entity.setUpdatedDateTime(calendar);
		return entity;
	}
	
	private ContactEntity getContact(String phone, Calendar calendar) {
		ContactEntity entity = new ContactEntity();
		entity.setPhone1(phone);
		entity.setCreatedDateTime(calendar);
		entity.setUpdatedDateTime(calendar);
		return entity;
	}
	
	private AddressEntity getAddress(String address, Calendar calendar, LocalityEntity locality) {
		AddressEntity entity = new AddressEntity();
		entity.setAddress(address);
		entity.setLocality(locality);
		entity.setCreatedDateTime(calendar);
		entity.setUpdatedDateTime(calendar);
		return entity;
	}
	
	private String getType(List<String> type) {
		String typeString = "";
		int i=0;
		for (i = 0; i < type.size() - 1; i++) {
			typeString += type.get(i) + ", ";
		}
		typeString += type.get(i);
		return typeString;
	}

	private List<Tag> mapSuggestions(List<SuggestionEntity> tags) {
		List<Tag> tagList = new ArrayList<>();
		Tag dto = null;
		for (TagEntity tag : tags) {
			dto = new Tag();
			dto.setId(tag.getId());
			dto.setName(tag.getName());
			tagList.add(dto);
		}
		return tagList;
	}
	
	private List<Tag> mapCuisines(List<CuisineEntity> tags) {
		List<Tag> tagList = new ArrayList<>();
		Tag dto = null;
		for (TagEntity tag : tags) {
			dto = new Tag();
			dto.setId(tag.getId());
			dto.setName(tag.getName());
			tagList.add(dto);
		}
		return tagList;
	}
}
