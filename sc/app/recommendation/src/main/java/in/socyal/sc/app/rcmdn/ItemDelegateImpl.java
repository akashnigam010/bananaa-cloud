package in.socyal.sc.app.rcmdn;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import in.socyal.sc.api.DetailsRequest;
import in.socyal.sc.api.SearchRequest;
import in.socyal.sc.api.helper.exception.BusinessException;
import in.socyal.sc.api.item.response.ItemsResponse;
import in.socyal.sc.api.items.dto.DishResultDto;
import in.socyal.sc.api.items.request.GetPopularItemsRequest;
import in.socyal.sc.api.merchant.response.ItemDetailsResponse;
import in.socyal.sc.api.recommendation.dto.RecommendationDto;
import in.socyal.sc.api.type.error.DishErrorCodeType;
import in.socyal.sc.app.rcmdn.mapper.ItemMapper;
import in.socyal.sc.persistence.DishDao;

@Component
public class ItemDelegateImpl implements ItemDelegate {
	@Autowired
	DishDao dishDao;
	@Autowired
	ItemMapper mapper;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
	public ItemsResponse searchItems(SearchRequest request) {
		ItemsResponse response = new ItemsResponse();
		response.setItems(
				mapper.map(dishDao.searchDishAtARestaurant(request.getSearchString(), request.getMerchantId())));
		return response;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
	public ItemsResponse getPopularItems(GetPopularItemsRequest request) throws BusinessException {
		ItemsResponse response = new ItemsResponse();
		List<DishResultDto> result = dishDao.getPopularDishesOfMerchant(request.getMerchantId(),
				request.getPage(), request.getResultsPerPage());
		return mapper.map(result, response);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
	public ItemDetailsResponse getItemDetails(DetailsRequest request) throws BusinessException {
		DishResultDto dishResult = dishDao.getItemDetails(request.getMerchantNameId(), request.getItemNameId());
		if (dishResult == null) {
			throw new BusinessException(DishErrorCodeType.DISH_ID_NOT_FOUND);
		}
		List<RecommendationDto> reviews = dishDao.getReviews(dishResult.getDish().getId());
		return mapper.map(dishResult, reviews);
	}
}
