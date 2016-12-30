package in.socyal.sc.persistence.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import in.socyal.sc.api.checkin.dto.CheckinDetailsDto;
import in.socyal.sc.api.checkin.dto.CheckinDto;
import in.socyal.sc.api.merchant.dto.MerchantDto;
import in.socyal.sc.persistence.entity.CheckinEntity;
import in.socyal.sc.persistence.entity.MerchantEntity;

@Component
public class CheckinDaoMapper {
	@Autowired MerchantDaoMapper mapper;
	
	public void map(CheckinDetailsDto from, CheckinEntity to) {
		to.setCheckinDateTime(from.getCheckinDateTime());
		MerchantEntity merchant = new MerchantEntity();
		merchant.setId(from.getMerchantId());
		to.setMerchant(merchant);
		to.setPreviousCheckinCount(from.getPreviousCheckinCount());
		to.setQrCode(from.getQrCode());
		to.setStatus(from.getStatus());
		to.setUpdatedDateTime(from.getUpdatedDateTime());
		to.setUserId(from.getUserId());
	}
	
	public void map(CheckinEntity from, CheckinDto to) {
		to.setCheckinDateTime(from.getCheckinDateTime());
		MerchantDto merchant = new MerchantDto();
		mapper.map(from.getMerchant(), merchant);
		to.setMerchant(merchant);
		to.setPreviousCheckinCount(from.getPreviousCheckinCount());
		to.setQrCode(from.getQrCode());
		to.setStatus(from.getStatus());
		to.setUpdatedDateTime(from.getUpdatedDateTime());
		to.setUserId(from.getUserId());
	}
}
