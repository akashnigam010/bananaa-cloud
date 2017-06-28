package in.socyal.sc.app.merchant;

import in.socyal.sc.api.DetailsRequest;
import in.socyal.sc.api.SearchRequest;
import in.socyal.sc.api.helper.exception.BusinessException;
import in.socyal.sc.api.merchant.request.SearchMerchantByTagRequest;
import in.socyal.sc.api.merchant.response.GetTrendingMerchantsResponse;
import in.socyal.sc.api.merchant.response.MerchantDetails;
import in.socyal.sc.api.merchant.response.MerchantListForTagResponse;
import in.socyal.sc.api.merchant.response.SearchMerchantResponse;

public interface MerchantDelegate {
	MerchantDetails getMerchantDetails(DetailsRequest request) throws BusinessException;

	SearchMerchantResponse searchActiveMerchant(SearchRequest request) throws BusinessException;

	SearchMerchantResponse searchMerchant(SearchRequest request) throws BusinessException;

	GetTrendingMerchantsResponse getTrendingMerchants() throws BusinessException;

	MerchantListForTagResponse getMerchantsByTag(SearchMerchantByTagRequest request) throws BusinessException;
}
