package in.socyal.sc.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import in.socyal.sc.api.merchant.request.GetMerchantListRequest;
import in.socyal.sc.api.merchant.request.MerchantDetailsRequest;
import in.socyal.sc.api.merchant.request.SearchMerchantRequest;
import in.socyal.sc.api.merchant.response.MerchantDetailsResponse;
import in.socyal.sc.api.merchant.response.MerchantListResponse;
import in.socyal.sc.api.merchant.response.SearchMerchantResponse;
import in.socyal.sc.app.merchant.MerchantDelegate;
import in.socyal.sc.core.mapper.MerchantServiceMapper;
import in.socyal.sc.helper.ResponseHelper;
import in.socyal.sc.helper.exception.BusinessException;

@RestController
@RequestMapping(value = "/merchant")
public class MerchantService {
	@Autowired MerchantDelegate delegate;
	@Autowired MerchantServiceMapper mapper;
	@Autowired ResponseHelper responseHelper;
	
	@RequestMapping(value = "/getMerchants", method = RequestMethod.POST, headers = "Accept=application/json")
	public MerchantListResponse getMerchants(@RequestBody GetMerchantListRequest request) {
		MerchantListResponse response = new MerchantListResponse();
		try {
			delegate.getMerchants();
			response = mapper.mapMerchantList();
			return responseHelper.success(response);
		} catch (BusinessException e) {
			return responseHelper.failure(response, e);
		}
	}
	
	@RequestMapping(value = "/getMerchantDetails", method = RequestMethod.POST, headers = "Accept=application/json")
	public MerchantDetailsResponse getMerchantDetails(@RequestBody MerchantDetailsRequest request) {
		MerchantDetailsResponse response = mapper.mapMerchantDetails();
		return responseHelper.success(response);
	}
	
	@RequestMapping(value = "/searchMerchant", method = RequestMethod.POST, headers = "Accept=application/json")
	public SearchMerchantResponse searchMerchant(@RequestBody SearchMerchantRequest request) {
		SearchMerchantResponse response = mapper.mapSearchMerchantResponse();
		return responseHelper.success(response);
	}
	
	@RequestMapping(value = "/test", method = RequestMethod.GET, headers = "Accept=application/json")
	public MerchantListResponse test() {
		MerchantListResponse response = new MerchantListResponse();
		return response;
	}
	
	//Testing purpose
	@RequestMapping(value = "/saveMerchantSample", method = RequestMethod.GET, headers = "Accept=application/json")
	public MerchantListResponse saveMerchantSample() {
		MerchantListResponse response = new MerchantListResponse();
		delegate.saveMerchantSample();
		return response;
	}
}
