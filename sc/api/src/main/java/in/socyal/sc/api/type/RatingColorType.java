package in.socyal.sc.api.type;

public enum RatingColorType {
	R10("#CD1C26", "r10"),
	R15("#DE1D05", "r15"),
	R20("#FF7800", "r20"),
	R25("#FFBA00", "r25"),
	R30("#CDD614", "r30"),
	R35("#9ACD32", "r35"),
	R40("#5BA829", "r40"),
	R45("#3F7E00", "r45"),
	R50("#305D02", "r50");

	private String code;
	private String cssClass;

	private RatingColorType(String code, String cssClass) {
		this.cssClass = cssClass;
		this.code = code;
	}

	public String getCssClass() {
		return cssClass;
	}

	public String getCode() {
		return code;
	}

	public static RatingColorType getCodeByCssClass(String cssClass) {
		for (RatingColorType type : RatingColorType.values()) {
			if (type.getCssClass().equalsIgnoreCase(cssClass)) {
				return type;
			}
		}
		return null;
	}
	
	public static RatingColorType getCodeByRating(Float rating) {
		if (rating <= 1) {
			return RatingColorType.R10;
		} else if (rating <= 1.5) {
			return RatingColorType.R15;
		} else if (rating <= 2) {
			return RatingColorType.R20;
		} else if (rating <= 2.5) {
			return RatingColorType.R25;
		} else if (rating <= 3) {
			return RatingColorType.R30;
		} else if (rating <= 3.5) {
			return RatingColorType.R35;
		} else if (rating <= 4) {
			return RatingColorType.R40;
		} else if (rating <= 4.5) {
			return RatingColorType.R45;
		} else {
			return RatingColorType.R50;
		}
	}
}
