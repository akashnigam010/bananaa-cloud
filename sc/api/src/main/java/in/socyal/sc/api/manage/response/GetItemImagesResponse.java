package in.socyal.sc.api.manage.response;

import java.util.ArrayList;
import java.util.List;

import in.socyal.sc.api.dish.dto.ItemImageDto;
import in.socyal.sc.api.response.GenericResponse;

public class GetItemImagesResponse extends GenericResponse {
	private static final long serialVersionUID = 1L;
	private List<ItemImageDto> images;

	public List<ItemImageDto> getImages() {
		if (this.images == null) {
			this.images = new ArrayList<>();
		}
		return images;
	}

	public void setImages(List<ItemImageDto> images) {
		this.images = images;
	}
}
