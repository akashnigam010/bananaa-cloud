package in.socyal.sc.app.rcmdn;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import in.socyal.sc.api.DetailsRequest;
import in.socyal.sc.api.GenericSearchRequest;
import in.socyal.sc.api.cache.dto.LocationCookieDto;
import in.socyal.sc.api.dish.dto.DishDto;
import in.socyal.sc.api.dish.dto.DishFilterCriteria;
import in.socyal.sc.api.engine.request.IdRequest;
import in.socyal.sc.api.helper.exception.BusinessException;
import in.socyal.sc.api.item.response.ItemsResponse;
import in.socyal.sc.api.item.response.PopularTag;
import in.socyal.sc.api.item.response.PopularTagResponse;
import in.socyal.sc.api.item.response.SearchItemsResponse;
import in.socyal.sc.api.items.request.TrendingRequest;
import in.socyal.sc.api.merchant.dto.MerchantDto;
import in.socyal.sc.api.merchant.request.SearchRequest;
import in.socyal.sc.api.merchant.response.AppItemDetailsResponse;
import in.socyal.sc.api.merchant.response.GlobalSearchItem;
import in.socyal.sc.api.merchant.response.ItemDetailsResponse;
import in.socyal.sc.api.merchant.response.MerchantDetails;
import in.socyal.sc.api.merchant.response.MerchantListForTagResponse;
import in.socyal.sc.api.type.TagType;
import in.socyal.sc.api.type.error.GenericErrorCodeType;
import in.socyal.sc.api.type.error.UserErrorCodeType;
import in.socyal.sc.api.user.dto.UserTagPreference;
import in.socyal.sc.app.merchant.mapper.MerchantDelegateMapper;
import in.socyal.sc.app.rcmdn.mapper.ItemMapper;
import in.socyal.sc.helper.security.jwt.JwtTokenHelper;
import in.socyal.sc.persistence.CacheDao;
import in.socyal.sc.persistence.DishDao;

@Component
public class ItemDelegateImpl implements ItemDelegate {
	@Autowired
	DishDao dishDao;
	@Autowired
	ItemMapper mapper;
	@Autowired
	MerchantDelegateMapper merchantMapper;
	@Autowired
	JwtTokenHelper jwtHelper;
	@Autowired
	CacheDao cacheDao;

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
	public AppItemDetailsResponse getItemDetailsById(IdRequest request) throws BusinessException {
		DishFilterCriteria dishCriteria = new DishFilterCriteria(true, true);
		DishDto dto = dishDao.getItemDetailsById(request.getId(), dishCriteria);
		AppItemDetailsResponse response = mapper.mapAppDetailsReponse(dto);
		return response;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
	public ItemDetailsResponse getItemDetailsWithFoodviews(DetailsRequest request) throws BusinessException {
		DishFilterCriteria dishCriteria = new DishFilterCriteria(false, false, true, true);
		DishDto dto = dishDao.getItemDetailsByNameId(request.getMerchantNameId(), request.getItemNameId(), dishCriteria);
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
	public List<GlobalSearchItem> searchTags(GenericSearchRequest request, TagType tagType, Integer page,
			Integer resultsPerPage) {
		return dishDao.searchTags(request.getSearchString(), page, resultsPerPage, tagType);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
	public List<UserTagPreference> searchTagsWithUserPrefs(GenericSearchRequest request, TagType tagType,
			Integer page, Integer resultsPerPage) throws BusinessException {
		if (!jwtHelper.isUserLoggedIn()) {
			throw new BusinessException(UserErrorCodeType.USER_NOT_LOGGED_IN);
		} else {
			return dishDao.getTagsMappedWithUserPrefs(tagType, request.getSearchString(), jwtHelper.getUserId(), page,
					resultsPerPage);
		}
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
