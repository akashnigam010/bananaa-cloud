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
import in.socyal.sc.api.item.response.Tag;
import in.socyal.sc.api.merchant.dto.MerchantDto;
import in.socyal.sc.api.merchant.dto.MerchantFilterCriteria;
import in.socyal.sc.api.merchant.dto.TimingDto;
import in.socyal.sc.api.merchant.dto.TrendingMerchantResultDto;
import in.socyal.sc.api.merchant.request.SearchMerchantByTagRequest;
import in.socyal.sc.api.merchant.response.GetTrendingMerchantsResponse;
import in.socyal.sc.api.merchant.response.GlobalSearchItem;
import in.socyal.sc.api.merchant.response.MerchantDetails;
import in.socyal.sc.api.merchant.response.MerchantListForTagResponse;
import in.socyal.sc.api.merchant.response.MerchantShortDetails;
import in.socyal.sc.api.merchant.response.SearchMerchantResponse;
import in.socyal.sc.api.type.SearchType;
import in.socyal.sc.api.type.TagType;
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
	public MerchantListForTagResponse getMerchantsByTag(SearchMerchantByTagRequest request) throws BusinessException {
		MerchantListForTagResponse response = new MerchantListForTagResponse();
		response.setTotalPages(dao.getMerchantSearchByTagPages(request));
		if (response.getTotalPages() >= 1) {
			List<MerchantDto> dtos = dao.getMerchantsByTag(request);
			MerchantDetails merchant = null;
			for (MerchantDto dto : dtos) {
				merchant = new MerchantDetails();
				try {
					buildMerchantTagResponse(dto, merchant);
					mapSearchTag(request, dto, merchant);
				} catch (ParseException e) {
					throw new BusinessException(GenericErrorCodeType.GENERIC_ERROR);
				}
				response.getMerchants().add(merchant);
			}
			response.setPage(request.getPage());
		}
		return response;
	}
	
	private void mapSearchTag(SearchMerchantByTagRequest request, MerchantDto dto, MerchantDetails merchant) {
		List<Tag> tags = null;
		if (request.getType() == TagType.CUISINE) {
			tags = dto.getRatedCuisines();
		} else if (request.getType() == TagType.SUGGESTION) {
			tags = dto.getRatedSuggestions();
		}
		if (tags != null) {
			for (Tag tag : tags) {
				if (tag.getNameId().equalsIgnoreCase(request.getNameId())) {
					merchant.setSearchTag(tag);
					break;
				}
			}
		}
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
	public MerchantDetails getMerchantDetails(DetailsRequest request) throws BusinessException {
		MerchantDetails response = new MerchantDetails();
		MerchantFilterCriteria filter = new MerchantFilterCriteria(true, true, true, true, false, true);
		MerchantDto merchantDto = dao.getMerchantDetailsByNameId(request.getMerchantNameId(), filter);
		try {
			buildMerchantDetailsResponse(merchantDto, response);
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
	public List<GlobalSearchItem> searchMerchantsGlobal(SearchRequest request) throws BusinessException {
		MerchantFilterCriteria filter = new MerchantFilterCriteria(true, true, false, false, false, false);
		List<MerchantDto> merchants = dao.searchActiveMerchant(request.getSearchString(), filter);
		return buildSearchMerchantsGlobalResponse(merchants);
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
	
	private List<GlobalSearchItem> buildSearchMerchantsGlobalResponse(List<MerchantDto> merchants) {
		List<GlobalSearchItem> items = new ArrayList<>();
		GlobalSearchItem item = null;
		for (MerchantDto dto : merchants) {
			item = new GlobalSearchItem(SearchType.RESTAURANT);
			item.setName(dto.getName());
			item.setNameId(dto.getNameId());
			item.setShortAddress(dto.getAddress().getLocality().getShortAddress());
			item.setMerchantUrl(dto.getMerchantUrl());
			items.add(item);
		}
		return items;
	}
	
	private void buildSearchMerchantsResponse(List<MerchantDto> merchants, SearchMerchantResponse response) {
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

	private void buildMerchantDetailsResponse(MerchantDto merchantDto, MerchantDetails response) throws ParseException {
		buildMerchantTagResponse(merchantDto, response);
		response.setId(merchantDto.getId());
		response.setImageUrl(merchantDto.getImageUrl());
		response.setLongAddress(merchantDto.getAddress().getAddress());
		if (StringUtils.isNotEmpty(merchantDto.getContact().getPhone1())) {
			response.setPhone(merchantDto.getContact().getPhone1());
		}
	}
	
	private void buildMerchantTagResponse(MerchantDto merchantDto, MerchantDetails response) throws ParseException {
		response.setAverageCost(merchantDto.getAverageCost().intValue()+"");
		response.setNameId(merchantDto.getNameId());
		response.setThumbnail(merchantDto.getThumbnail());
		response.setName(merchantDto.getName());
		response.setOpeningHours(getOpeningHours(merchantDto.getTimings()));
		response.setShortAddress(merchantDto.getAddress().getLocality().getShortAddress());
		response.setType(merchantDto.getTypes());
		response.setRatedCuisines(merchantDto.getRatedCuisines());
		response.setMerchantUrl(merchantDto.getMerchantUrl());
	}
}
