package in.socyal.sc.app.merchant.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import in.socyal.sc.api.merchant.dto.GetMerchantListRequestDto;
import in.socyal.sc.api.merchant.dto.MerchantDto;
import in.socyal.sc.api.merchant.dto.TrendingMerchantResultDto;
import in.socyal.sc.api.merchant.request.GetMerchantListRequest;
import in.socyal.sc.api.merchant.response.GetTrendingMerchantsResponse;
import in.socyal.sc.api.merchant.response.TrendingMerchant;

@Component
public class MerchantDelegateMapper {
	public void map(GetMerchantListRequest request, GetMerchantListRequestDto requestDto) {
		requestDto.setLatitude(request.getLocation().getLatitude());
		requestDto.setLongitude(request.getLocation().getLongitude());
		requestDto.setPage(request.getPage());
	}

	public void map(List<TrendingMerchantResultDto> result, GetTrendingMerchantsResponse response) {
		List<TrendingMerchant> merchants = new ArrayList<>();
		for (TrendingMerchantResultDto dto : result) {
			TrendingMerchant trendingMerchant = new TrendingMerchant();
			MerchantDto merchant = dto.getMerchant();
			trendingMerchant.setId(merchant.getId());
			trendingMerchant.setThumbnail(merchant.getThumbnail());
			trendingMerchant.setName(merchant.getName());
			trendingMerchant.setNameId(merchant.getNameId());
			trendingMerchant.setRecommendations(dto.getRecommendations());
			trendingMerchant.setShortAddress(merchant.getAddress().getLocality().getShortAddress());
			trendingMerchant.setMerchantUrl(merchant.getMerchantUrl());
			merchants.add(trendingMerchant);
		}
		
		response.setMerchants(merchants);
	}
}
