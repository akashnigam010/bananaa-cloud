package in.socyal.sc.persistence.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "ITEM_IMAGE", schema = "bna")
public class ItemImageEntity extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "NAME")
	private String name;

	@Column(name = "IMAGE_URL")
	private String imageUrl;

	@Column(name = "THUMBNAIL")
	private String thumbnail;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}
}
