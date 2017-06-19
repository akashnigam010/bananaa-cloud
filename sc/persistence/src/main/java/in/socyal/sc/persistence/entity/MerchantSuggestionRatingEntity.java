package in.socyal.sc.persistence.entity;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "MERCHANT_SUGGESTION_RATING", schema = "bna")
public class MerchantSuggestionRatingEntity extends MerchantRatingEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name = "SUGGESTION_ID")
	private SuggestionEntity suggestion;

	public MerchantSuggestionRatingEntity() {

	}

	public MerchantSuggestionRatingEntity(Calendar createdDateTime, Calendar updatedDateTime) {
		super(createdDateTime, updatedDateTime);
	}

	public SuggestionEntity getSuggestion() {
		return suggestion;
	}

	public void setSuggestion(SuggestionEntity tag) {
		this.suggestion = tag;
	}
	
	@Override
	public TagEntity getTag() {
		return getSuggestion();
	}
}
