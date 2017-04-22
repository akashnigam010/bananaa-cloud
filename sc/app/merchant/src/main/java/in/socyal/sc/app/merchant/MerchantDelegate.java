package in.socyal.sc.app.merchant;

import in.socyal.sc.api.DetailsRequest;
import in.socyal.sc.api.SearchRequest;
import in.socyal.sc.api.helper.exception.BusinessException;
import in.socyal.sc.api.merchant.request.GetMerchantListRequest;
import in.socyal.sc.api.merchant.request.SaveMerchantDetailsRequest;
import in.socyal.sc.api.merchant.response.GetMerchantListResponse;
import in.socyal.sc.api.merchant.response.GetTrendingMerchantsResponse;
import in.socyal.sc.api.merchant.response.MerchantDetailsResponse;
import in.socyal.sc.api.merchant.response.SearchMerchantResponse;

public interface MerchantDelegate {
	public GetMerchantListResponse getMerchants(GetMerchantListRequest request) throws BusinessException;
	public MerchantDetailsResponse getMerchantDetails(DetailsRequest request) throws BusinessException;
	public void saveMerchantDetails(SaveMerchantDetailsRequest request) throws BusinessException;
	public SearchMerchantResponse searchMerchant(SearchRequest request) throws BusinessException;
	public GetTrendingMerchantsResponse getTrendingMerchants() throws BusinessException;
}
