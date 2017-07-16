package in.socyal.sc.persistence.entity;

public class TrendingMerchantResultEntity {
	private Double rating;
	private MerchantEntity merchant;

	public Double getRating() {
		return rating;
	}

	public void setRating(Double rating) {
		this.rating = rating;
	}

	public MerchantEntity getMerchant() {
		return merchant;
	}

	public void setMerchant(MerchantEntity merchant) {
		this.merchant = merchant;
	}

}
