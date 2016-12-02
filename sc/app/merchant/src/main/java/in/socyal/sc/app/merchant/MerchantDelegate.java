package in.socyal.sc.app.merchant;

import in.socyal.sc.api.merchant.request.GetMerchantListRequest;
import in.socyal.sc.api.merchant.response.GetMerchantListResponse;
import in.socyal.sc.helper.exception.BusinessException;

public interface MerchantDelegate {
	public GetMerchantListResponse getMerchants(GetMerchantListRequest request) throws BusinessException;
	public void saveMerchantSample();
}
