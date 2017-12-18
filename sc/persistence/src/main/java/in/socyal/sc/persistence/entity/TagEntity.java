package in.socyal.sc.persistence.entity;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import in.socyal.sc.persistence.type.BucketUrlType;

@MappedSuperclass
public class TagEntity extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "NAME")
	private String name;

	@Column(name = "NAME_ID")
	private String nameId;
	
	@Column(name = "IS_ACTIVE")
	private Boolean isActive;
	
	@ManyToOne
	@JoinColumn(name = "VEGNONVEG_ID")
	private VegnonvegEntity vegnonveg;
	
	@Column(name = "IMAGE_URL")
	private String imageUrl;

	@Column(name = "THUMBNAIL")
	private String thumbnail;

	public TagEntity() {

	}

	public TagEntity(Calendar createdDateTime, Calendar updatedDateTime) {
		super(createdDateTime, updatedDateTime);
		this.isActive = Boolean.TRUE;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNameId() {
		return nameId;
	}

	public void setNameId(String nameId) {
		this.nameId = nameId;
	}

	public String getImageUrl() {
		return BucketUrlType.URL_PREFIX.getUrlPrefix() + imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getThumbnail() {
		return BucketUrlType.URL_PREFIX.getUrlPrefix() +  thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public VegnonvegEntity getVegnonveg() {
		return vegnonveg;
	}

	public void setVegnonveg(VegnonvegEntity vegnonveg) {
		this.vegnonveg = vegnonveg;
	}
}
