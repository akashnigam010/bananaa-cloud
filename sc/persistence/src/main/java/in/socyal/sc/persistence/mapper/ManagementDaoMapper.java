package in.socyal.sc.persistence.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import in.socyal.sc.api.dish.dto.ItemImageDto;
import in.socyal.sc.api.manage.request.AddItemRequest;
import in.socyal.sc.persistence.entity.DishEntity;
import in.socyal.sc.persistence.entity.ItemImageEntity;
import in.socyal.sc.persistence.entity.MerchantEntity;

@Component
public class ManagementDaoMapper {

	@Autowired
	DishDaoMapper dishDaoMapper;

	public DishEntity map(AddItemRequest request, MerchantEntity merchant) {
		DishEntity entity = new DishEntity();
		entity.setName(request.getName());
		entity.setNameId(request.getNameId());
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
}
