package in.socyal.sc.app.merchant;

import in.socyal.sc.helper.exception.BusinessException;

public interface MerchantDelegate {
	public void getMerchants() throws BusinessException;
	public void saveMerchantSample();
}
