package in.socyal.sc.persistence.entity;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "SUGGESTION", schema = "bna")
public class SuggestionEntity extends TagEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	public SuggestionEntity() {

	}

	public SuggestionEntity(Calendar createdDateTime, Calendar updatedDateTime) {
		super(createdDateTime, updatedDateTime);
	}
}
