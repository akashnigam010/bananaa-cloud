package in.socyal.sc.app.merchant;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.socyal.sc.api.merchant.dto.AddressDto;
import in.socyal.sc.api.merchant.dto.GetMerchantListRequestDto;
import in.socyal.sc.api.merchant.dto.MerchantDto;
import in.socyal.sc.api.merchant.request.GetMerchantListRequest;
import in.socyal.sc.api.merchant.request.MerchantDetailsRequest;
import in.socyal.sc.api.merchant.response.GetMerchantListResponse;
import in.socyal.sc.api.merchant.response.LocationResponse;
import in.socyal.sc.api.merchant.response.MerchantDetailsResponse;
import in.socyal.sc.api.merchant.response.MerchantResponse;
import in.socyal.sc.app.merchant.mapper.MerchantDelegateMapper;
import in.socyal.sc.helper.distance.DistanceHelper;
import in.socyal.sc.helper.distance.DistanceUnitType;
import in.socyal.sc.helper.exception.BusinessException;
import in.socyal.sc.persistence.MerchantDao;

@Service
public class MerchantDelegateImpl implements MerchantDelegate {
	@Autowired MerchantDao dao;
	@Autowired MerchantDelegateMapper mapper;

	@Override
	public GetMerchantListResponse getMerchants(GetMerchantListRequest request) throws BusinessException {
		GetMerchantListResponse response = new GetMerchantListResponse();
		GetMerchantListRequestDto requestDto = new GetMerchantListRequestDto();
		mapper.map(request, requestDto);
		List<MerchantDto> merchants = dao.getMerchants(requestDto);
		if (merchants == null) {
			throw new BusinessException(MerchantErrorCodeType.MERCHANTS_NOT_FOUND);
		}
		buildMerchantListResponse(request, merchants, response);
		return response;
	}
	
	@Override
	public MerchantDetailsResponse getMerchantDetails(MerchantDetailsRequest request) throws BusinessException {
		MerchantDetailsResponse response = new MerchantDetailsResponse();
		MerchantDto merchantDto = dao.getMerchantDetails(request.getId());
		if (merchantDto == null) {
			throw new BusinessException(MerchantErrorCodeType.MERCHANT_DETAILS_NOT_FOUND);
		}
		buildMerchantDetailsResponse(merchantDto, response);
		return response;
	}
	
	@Override
	public void saveMerchantSample() {
		dao.saveMerchantDetails();
	}

	private void buildMerchantListResponse(GetMerchantListRequest request, List<MerchantDto> merchants, GetMerchantListResponse response) {
		List<MerchantResponse> merchantResponse = new ArrayList<>();
		for (MerchantDto dto : merchants) {
			MerchantResponse merchant = new MerchantResponse();
			merchant.setId(dto.getId());
			merchant.setImageUrl(dto.getImageUrl());
			merchant.setIsOpen(checkIfMerchantIsOpen(dto.getOpenTime(), dto.getCloseTime()));
			merchant.setName(dto.getName());
			merchant.setRating(dto.getRating());
			merchant.setCheckins(dto.getCheckins());
			merchant.setDistance(calculateDistance(request, dto.getAddress()));
			merchant.setShortAddress(createShortAddress(dto.getAddress()));
			merchantResponse.add(merchant);
		}
		response.setMerchants(merchantResponse);
	}
	
	private Double calculateDistance(GetMerchantListRequest request, AddressDto address) {
		return DistanceHelper.distance(request.getLocation().getLatitude(), 
								request.getLocation().getLongitude(), 
								address.getLatitude(), 
								address.getLongitude(), 
								DistanceUnitType.KM.getCode());
	}

	private String createShortAddress(AddressDto address) {
		StringBuilder shortAddress = new StringBuilder();
		shortAddress.append(address.getAddressLine1());
		shortAddress.append(", ");
		shortAddress.append(address.getCity());
		return shortAddress.toString();
	}
	
	private String createLongAddress(AddressDto address) {
		StringBuilder longAddress = new StringBuilder();
		longAddress.append(address.getAddressLine1());
		longAddress.append(", ");
		longAddress.append(address.getAddressLine2());
		longAddress.append(", ");
		longAddress.append(address.getCity());
		return longAddress.toString();
	}

	private Boolean checkIfMerchantIsOpen(Double openTime, Double closeTime) {
		//Write logic for isOpen
		return Boolean.TRUE;
	}
	
	private void buildMerchantDetailsResponse(MerchantDto merchantDto, MerchantDetailsResponse response) {
		response.setAverageCost(1300.00);
		response.setCheckins(merchantDto.getCheckins());
		response.setCuisines(parseCuisineStringToList(merchantDto.getCuisines()));
		//For calculating distance we need user's current place latitude and longitude
		response.setDistance(null);
		response.setId(merchantDto.getId());
		response.setImageUrl(merchantDto.getImageUrl());
		response.setIsOpen(checkIfMerchantIsOpen(merchantDto.getOpenTime(), merchantDto.getCloseTime()));
		response.setLocation(buildLocationResponse(merchantDto.getAddress()));
		response.setLongAddress(createLongAddress(merchantDto.getAddress()));
		response.setName(merchantDto.getName());
		response.setOpenTime(merchantDto.getOpenTime());
		response.setRating(merchantDto.getRating());
		response.setShortAddress(createShortAddress(merchantDto.getAddress()));
		//Need to confirm on what is restaurant type
		response.setType(null);
	}

	/**
	 * This method parses a comma separated string
	 *  splits the string on a delimiter defined as: 
	 *  zero or more whitespace, a literal comma, zero or more whitespace 
	 *  which will place the words into the list and collapse any 
	 *  whitespace between the words and commas 
	 * @param cuisines
	 * @return List of cuisines
	 */
	private List<String> parseCuisineStringToList(String cuisines) {
		List<String> list = new ArrayList<>();
		if (cuisines != null && !cuisines.isEmpty()) {
			list = Arrays.asList(cuisines.split("\\s*,\\s*"));
		}
		return list;
	}
	
	private LocationResponse buildLocationResponse(AddressDto address) {
		LocationResponse locationResponse = new LocationResponse();
		locationResponse.setLatitude(address.getLatitude());
		locationResponse.setLongitude(address.getLongitude());
		return locationResponse;
	}
}
