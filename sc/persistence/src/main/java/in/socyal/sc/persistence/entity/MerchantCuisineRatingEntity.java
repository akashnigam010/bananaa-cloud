package in.socyal.sc.persistence.entity;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "MERCHANT_CUISINE_RATING", schema = "bna")
public class MerchantCuisineRatingEntity extends MerchantRatingEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name = "CUISINE_ID")
	private CuisineEntity cuisine;

	public MerchantCuisineRatingEntity() {

	}

	public MerchantCuisineRatingEntity(Calendar createdDateTime, Calendar updatedDateTime) {
		super(createdDateTime, updatedDateTime);
	}

	public CuisineEntity getCuisine() {
		return cuisine;
	}

	public void setCuisine(CuisineEntity cuisine) {
		this.cuisine = cuisine;
	}

	@Override
	public TagEntity getTag() {
		return getCuisine();
	}
}
