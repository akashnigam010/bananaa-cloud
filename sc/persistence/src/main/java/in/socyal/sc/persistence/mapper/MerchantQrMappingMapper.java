package in.socyal.sc.persistence.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import in.socyal.sc.api.merchant.dto.MerchantDto;
import in.socyal.sc.api.qr.dto.MerchantQrMappingDto;
import in.socyal.sc.persistence.entity.MerchantQrMappingEntity;

@Component
public class MerchantQrMappingMapper {
	@Autowired MerchantDaoMapper merchantMapper;
	
	public void map(MerchantQrMappingEntity entity, MerchantQrMappingDto dto) {
		dto.setCardId(entity.getCardId());
		dto.setId(entity.getId());
		MerchantDto merchant = new MerchantDto();
		merchantMapper.map(entity.getMerchant(), merchant, false);
		dto.setMerchant(merchant);
		dto.setQrCode(entity.getQrCode());
		dto.setStatus(entity.getStatus());
	}
}
