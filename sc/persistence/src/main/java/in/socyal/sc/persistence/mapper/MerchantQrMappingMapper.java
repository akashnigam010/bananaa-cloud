package in.socyal.sc.persistence.mapper;

import org.springframework.stereotype.Component;

import in.socyal.sc.api.qr.dto.MerchantQrMappingDto;
import in.socyal.sc.persistence.entity.MerchantQrMappingEntity;

@Component
public class MerchantQrMappingMapper {

	public void map(MerchantQrMappingEntity entity, MerchantQrMappingDto dto) {
		dto.setCardId(entity.getCardId());
		dto.setId(entity.getId());
		dto.setMerchantId(entity.getMerchantId());
		dto.setQrCode(entity.getQrCode());
		dto.setStatus(entity.getStatus());
	}

}
