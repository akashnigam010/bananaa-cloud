package in.socyal.sc.persistence.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import in.socyal.sc.api.merchant.dto.MerchantDto;
import in.socyal.sc.api.merchant.dto.MerchantFilterCriteria;
import in.socyal.sc.api.qr.dto.MerchantQrMappingDto;
import in.socyal.sc.persistence.entity.MerchantQrMappingEntity;

@Component
public class MerchantQrMappingMapper {
	@Autowired MerchantDaoMapper merchantMapper;
	
	public void map(MerchantQrMappingEntity entity, MerchantQrMappingDto dto, MerchantFilterCriteria filter) {
		dto.setCardId(entity.getCardId());
		dto.setId(entity.getId());
		MerchantDto merchant = new MerchantDto();
		merchantMapper.map(entity.getMerchant(), merchant, filter);
		dto.setMerchant(merchant);
		dto.setQrCode(entity.getQrCode());
		dto.setStatus(entity.getStatus());
	}
}
