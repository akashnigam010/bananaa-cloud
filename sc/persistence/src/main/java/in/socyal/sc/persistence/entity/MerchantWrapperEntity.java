package in.socyal.sc.persistence.entity;

public class MerchantWrapperEntity {
	private MerchantEntity merchant;
	private String dishName;

	public MerchantEntity getMerchant() {
		return merchant;
	}

	public void setMerchant(MerchantEntity merchant) {
		this.merchant = merchant;
	}

	public String getDishName() {
		return dishName;
	}

	public void setDishName(String dishName) {
		this.dishName = dishName;
	}
}
