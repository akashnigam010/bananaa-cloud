package in.socyal.sc.persistence.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import in.socyal.sc.api.merchant.business.dto.MerchantLoginDto;
import in.socyal.sc.api.merchant.dto.MerchantDto;
import in.socyal.sc.api.merchant.dto.MerchantFilterCriteria;
import in.socyal.sc.persistence.entity.MerchantLoginEntity;

@Component
public class MerchantLoginDaoMapper {
	@Autowired MerchantDaoMapper merchantDaoMapper;
	
	public void map(MerchantLoginEntity from, MerchantLoginDto to) {
		to.setId(from.getId());
		to.setDeviceId(from.getDeviceId());
		MerchantDto merchantDto = new MerchantDto();
		MerchantFilterCriteria filter = new MerchantFilterCriteria(false, true);
		merchantDaoMapper.map(from.getMerchant(), merchantDto, filter);
		to.setMerchant(merchantDto);
	}
}
