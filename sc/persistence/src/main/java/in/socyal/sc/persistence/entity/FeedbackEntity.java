package in.socyal.sc.persistence.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import in.socyal.sc.api.type.FeedbackStatusType;

@Entity
@Table(name = "FEEDBACK", schema = "Socyal")
public class FeedbackEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private Integer id;
	
	@Column(name = "USER_ID")
	private Integer userId;
	
	@Column(name = "MERCHANT_ID")
	private Integer merchantId;
	
	@Column(name = "STATUS")
	@Enumerated(EnumType.STRING)
	private FeedbackStatusType status;
	
	@Column(name = "FOOD_RATING")
	private Integer foodRating;
	
	@Column(name = "SERVICE_RATING")
	private Integer serviceRating;
	
	@Column(name = "AMBIENCE_RATING")
	private Integer ambienceRating;
	
}
