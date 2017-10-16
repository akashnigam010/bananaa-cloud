package in.socyal.sc.app.merchant;

import java.util.List;

import in.socyal.sc.api.DetailsRequest;
import in.socyal.sc.api.GenericSearchRequest;
import in.socyal.sc.api.cache.dto.LocationCookieDto;
import in.socyal.sc.api.helper.exception.BusinessException;
import in.socyal.sc.api.merchant.request.SearchMerchantByTagRequest;
import in.socyal.sc.api.merchant.request.SearchRequest;
import in.socyal.sc.api.merchant.response.GetTrendingMerchantsResponse;
import in.socyal.sc.api.merchant.response.GlobalSearchItem;
import in.socyal.sc.api.merchant.response.MerchantDetails;
import in.socyal.sc.api.merchant.response.MerchantListForTagResponse;
import in.socyal.sc.api.merchant.response.SearchMerchantResponse;

public interface MerchantDelegate {
	MerchantDetails getMerchantDetails(DetailsRequest request, boolean isSearchByNameId) throws BusinessException;

	SearchMerchantResponse searchActiveMerchant(SearchRequest request) throws BusinessException;

	SearchMerchantResponse searchMerchant(SearchRequest request) throws BusinessException;

	GetTrendingMerchantsResponse getTrendingMerchants(LocationCookieDto cookieDto) throws BusinessException;

	MerchantListForTagResponse getMerchantsByTag(SearchMerchantByTagRequest request) throws BusinessException;
	
	MerchantListForTagResponse getAllSortedMerchants(LocationCookieDto cookieDto, Integer page) throws BusinessException;

	List<GlobalSearchItem> searchMerchantsGlobal(GenericSearchRequest request) throws BusinessException;
}
