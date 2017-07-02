package in.socyal.sc.api.item.response;

import in.socyal.sc.api.type.TagType;

public class TagShortDetails {
	private String nameId;
	private String name;
	private TagType tagType;

	public String getNameId() {
		return nameId;
	}

	public void setNameId(String nameId) {
		this.nameId = nameId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public TagType getTagType() {
		return tagType;
	}

	public void setTagType(TagType tagType) {
		this.tagType = tagType;
	}
}
