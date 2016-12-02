package in.socyal.sc.app.merchant;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.socyal.sc.api.merchant.dto.AddressDto;
import in.socyal.sc.api.merchant.dto.GetMerchantListRequestDto;
import in.socyal.sc.api.merchant.dto.MerchantDto;
import in.socyal.sc.api.merchant.request.GetMerchantListRequest;
import in.socyal.sc.api.merchant.response.GetMerchantListResponse;
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
		buildMerchantListResponse(request, merchants, response);
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
		return address.getAddressLine1() + ", " + address.getCity();
	}

	private Boolean checkIfMerchantIsOpen(Double double1, Double double2) {
		//Write logic for isOpen
		return Boolean.TRUE;
	}
}
