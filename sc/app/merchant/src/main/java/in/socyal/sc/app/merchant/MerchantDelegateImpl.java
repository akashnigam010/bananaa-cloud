package in.socyal.sc.app.merchant;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import in.socyal.sc.api.DetailsRequest;
import in.socyal.sc.api.GenericSearchRequest;
import in.socyal.sc.api.cache.dto.LocationCookieDto;
import in.socyal.sc.api.helper.exception.BusinessException;
import in.socyal.sc.api.item.response.Tag;
import in.socyal.sc.api.merchant.dto.MerchantDto;
import in.socyal.sc.api.merchant.dto.MerchantFilterCriteria;
import in.socyal.sc.api.merchant.dto.TrendingMerchantResultDto;
import in.socyal.sc.api.merchant.request.SearchMerchantByTagRequest;
import in.socyal.sc.api.merchant.request.SearchRequest;
import in.socyal.sc.api.merchant.response.GetTrendingMerchantsResponse;
import in.socyal.sc.api.merchant.response.GlobalSearchItem;
import in.socyal.sc.api.merchant.response.MerchantDetails;
import in.socyal.sc.api.merchant.response.MerchantListForTagResponse;
import in.socyal.sc.api.merchant.response.SearchMerchantResponse;
import in.socyal.sc.api.type.TagType;
import in.socyal.sc.api.type.error.GenericErrorCodeType;
import in.socyal.sc.app.merchant.mapper.MerchantDelegateMapper;
import in.socyal.sc.helper.security.jwt.JwtTokenHelper;
import in.socyal.sc.persistence.MerchantDao;
import in.socyal.sc.persistence.RecommendationDao;

@Service
public class MerchantDelegateImpl implements MerchantDelegate {
	private static final Integer PAGE = 1;
	private static final Integer RESULTS_PER_PAGE = 10;
	private static final Integer RESULTS_PER_PAGE_TRENDING = 5;
	
	@Autowired
	MerchantDao dao;
	@Autowired
	MerchantDelegateMapper mapper;
	@Autowired
	RecommendationDao rcmdnDao;
	@Autowired
	JwtTokenHelper jwtHelper;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
	public MerchantListForTagResponse getMerchantsByTag(SearchMerchantByTagRequest request) throws BusinessException {
		MerchantListForTagResponse response = new MerchantListForTagResponse();
		response.setTotalPages(dao.getMerchantSearchByTagPages(request));
		if (response.getTotalPages() >= 1) {
			List<MerchantDto> dtos = dao.getMerchantsByTag(request);
			MerchantDetails merchant = null;
			for (MerchantDto dto : dtos) {
				merchant = new MerchantDetails();
				try {
					mapper.buildMerchantTagResponse(dto, merchant);
					mapSearchTag(request, dto, merchant);
				} catch (ParseException e) {
					throw new BusinessException(GenericErrorCodeType.GENERIC_ERROR);
				}
				response.getMerchants().add(merchant);
			}
			response.setPage(request.getPage());
		}
		return response;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
	public MerchantListForTagResponse getAllSortedMerchants(LocationCookieDto cookieDto, Integer page) throws BusinessException {
		MerchantListForTagResponse response = new MerchantListForTagResponse();
		response.setTotalPages(dao.getAllSortedMerchantsPages(cookieDto));
		if (response.getTotalPages() >= 1) {
			MerchantFilterCriteria filter = new MerchantFilterCriteria(true, true, true, true, false, true);
			List<TrendingMerchantResultDto> dtos = dao.getAllSortedMerchants(cookieDto, filter, page, RESULTS_PER_PAGE);
			MerchantDetails merchant = null;
			for (TrendingMerchantResultDto dto : dtos) {
				merchant = new MerchantDetails();
				try {
					mapper.buildMerchantTagResponse(dto.getMerchant(), merchant);
					Tag tag = new Tag();
					tag.setRating(dto.getRating().toString());
					tag.setName("Food");
					merchant.setSearchTag(tag);
				} catch (ParseException e) {
					throw new BusinessException(GenericErrorCodeType.GENERIC_ERROR);
				}
				response.getMerchants().add(merchant);
			}
			response.setPage(page);
		}
		return response;
	}

	private void mapSearchTag(SearchMerchantByTagRequest request, MerchantDto dto, MerchantDetails merchant) {
		List<Tag> tags = null;
		if (request.getType() == TagType.CUISINE) {
			tags = dto.getRatedCuisines();
		} else if (request.getType() == TagType.SUGGESTION) {
			tags = dto.getRatedSuggestions();
		}
		if (tags != null) {
			for (Tag tag : tags) {
				if (tag.getNameId().equalsIgnoreCase(request.getNameId())) {
					merchant.setSearchTag(tag);
					break;
				}
			}
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
	public MerchantDetails getMerchantDetails(DetailsRequest request, boolean isSearchByNameId) throws BusinessException {
		MerchantDetails response = new MerchantDetails();
		MerchantFilterCriteria filter = new MerchantFilterCriteria(true, true, true, true, false, true);
		MerchantDto merchantDto = null;
		if (isSearchByNameId) {
			merchantDto = dao.getMerchantDetailsByNameId(request.getMerchantNameId(), filter);
		} else {
			merchantDto = dao.getMerchantDetailsById(request.getId(), filter);
		}
		try {
			mapper.buildMerchantDetailsResponse(merchantDto, response);
		} catch (ParseException e) {
			throw new BusinessException(GenericErrorCodeType.GENERIC_ERROR);
		}
		return response;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
	public SearchMerchantResponse searchActiveMerchant(SearchRequest request) throws BusinessException {
		SearchMerchantResponse response = new SearchMerchantResponse();
		MerchantFilterCriteria filter = new MerchantFilterCriteria(true, true, false, false, false, false);
		List<MerchantDto> merchants = dao.searchActiveMerchant(request.getSearchString(), filter);
		if (merchants != null) {
			mapper.buildSearchMerchantsResponse(merchants, response);
		}
		return response;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
	public List<GlobalSearchItem> searchMerchantsGlobal(GenericSearchRequest request) throws BusinessException {
		MerchantFilterCriteria filter = new MerchantFilterCriteria(true, true, false, false, false, false);
		List<MerchantDto> merchants = dao.searchActiveMerchant(request.getSearchString(), filter);
		return mapper.buildSearchMerchantsGlobalResponse(merchants);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
	public SearchMerchantResponse searchMerchant(SearchRequest request) throws BusinessException {
		SearchMerchantResponse response = new SearchMerchantResponse();
		MerchantFilterCriteria filter = new MerchantFilterCriteria(true, true, false, false, false, false);
		List<MerchantDto> merchants = dao.searchMerchant(request.getSearchString(), filter);
		if (merchants != null) {
			mapper.buildSearchMerchantsResponse(merchants, response);
		}
		return response;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
	public GetTrendingMerchantsResponse getTrendingMerchants(LocationCookieDto cookieDto) throws BusinessException {
		GetTrendingMerchantsResponse response = new GetTrendingMerchantsResponse();
		MerchantFilterCriteria filter = new MerchantFilterCriteria(true, true, false, false, false, false);
		List<TrendingMerchantResultDto> result = dao.getAllSortedMerchants(cookieDto, filter, PAGE, RESULTS_PER_PAGE_TRENDING);
		mapper.map(result, response);
		return response;
	}
}
