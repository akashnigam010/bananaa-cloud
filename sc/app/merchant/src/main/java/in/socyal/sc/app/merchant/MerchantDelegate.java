package in.socyal.sc.app.merchant;

import java.text.ParseException;

import in.socyal.sc.api.merchant.request.GetMerchantListRequest;
import in.socyal.sc.api.merchant.request.MerchantDetailsRequest;
import in.socyal.sc.api.merchant.request.SaveMerchantDetailsRequest;
import in.socyal.sc.api.merchant.request.SearchMerchantRequest;
import in.socyal.sc.api.merchant.response.GetMerchantListResponse;
import in.socyal.sc.api.merchant.response.MerchantDetailsResponse;
import in.socyal.sc.api.merchant.response.SearchMerchantResponse;
import in.socyal.sc.helper.exception.BusinessException;

public interface MerchantDelegate {
	public GetMerchantListResponse getMerchants(GetMerchantListRequest request) throws BusinessException;
	public MerchantDetailsResponse getMerchantDetails(MerchantDetailsRequest request) throws BusinessException, ParseException;
	public void saveMerchantDetails(SaveMerchantDetailsRequest request) throws BusinessException;
	public SearchMerchantResponse searchMerchant(SearchMerchantRequest request) throws BusinessException;
}
