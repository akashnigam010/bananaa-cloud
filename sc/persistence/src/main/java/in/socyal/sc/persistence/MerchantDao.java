package in.socyal.sc.persistence;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import in.socyal.sc.api.helper.exception.BusinessException;
import in.socyal.sc.api.merchant.dto.MerchantDto;
import in.socyal.sc.api.merchant.dto.MerchantFilterCriteria;
import in.socyal.sc.api.type.error.MerchantErrorCodeType;
import in.socyal.sc.persistence.entity.MerchantEntity;
import in.socyal.sc.persistence.mapper.MerchantDaoMapper;

@Repository
public class MerchantDao {
	private static final String NAME = "name";
	@Autowired
	SessionFactory sessionFactory;
	@Autowired
	MerchantDaoMapper mapper;

	public MerchantDao() {
	}

	public MerchantDao(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public MerchantDto getMerchantDetailsByNameId(String nameId, MerchantFilterCriteria filter)
			throws BusinessException {
		MerchantDto dto = null;
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(MerchantEntity.class);
		criteria.add(Restrictions.eq("nameId", nameId));
		criteria.add(Restrictions.eq("isActive", Boolean.TRUE));
		MerchantEntity entity = (MerchantEntity) criteria.uniqueResult();
		if (entity != null) {
			dto = new MerchantDto();
			mapper.map(entity, dto, filter);
		} else {
			throw new BusinessException(MerchantErrorCodeType.MERCHANT_DETAILS_NOT_FOUND);
		}

		return dto;
	}

	public List<MerchantDto> searchActiveMerchant(String restaurantName, MerchantFilterCriteria filter)
			throws BusinessException {
		List<MerchantDto> merchantDtos = null;
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(MerchantEntity.class);
		criteria.add(Restrictions.ilike(NAME, restaurantName, MatchMode.ANYWHERE));
		criteria.add(Restrictions.eq("isActive", Boolean.TRUE));
		@SuppressWarnings("unchecked")
		List<MerchantEntity> merchants = (List<MerchantEntity>) criteria.list();
		if (merchants != null && !merchants.isEmpty()) {
			merchantDtos = new ArrayList<>();
			mapper.map(merchants, merchantDtos, filter);
		}
		return merchantDtos;
	}
	
	public List<MerchantDto> searchMerchant(String restaurantName, MerchantFilterCriteria filter)
			throws BusinessException {
		List<MerchantDto> merchantDtos = null;
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(MerchantEntity.class);
		criteria.add(Restrictions.ilike(NAME, restaurantName, MatchMode.ANYWHERE));
		@SuppressWarnings("unchecked")
		List<MerchantEntity> merchants = (List<MerchantEntity>) criteria.list();
		if (merchants != null && !merchants.isEmpty()) {
			merchantDtos = new ArrayList<>();
			mapper.map(merchants, merchantDtos, filter);
		}
		return merchantDtos;
	}
}
