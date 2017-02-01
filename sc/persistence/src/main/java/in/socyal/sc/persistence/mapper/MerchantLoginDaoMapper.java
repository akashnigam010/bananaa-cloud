package in.socyal.sc.persistence.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import in.socyal.sc.api.merchant.business.dto.MerchantLoginDto;
import in.socyal.sc.api.merchant.dto.MerchantDto;
import in.socyal.sc.persistence.entity.MerchantLoginEntity;

@Component
public class MerchantLoginDaoMapper {
	@Autowired MerchantDaoMapper merchantDaoMapper;
	
	public void map(MerchantLoginEntity from, MerchantLoginDto to) {
		to.setId(from.getId());
		to.setDeviceId(from.getDeviceId());
		MerchantDto merchantDto = new MerchantDto();
		merchantDaoMapper.map(from.getMerchant(), merchantDto, Boolean.FALSE);
		to.setMerchant(merchantDto);
	}
}
