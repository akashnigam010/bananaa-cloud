package in.socyal.sc.app.merchant.mapper;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import in.socyal.sc.api.merchant.dto.GetMerchantListRequestDto;
import in.socyal.sc.api.merchant.dto.MerchantDto;
import in.socyal.sc.api.merchant.dto.TimingDto;
import in.socyal.sc.api.merchant.dto.TrendingMerchantResultDto;
import in.socyal.sc.api.merchant.request.GetMerchantListRequest;
import in.socyal.sc.api.merchant.response.GetTrendingMerchantsResponse;
import in.socyal.sc.api.merchant.response.GlobalSearchItem;
import in.socyal.sc.api.merchant.response.MerchantDetails;
import in.socyal.sc.api.merchant.response.MerchantShortDetails;
import in.socyal.sc.api.merchant.response.SearchMerchantResponse;
import in.socyal.sc.api.merchant.response.TrendingMerchant;
import in.socyal.sc.api.type.SearchType;
import in.socyal.sc.app.response.MerchantDetailsResponse;
import in.socyal.sc.date.type.DateFormatType;
import in.socyal.sc.date.util.DateTimeUtil;

@Component
public class MerchantDelegateMapper {
	@Autowired
	DateTimeUtil dateTimeUtil;

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
			trendingMerchant.setRating(dto.getRating() != null ? dto.getRating().toString() : "");
			trendingMerchant.setThumbnail(merchant.getThumbnail());
			trendingMerchant.setName(merchant.getName());
			trendingMerchant.setNameId(merchant.getNameId());
			trendingMerchant.setShortAddress(merchant.getAddress().getLocality().getShortAddress());
			trendingMerchant.setMerchantUrl(merchant.getMerchantUrl());
			merchants.add(trendingMerchant);
		}

		response.setMerchants(merchants);
	}

	// private Boolean isOpen(List<TimingDto> timings) {
	// Calendar today = Calendar.getInstance();
	// for (TimingDto dto : timings) {
	// if (today.get(Calendar.DAY_OF_WEEK) == dto.getDay().getValue()) {
	// String timeStr = dayUtil.formatDate(today,
	// DateFormatType.DATE_FORMAT_24_HOUR);
	// if (Integer.parseInt(timeStr) >= Integer.parseInt(dto.getOpen())
	// && Integer.parseInt(timeStr) < Integer.parseInt(dto.getClose())) {
	// return Boolean.TRUE;
	// }
	//
	// }
	// }
	// return Boolean.FALSE;
	// }

	public List<String> getOpeningHours(List<TimingDto> timings) throws ParseException {
		Calendar today = Calendar.getInstance();
		List<String> openingHours = new ArrayList<>();
		for (TimingDto dto : timings) {
			if (today.get(Calendar.DAY_OF_WEEK) == dto.getDay().getValue()) {
				openingHours.add(createTimingString(
						dateTimeUtil.parseDate(dto.getOpen().toString(), DateFormatType.DATE_FORMAT_24_HOUR),
						dateTimeUtil.parseDate(dto.getClose().toString(), DateFormatType.DATE_FORMAT_24_HOUR)));
			}
		}
		return openingHours;
	}

	public String createTimingString(Date open, Date close) {
		String openStr = dateTimeUtil.formatDate(open, DateFormatType.TIME_FORMAT_AM_PM);
		String closeStr = dateTimeUtil.formatDate(close, DateFormatType.TIME_FORMAT_AM_PM);
		return openStr + " to " + closeStr;
	}

	public List<GlobalSearchItem> buildSearchMerchantsGlobalResponse(List<MerchantDto> merchants) {
		List<GlobalSearchItem> items = new ArrayList<>();
		GlobalSearchItem item = null;
		for (MerchantDto dto : merchants) {
			item = new GlobalSearchItem(SearchType.RESTAURANT);
			item.setId(dto.getId());
			item.setName(dto.getName());
			item.setNameId(dto.getNameId());
			item.setShortAddress(dto.getAddress().getLocality().getShortAddress());
			item.setMerchantUrl(dto.getMerchantUrl());
			items.add(item);
		}
		return items;
	}

	public void buildSearchMerchantsResponse(List<MerchantDto> merchants, SearchMerchantResponse response) {
		List<MerchantShortDetails> merchantResponse = new ArrayList<>();
		for (MerchantDto dto : merchants) {
			MerchantShortDetails merchant = new MerchantShortDetails();
			merchant.setId(dto.getId());
			merchant.setNameId(dto.getNameId());
			merchant.setName(dto.getName());
			merchant.setShortAddress(dto.getAddress().getLocality().getShortAddress());
			merchant.setMerchantUrl(dto.getMerchantUrl());
			merchantResponse.add(merchant);
		}
		response.setMerchants(merchantResponse);
	}

	public void buildMerchantDetailsResponse(MerchantDto merchantDto, MerchantDetails response) throws ParseException {
		buildMerchantTagResponse(merchantDto, response);
		response.setId(merchantDto.getId());
		response.setImageUrl(merchantDto.getImageUrl());
		response.setLongAddress(merchantDto.getAddress().getAddress());
		if (StringUtils.isNotEmpty(merchantDto.getContact().getPhone1())) {
			response.setPhone(merchantDto.getContact().getPhone1());
		}
	}

	public void buildMerchantTagResponse(MerchantDto merchantDto, MerchantDetails response) throws ParseException {
		response.setId(merchantDto.getId());
		response.setAverageCost(merchantDto.getAverageCost().intValue() + "");
		response.setNameId(merchantDto.getNameId());
		response.setThumbnail(merchantDto.getThumbnail());
		response.setName(merchantDto.getName());
		response.setOpeningHours(getOpeningHours(merchantDto.getTimings()));
		response.setShortAddress(merchantDto.getAddress().getLocality().getShortAddress());
		response.setType(merchantDto.getTypes());
		
		response.setRatedCuisines(merchantDto.getRatedCuisines());
		if (response.getRatedCuisines().size() > 3) {
			response.setRatedCuisines(response.getRatedCuisines().subList(0, 3));
		}
		if (merchantDto.getRatedSuggestions().size() > 3) {
			response.getRatedCuisines().addAll(merchantDto.getRatedSuggestions().subList(0, 3));
		} else {
			response.getRatedCuisines().addAll(merchantDto.getRatedSuggestions());
		}
		response.setMerchantUrl(merchantDto.getMerchantUrl());
	}
	
	public MerchantDetailsResponse mapToMerchantDetailsResponse(MerchantDetails details) {
		MerchantDetailsResponse response = new MerchantDetailsResponse();
		response.setId(details.getId());
		response.setName(details.getName());
		response.setShortAddress(details.getShortAddress());
		response.setLongAddress(details.getLongAddress());
		response.setImageUrl(details.getImageUrl());
		response.setPhone(details.getPhone());
		response.setOpeningHours(details.getOpeningHours());
		response.setType(details.getType());
		response.setAverageCost(details.getAverageCost());
		response.setRatedCuisines(details.getRatedCuisines());
		return response;
	}
}
