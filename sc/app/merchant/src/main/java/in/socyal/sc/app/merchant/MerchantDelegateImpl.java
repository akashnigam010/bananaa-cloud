package in.socyal.sc.app.merchant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.socyal.sc.helper.exception.BusinessException;
import in.socyal.sc.persistence.MerchantDao;

@Service
public class MerchantDelegateImpl implements MerchantDelegate {
	@Autowired MerchantDao dao;

	@Override
	public void getMerchants() throws BusinessException {
		dao.getMerchants();
	}

	@Override
	public void saveMerchantSample() {
		dao.saveMerchantDetails();
	}
}
