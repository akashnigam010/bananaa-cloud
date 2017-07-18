package in.socyal.sc.persistence.entity;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "CUISINE", schema = "bna")
public class CuisineEntity extends TagEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	public CuisineEntity() {

	}

	public CuisineEntity(Calendar createdDateTime, Calendar updatedDateTime) {
		super(createdDateTime, updatedDateTime);
	}
}
