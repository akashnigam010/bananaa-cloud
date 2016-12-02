package in.socyal.sc.app.merchant.mapper;

import org.springframework.stereotype.Component;

import in.socyal.sc.api.merchant.dto.GetMerchantListRequestDto;
import in.socyal.sc.api.merchant.request.GetMerchantListRequest;

@Component
public class MerchantDelegateMapper {
	public void map(GetMerchantListRequest request, GetMerchantListRequestDto requestDto) {
		requestDto.setLatitude(request.getLocation().getLatitude());
		requestDto.setLongitude(request.getLocation().getLongitude());
		requestDto.setPage(request.getPage());
	}
}
