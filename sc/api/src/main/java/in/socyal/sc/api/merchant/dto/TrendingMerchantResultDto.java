package in.socyal.sc.api.merchant.dto;

public class TrendingMerchantResultDto {
	private Float rating;
	private MerchantDto merchant;

	public Float getRating() {
		return rating;
	}

	public void setRating(Float rating) {
		this.rating = rating;
	}

	public MerchantDto getMerchant() {
		return merchant;
	}

	public void setMerchant(MerchantDto merchant) {
		this.merchant = merchant;
	}
}
