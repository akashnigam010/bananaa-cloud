package in.socyal.sc.app.merchant;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import in.socyal.sc.api.DetailsRequest;
import in.socyal.sc.api.SearchRequest;
import in.socyal.sc.api.helper.exception.BusinessException;
import in.socyal.sc.api.merchant.dto.AddressDto;
import in.socyal.sc.api.merchant.dto.GetMerchantListRequestDto;
import in.socyal.sc.api.merchant.dto.Location;
import in.socyal.sc.api.merchant.dto.MerchantDto;
import in.socyal.sc.api.merchant.dto.MerchantFilterCriteria;
import in.socyal.sc.api.merchant.dto.TimingDto;
import in.socyal.sc.api.merchant.dto.TrendingMerchantResultDto;
import in.socyal.sc.api.merchant.request.GetMerchantListRequest;
import in.socyal.sc.api.merchant.request.SaveMerchantDetailsRequest;
import in.socyal.sc.api.merchant.response.GetMerchantListResponse;
import in.socyal.sc.api.merchant.response.GetTrendingMerchantsResponse;
import in.socyal.sc.api.merchant.response.MerchantDetailsResponse;
import in.socyal.sc.api.merchant.response.MerchantResponse;
import in.socyal.sc.api.merchant.response.SearchMerchantResponse;
import in.socyal.sc.api.type.MerchantListSortType;
import in.socyal.sc.api.type.error.MerchantErrorCodeType;
import in.socyal.sc.app.merchant.mapper.MerchantDelegateMapper;
import in.socyal.sc.date.type.DateFormatType;
import in.socyal.sc.date.util.Clock;
import in.socyal.sc.date.util.DayUtil;
import in.socyal.sc.helper.distance.DistanceHelper;
import in.socyal.sc.helper.distance.DistanceUnitType;
import in.socyal.sc.helper.security.jwt.JwtTokenHelper;
import in.socyal.sc.persistence.MerchantDao;
import in.socyal.sc.persistence.RecommendationDao;

@Service
public class MerchantDelegateImpl implements MerchantDelegate {
	private static final Logger LOG = Logger.getLogger(MerchantDelegateImpl.class);
	@Autowired
	MerchantDao dao;
	@Autowired
	MerchantDelegateMapper mapper;
	@Autowired
	DayUtil dayUtil;
	@Autowired
	Clock clock;
	@Autowired
	RecommendationDao rcmdnDao;
	@Autowired 
	JwtTokenHelper jwtHelper;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
	public GetMerchantListResponse getMerchants(GetMerchantListRequest request) throws BusinessException {
		GetMerchantListResponse response = new GetMerchantListResponse();
		GetMerchantListRequestDto requestDto = new GetMerchantListRequestDto();
		mapper.map(request, requestDto);
		List<MerchantDto> merchants = null;
		// TODO - FIX ME: Decide on what Default sorting needs to be happened
		MerchantListSortType sortType = MerchantListSortType.DISTANCE;
		MerchantFilterCriteria filter = new MerchantFilterCriteria(true, true, true, true, true, false);
		if (sortType == MerchantListSortType.RATING || sortType == MerchantListSortType.PROMOTION) {
			merchants = dao.getMerchantsByRatingOrPromotion(requestDto, sortType, filter);
		} else {
			merchants = dao.getMerchantsByDistance(requestDto, filter);
		}
		if (merchants == null) {
			throw new BusinessException(MerchantErrorCodeType.MERCHANTS_NOT_FOUND);
		}
		buildMerchantListResponse(request, merchants, response);
		return response;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
	public MerchantDetailsResponse getMerchantDetails(DetailsRequest request) throws BusinessException {
		MerchantDetailsResponse response = new MerchantDetailsResponse();
		MerchantFilterCriteria filter = new MerchantFilterCriteria(true);
		MerchantDto merchantDto = dao.getMerchantDetailsByNameId(request.getNameId(), filter);
		try {
			int previousCheckinCount = 0;
			if (jwtHelper.isUserLoggedIn()) {
				System.out.println(jwtHelper.getUserId());
			}
			buildMerchantDetailsResponse(merchantDto, response, previousCheckinCount);
		} catch (ParseException e) {
			throw new BusinessException(MerchantErrorCodeType.MERCHANT_DETAILS_NOT_FOUND);
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
	public void saveMerchantDetails(SaveMerchantDetailsRequest request) throws BusinessException {
		MerchantDto merchantDto = new MerchantDto();
		mapper.map(request, merchantDto);
		dao.saveMerchantDetails(merchantDto);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
	public GetTrendingMerchantsResponse getTrendingMerchants() throws BusinessException {
		GetTrendingMerchantsResponse response = new GetTrendingMerchantsResponse();
		List<TrendingMerchantResultDto> result = rcmdnDao.getTrendingMerchants();
		mapper.map(result, response);
		return response;
	}

	// @Override
	// @Transactional(propagation = Propagation.REQUIRED, rollbackFor =
	// BusinessException.class)
	// public SaveBusinessRegistrationIdResponse
	// saveBusinessRegistrationId(SaveBusinessRegistrationIdRequest request)
	// throws BusinessException {
	// SaveBusinessRegistrationIdResponse response = new
	// SaveBusinessRegistrationIdResponse();
	// MerchantFilterCriteria filter = new MerchantFilterCriteria(false);
	// Integer currentMerchantId = jwtDetailsHelper.getCurrentMerchantId();
	// Integer currentMerchantDeviceId =
	// jwtDetailsHelper.getCurrentMerchantDeviceId();
	// MerchantDto merchant = dao.getMerchantDetails(currentMerchantId, filter);
	// if (merchant == null) {
	// LOG.error("Exception occured: merchant details not found for merchantId:"
	// + currentMerchantId);
	// throw new BusinessException(MerchantErrorCodeType.MERCHANTS_NOT_FOUND);
	// }
	// if (!merchantLoginDao.isMerchantLoginDetailsPresent(currentMerchantId,
	// currentMerchantDeviceId)) {
	// LOG.error("Exception occured: business device details not found for
	// merchantId:" + currentMerchantId
	// + " and deviceId:" + currentMerchantDeviceId);
	// throw new
	// BusinessException(MerchantLoginErrorCodeType.MERCHANT_DEVICE_DETAILS_FOUND);
	// }
	// merchantLoginDao.saveRegistrationIdForMerchant(currentMerchantId,
	// currentMerchantDeviceId,
	// request.getRegistrationId());
	// return response;
	// }

	private void buildSearchMerchantsResponse(List<MerchantDto> merchants, SearchMerchantResponse response) {
		List<MerchantResponse> merchantResponse = new ArrayList<>();
		for (MerchantDto dto : merchants) {
			MerchantResponse merchant = new MerchantResponse();
			merchant.setId(dto.getId());
			merchant.setNameId(dto.getNameId());
			merchant.setName(dto.getName());
			merchant.setShortAddress(dto.getAddress().getLocality().getShortAddress());
			merchant.setImageUrl(dto.getImageUrl());
			merchant.setMerchantUrl(dto.getMerchantUrl());
			merchantResponse.add(merchant);
		}
		response.setMerchants(merchantResponse);
	}

	private void buildMerchantListResponse(GetMerchantListRequest request, List<MerchantDto> merchants,
			GetMerchantListResponse response) {
		List<MerchantResponse> merchantResponse = new ArrayList<>();
		for (MerchantDto dto : merchants) {
			MerchantResponse merchant = new MerchantResponse();
			merchant.setNameId(dto.getNameId());
			merchant.setId(dto.getId());
			merchant.setImageUrl(dto.getImageUrl());
			merchant.setMerchantUrl(dto.getMerchantUrl());
			merchant.setIsOpen(isOpen(dto.getTimings()));
			merchant.setName(dto.getName());
			merchant.setRating(dto.getRating());
			merchant.setCheckins(dto.getCheckins());
			merchant.setDistance(calculateDistance(request, dto.getAddress()));
			merchant.setShortAddress(dto.getAddress().getLocality().getShortAddress());
			merchantResponse.add(merchant);
		}
		response.setMerchants(merchantResponse);
	}

	private Double calculateDistance(GetMerchantListRequest request, AddressDto address) {
		return DistanceHelper.distance(request.getLocation().getLatitude(), request.getLocation().getLongitude(),
				address.getLatitude(), address.getLongitude(), DistanceUnitType.KM.getCode());
	}

	private String createLongAddress(AddressDto address) {
		StringBuilder longAddress = new StringBuilder();
		longAddress.append(address.getAddress());
		longAddress.append(", ");
		longAddress.append(address.getCity());
		longAddress.append(", ");
		longAddress.append(address.getZip());
		return longAddress.toString();
	}

	private Boolean isOpen(Set<TimingDto> timings) {
		Calendar today = clock.cal();
		for (TimingDto dto : timings) {
			if (today.get(Calendar.DAY_OF_WEEK) == dto.getDay().getValue()) {
				String timeStr = dayUtil.formatDate(today, DateFormatType.DATE_FORMAT_24_HOUR);
				if (Integer.parseInt(timeStr) >= Integer.parseInt(dto.getOpen())
						&& Integer.parseInt(timeStr) < Integer.parseInt(dto.getClose())) {
					return Boolean.TRUE;
				}

			}
		}
		return Boolean.FALSE;
	}

	private List<String> getOpeningHours(Set<TimingDto> timings) throws ParseException {
		Calendar today = clock.cal();
		List<String> openingHours = new ArrayList<>();
		for (TimingDto dto : timings) {
			if (today.get(Calendar.DAY_OF_WEEK) == dto.getDay().getValue()) {
				openingHours.add(createTimingString(
						dayUtil.parseDate(dto.getOpen().toString(), DateFormatType.DATE_FORMAT_24_HOUR),
						dayUtil.parseDate(dto.getClose().toString(), DateFormatType.DATE_FORMAT_24_HOUR)));
			}
		}
		return openingHours;
	}

	private String createTimingString(Date open, Date close) {
		String openStr = dayUtil.formatDate(open, DateFormatType.TIME_FORMAT_AM_PM);
		String closeStr = dayUtil.formatDate(close, DateFormatType.TIME_FORMAT_AM_PM);
		return openStr + " to " + closeStr;
	}

	private void buildMerchantDetailsResponse(MerchantDto merchantDto, MerchantDetailsResponse response,
			int previousCheckinCount) throws ParseException {
		response.setAverageCost(merchantDto.getAverageCost().intValue() + " for 2");
		response.setCheckins(merchantDto.getCheckins());
		response.setCuisines(merchantDto.getCuisines());
		// For calculating distance we need user's current place latitude and
		// longitude
		response.setDistance(null);
		response.setId(merchantDto.getId());
		response.setNameId(merchantDto.getNameId());
		response.setImageUrl(merchantDto.getImageUrl());
		response.setIsOpen(isOpen(merchantDto.getTimings()));
		response.setLocation(buildLocationResponse(merchantDto.getAddress()));
		response.setLongAddress(createLongAddress(merchantDto.getAddress()));
		response.setName(merchantDto.getName());
		response.setOpeningHours(getOpeningHours(merchantDto.getTimings()));
		response.setRating(merchantDto.getRating());
		response.setShortAddress(merchantDto.getAddress().getLocality().getShortAddress());
		response.setType(merchantDto.getTypes());
		response.setPreviousCheckinCount(previousCheckinCount);
		if (StringUtils.isNotEmpty(merchantDto.getContact().getPhone1())) {
			response.setPhone(merchantDto.getContact().getPhone1());
		}
	}

	private Location buildLocationResponse(AddressDto address) {
		Location locationResponse = new Location();
		locationResponse.setLatitude(address.getLatitude());
		locationResponse.setLongitude(address.getLongitude());
		return locationResponse;
	}
}
