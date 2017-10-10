package in.socyal.sc.controller.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import in.socyal.sc.api.DetailsRequest;
import in.socyal.sc.api.helper.ResponseHelper;
import in.socyal.sc.api.helper.exception.BusinessException;
import in.socyal.sc.api.item.response.ItemsResponse;
import in.socyal.sc.api.items.request.TrendingRequest;
import in.socyal.sc.api.merchant.response.MerchantDetails;
import in.socyal.sc.app.merchant.MerchantDelegate;
import in.socyal.sc.app.merchant.mapper.MerchantDelegateMapper;
import in.socyal.sc.app.rcmdn.ItemDelegate;
import in.socyal.sc.app.response.MerchantDetailsResponse;
import in.socyal.sc.controller.MerchantService;
import in.socyal.sc.core.validation.MerchantValidator;
import in.socyal.sc.helper.JsonHelper;

@RestController
@RequestMapping(value = "/bna/merchant")
public class AppMerchantService {
	
	@Autowired
	MerchantDelegate merchantDelegate;
	@Autowired
	ItemDelegate itemDelegate;
	@Autowired
	MerchantDelegateMapper merchantMapper;
	@Autowired
	MerchantValidator validator;
	@Autowired
	ResponseHelper responseHelper;
	
	@RequestMapping(value = "/getDetails", method = RequestMethod.POST, headers = "Accept=application/json")
	public MerchantDetailsResponse getDetails(@RequestBody DetailsRequest request) {
		JsonHelper.logRequest(request, MerchantService.class, "/bna/merchant/getDetails");
		MerchantDetailsResponse response = null;
		try {
			validator.validateGetDetailsByIdRequest(request);
			MerchantDetails details = merchantDelegate.getMerchantDetails(request, false);
			response = merchantMapper.mapToMerchantDetailsResponse(details);
			TrendingRequest itemsRequest = new TrendingRequest(request.getId(), 5, 1);
			ItemsResponse itemsResponse = itemDelegate.getPopularItems(itemsRequest);
			response.setItems(itemsResponse.getItems());
			return responseHelper.success(response);
		} catch (BusinessException e) {
			return responseHelper.failure(response, e);
		}
	}
}
