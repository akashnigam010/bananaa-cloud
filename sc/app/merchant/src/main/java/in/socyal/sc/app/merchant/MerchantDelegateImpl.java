package in.socyal.sc.app.merchant;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import in.socyal.sc.api.DetailsRequest;
import in.socyal.sc.api.SearchRequest;
import in.socyal.sc.api.helper.exception.BusinessException;
import in.socyal.sc.api.merchant.dto.MerchantDto;
import in.socyal.sc.api.merchant.dto.MerchantFilterCriteria;
import in.socyal.sc.api.merchant.dto.TimingDto;
import in.socyal.sc.api.merchant.dto.TrendingMerchantResultDto;
import in.socyal.sc.api.merchant.response.GetTrendingMerchantsResponse;
import in.socyal.sc.api.merchant.response.MerchantDetailsResponse;
import in.socyal.sc.api.merchant.response.MerchantResponse;
import in.socyal.sc.api.merchant.response.SearchMerchantResponse;
import in.socyal.sc.api.type.error.GenericErrorCodeType;
import in.socyal.sc.app.merchant.mapper.MerchantDelegateMapper;
import in.socyal.sc.date.type.DateFormatType;
import in.socyal.sc.date.util.DateTimeUtil;
import in.socyal.sc.helper.security.jwt.JwtTokenHelper;
import in.socyal.sc.persistence.MerchantDao;
import in.socyal.sc.persistence.RecommendationDao;

@Service
public class MerchantDelegateImpl implements MerchantDelegate {
	@Autowired
	MerchantDao dao;
	@Autowired
	MerchantDelegateMapper mapper;
	@Autowired
	DateTimeUtil dateTimeUtil;
	@Autowired
	RecommendationDao rcmdnDao;
	@Autowired
	JwtTokenHelper jwtHelper;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
	public MerchantDetailsResponse getMerchantDetails(DetailsRequest request) throws BusinessException {
		MerchantDetailsResponse response = new MerchantDetailsResponse();
		MerchantFilterCriteria filter = new MerchantFilterCriteria(true);
		MerchantDto merchantDto = dao.getMerchantDetailsByNameId(request.getMerchantNameId(), filter);
		Integer recommendations = rcmdnDao.getMerchantRecommendationCount(merchantDto.getId());
		try {
			buildMerchantDetailsResponse(merchantDto, response, recommendations);
		} catch (ParseException e) {
			throw new BusinessException(GenericErrorCodeType.GENERIC_ERROR);
		}
		return response;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
	public SearchMerchantResponse searchActiveMerchant(SearchRequest request) throws BusinessException {
		SearchMerchantResponse response = new SearchMerchantResponse();
		MerchantFilterCriteria filter = new MerchantFilterCriteria(true, true, false, false, false, false);
		List<MerchantDto> merchants = dao.searchActiveMerchant(request.getSearchString(), filter);
		if (merchants != null) {
			buildSearchMerchantsResponse(merchants, response);
		}
		return response;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
	public SearchMerchantResponse searchMerchant(SearchRequest request) throws BusinessException {
		SearchMerchantResponse response = new SearchMerchantResponse();
		MerchantFilterCriteria filter = new MerchantFilterCriteria(true, true, false, false, false, false);
		List<MerchantDto> merchants = dao.searchMerchant(request.getSearchString(), filter);
		if (merchants != null) {
			buildSearchMerchantsResponse(merchants, response);
		}
		return response;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
	public GetTrendingMerchantsResponse getTrendingMerchants() throws BusinessException {
		GetTrendingMerchantsResponse response = new GetTrendingMerchantsResponse();
		List<TrendingMerchantResultDto> result = rcmdnDao.getTrendingMerchants();
		mapper.map(result, response);
		return response;
	}

	private void buildSearchMerchantsResponse(List<MerchantDto> merchants, SearchMerchantResponse response) {
		List<MerchantResponse> merchantResponse = new ArrayList<>();
		for (MerchantDto dto : merchants) {
			MerchantResponse merchant = new MerchantResponse();
			merchant.setId(dto.getId());
			merchant.setNameId(dto.getNameId());
			merchant.setName(dto.getName());
			merchant.setShortAddress(dto.getAddress().getLocality().getShortAddress());
			merchant.setMerchantUrl(dto.getMerchantUrl());
			merchantResponse.add(merchant);
		}
		response.setMerchants(merchantResponse);
	}

//	private Boolean isOpen(List<TimingDto> timings) {
//		Calendar today = Calendar.getInstance();
//		for (TimingDto dto : timings) {
//			if (today.get(Calendar.DAY_OF_WEEK) == dto.getDay().getValue()) {
//				String timeStr = dayUtil.formatDate(today, DateFormatType.DATE_FORMAT_24_HOUR);
//				if (Integer.parseInt(timeStr) >= Integer.parseInt(dto.getOpen())
//						&& Integer.parseInt(timeStr) < Integer.parseInt(dto.getClose())) {
//					return Boolean.TRUE;
//				}
//
//			}
//		}
//		return Boolean.FALSE;
//	}

	private List<String> getOpeningHours(List<TimingDto> timings) throws ParseException {
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

	private String createTimingString(Date open, Date close) {
		String openStr = dateTimeUtil.formatDate(open, DateFormatType.TIME_FORMAT_AM_PM);
		String closeStr = dateTimeUtil.formatDate(close, DateFormatType.TIME_FORMAT_AM_PM);
		return openStr + " to " + closeStr;
	}

	private void buildMerchantDetailsResponse(MerchantDto merchantDto, MerchantDetailsResponse response,
			Integer recommendations) throws ParseException {
		response.setAverageCost(merchantDto.getAverageCost().intValue() + " for 2");
		response.setId(merchantDto.getId());
		response.setNameId(merchantDto.getNameId());
		response.setImageUrl(merchantDto.getImageUrl());
		response.setThumbnail(merchantDto.getThumbnail());
		response.setLongAddress(merchantDto.getAddress().getAddress());
		response.setName(merchantDto.getName());
		response.setOpeningHours(getOpeningHours(merchantDto.getTimings()));
		response.setShortAddress(merchantDto.getAddress().getLocality().getShortAddress());
		response.setType(merchantDto.getTypes());
		if (StringUtils.isNotEmpty(merchantDto.getContact().getPhone1())) {
			response.setPhone(merchantDto.getContact().getPhone1());
		}
		response.setRecommendations(recommendations);
	}
}
