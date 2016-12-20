package in.socyal.sc.persistence.mapper;

import org.springframework.stereotype.Component;

import in.socyal.sc.api.checkin.dto.CheckinDetailsDto;
import in.socyal.sc.persistence.entity.CheckinEntity;

@Component
public class CheckinDaoMapper {
	public void map(CheckinDetailsDto from, CheckinEntity to) {
		to.setCheckinDateTime(from.getCheckinDateTime());
		to.setMerchantId(12330);
		to.setPreviousCheckinCount(from.getPreviousCheckinCount());
		to.setQrCode(from.getQrCode());
		to.setStatus(from.getStatus());
		to.setUpdatedDateTime(from.getUpdatedDateTime());
		to.setUserId(from.getUserId());
	}
}
