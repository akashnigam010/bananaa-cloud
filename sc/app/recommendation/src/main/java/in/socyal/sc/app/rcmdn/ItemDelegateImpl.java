package in.socyal.sc.app.rcmdn;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import in.socyal.sc.api.DetailsRequest;
import in.socyal.sc.api.SearchRequest;
import in.socyal.sc.api.cache.dto.LocationCookieDto;
import in.socyal.sc.api.dish.dto.DishDto;
import in.socyal.sc.api.helper.exception.BusinessException;
import in.socyal.sc.api.item.response.ItemsResponse;
import in.socyal.sc.api.item.response.PopularTag;
import in.socyal.sc.api.item.response.PopularTagResponse;
import in.socyal.sc.api.item.response.SearchItemsResponse;
import in.socyal.sc.api.items.request.TrendingRequest;
import in.socyal.sc.api.merchant.dto.MerchantDto;
import in.socyal.sc.api.merchant.response.GlobalSearchItem;
import in.socyal.sc.api.merchant.response.ItemDetailsResponse;
import in.socyal.sc.api.merchant.response.MerchantDetails;
import in.socyal.sc.api.merchant.response.MerchantListForTagResponse;
import in.socyal.sc.api.type.TagType;
import in.socyal.sc.api.type.error.GenericErrorCodeType;
import in.socyal.sc.app.merchant.mapper.MerchantDelegateMapper;
import in.socyal.sc.app.rcmdn.mapper.ItemMapper;
import in.socyal.sc.persistence.DishDao;

@Component
public class ItemDelegateImpl implements ItemDelegate {
	@Autowired
	DishDao dishDao;
	@Autowired
	ItemMapper mapper;
	@Autowired
	MerchantDelegateMapper merchantMapper;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
	public SearchItemsResponse searchItems(SearchRequest request) {
		SearchItemsResponse response = new SearchItemsResponse();
		response.setItems(
				mapper.map(dishDao.searchDishAtARestaurant(request.getSearchString(), request.getMerchantId())));
		return response;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
	public ItemsResponse getPopularItems(TrendingRequest request) throws BusinessException {
		ItemsResponse response = new ItemsResponse();
		List<DishDto> result = dishDao.getPopularItems(request.getMerchantId(), request.getPage(),
				request.getResultsPerPage());
		return mapper.map(result, response);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
	public ItemDetailsResponse getItemDetails(DetailsRequest request) throws BusinessException {
		DishDto dto = dishDao.getItemDetails(request.getMerchantNameId(), request.getItemNameId());
		ItemDetailsResponse response = new ItemDetailsResponse();
		response.setDish(dto);
		response.setReviews(mapper.mapReviews(dto.getRecommendations()));
		response.setTotalRecommendations(dto.getRecommendations().size());
		return response;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
	public PopularTagResponse getPopularCuisines(LocationCookieDto cookieDto) throws BusinessException {
		PopularTagResponse response = new PopularTagResponse();
		List<PopularTag> tags = dishDao.getPopularCuisines(cookieDto, 1, 5);
		if (!tags.isEmpty()) {
			response.setTags(tags);
		}
		return response;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
	public PopularTagResponse getPopularDishes(LocationCookieDto cookieDto) throws BusinessException {
		PopularTagResponse response = new PopularTagResponse();
		List<PopularTag> tags = dishDao.getPopularSuggestions(cookieDto, 1, 5);
		if (!tags.isEmpty()) {
			response.setTags(tags);
		}
		return response;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
	public List<GlobalSearchItem> searchTags(SearchRequest request, TagType tagType) {
		return dishDao.searchTags(request.getSearchString(), 1, 3, tagType);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
	public MerchantListForTagResponse searchDishByName(String searchString, LocationCookieDto cookieDto, Integer page)
			throws BusinessException {
		MerchantListForTagResponse response = new MerchantListForTagResponse();
		response.setTotalPages(dishDao.searchDishByNamePages(searchString, cookieDto));
		if (response.getTotalPages() >= 1) {
			List<MerchantDto> dtos = dishDao.searchDishByName(searchString, cookieDto, page);
			MerchantDetails merchant = null;
			for (MerchantDto dto : dtos) {
				merchant = new MerchantDetails();
				try {
					merchantMapper.buildMerchantTagResponse(dto, merchant);
				} catch (ParseException e) {
					throw new BusinessException(GenericErrorCodeType.GENERIC_ERROR);
				}
				response.getMerchants().add(merchant);
			}
			response.setPage(page);
		}		
		return response;
	}
}
