package in.socyal.sc.persistence.mapper;

import org.springframework.stereotype.Component;

import in.socyal.sc.api.checkin.dto.CheckinDetailsDto;
import in.socyal.sc.persistence.entity.CheckinEntity;
import in.socyal.sc.persistence.entity.MerchantEntity;

@Component
public class CheckinDaoMapper {
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
}
