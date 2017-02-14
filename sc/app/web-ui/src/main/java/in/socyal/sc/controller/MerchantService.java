package in.socyal.sc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import in.socyal.sc.api.helper.ResponseHelper;
import in.socyal.sc.api.helper.exception.BusinessException;
import in.socyal.sc.api.merchant.business.request.SaveBusinessRegistrationIdRequest;
import in.socyal.sc.api.merchant.business.response.SaveBusinessRegistrationIdResponse;
import in.socyal.sc.api.merchant.request.GetMerchantListRequest;
import in.socyal.sc.api.merchant.request.MerchantDetailsRequest;
import in.socyal.sc.api.merchant.request.SaveMerchantDetailsRequest;
import in.socyal.sc.api.merchant.request.SearchMerchantRequest;
import in.socyal.sc.api.merchant.response.GetMerchantListResponse;
import in.socyal.sc.api.merchant.response.MerchantDetailsResponse;
import in.socyal.sc.api.merchant.response.SaveMerchantDetailsResponse;
import in.socyal.sc.api.merchant.response.SearchMerchantResponse;
import in.socyal.sc.app.merchant.MerchantDelegate;
import in.socyal.sc.core.validation.MerchantValidator;
import in.socyal.sc.helper.JsonHelper;

@RestController
@RequestMapping(value = "/socyal/merchant")
public class MerchantService {
	public static final Integer MINIMUM_SEARCH_STRING_LENGTH = 2;

	@Autowired
	MerchantDelegate delegate;
	@Autowired
	ResponseHelper responseHelper;
	@Autowired
	MerchantValidator validator;

	@RequestMapping(value = "/getMerchants", method = RequestMethod.POST, headers = "Accept=application/json")
	public GetMerchantListResponse getMerchants(@RequestBody GetMerchantListRequest request) {
		JsonHelper.logRequest(request, MerchantService.class, "/merchant/getMerchants");
		GetMerchantListResponse response = new GetMerchantListResponse();
		try {
			validator.validateGetMerchantRequest(request);
			response = delegate.getMerchants(request);
			return responseHelper.success(response);
		} catch (BusinessException e) {
			return responseHelper.failure(response, e);
		}

	}

	@RequestMapping(value = "/getMerchantDetails", method = RequestMethod.POST, headers = "Accept=application/json")
	public MerchantDetailsResponse getMerchantDetails(@RequestBody MerchantDetailsRequest request) {
		JsonHelper.logRequest(request, MerchantService.class, "/merchant/getMerchantDetails");
		MerchantDetailsResponse response = new MerchantDetailsResponse();
		try {
			validator.validateGetMerchantDetailsRequest(request);
			response = delegate.getMerchantDetails(request);
			return responseHelper.success(response);
		} catch (BusinessException e) {
			return responseHelper.failure(response, e);
		}
	}

	@RequestMapping(value = "/searchMerchant", method = RequestMethod.POST, headers = "Accept=application/json")
	public SearchMerchantResponse searchMerchant(@RequestBody SearchMerchantRequest request) {
		JsonHelper.logRequest(request, MerchantService.class, "/merchant/searchMerchant");
		SearchMerchantResponse response = new SearchMerchantResponse();
		try {
			if (request.getSearchString().length() >= MINIMUM_SEARCH_STRING_LENGTH) {
				response = delegate.searchMerchant(request);
			}			
			return responseHelper.success(response);
		} catch (BusinessException e) {
			return responseHelper.failure(response, e);
		}
	}

	// Testing purpose
	@RequestMapping(value = "/saveMerchantDetails", method = RequestMethod.POST, headers = "Accept=application/json")
	public SaveMerchantDetailsResponse saveMerchantDetails(@RequestBody SaveMerchantDetailsRequest request) {
		JsonHelper.logRequest(request, MerchantService.class, "/merchant/saveMerchantDetails");
		SaveMerchantDetailsResponse response = new SaveMerchantDetailsResponse();
		try {
			delegate.saveMerchantDetails(request);
			return responseHelper.success(response);
		} catch (BusinessException e) {
			return responseHelper.failure(response, e);
		}
	}
	
	/**
	 * This method is used to save REGISTRATION_ID for a respective merchant logged in device
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/saveBusinessRegistrationId", method = RequestMethod.POST, headers = "Accept=application/json")
	public SaveBusinessRegistrationIdResponse saveBusinessRegistrationId(@RequestBody SaveBusinessRegistrationIdRequest request) {
		JsonHelper.logRequest(request, MerchantService.class, "/merchant/saveBusinessRegistrationId");
		SaveBusinessRegistrationIdResponse response = new SaveBusinessRegistrationIdResponse();
		try {
			validator.validateSaveBusinessRegistrationIdRequest(request);
			response = delegate.saveBusinessRegistrationId(request);
			return responseHelper.success(response);
		} catch (BusinessException e) {
			return responseHelper.failure(response, e);
		}
	}
	
}
