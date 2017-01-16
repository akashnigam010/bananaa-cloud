package in.socyal.sc.helper.distance;

import org.apache.commons.lang3.StringUtils;

public enum DistanceUnitType {
	KM("K", "Kilometer"),
	MILE("M", "Mile");
	
	private String code;
	private String description;
	
	private DistanceUnitType(String code, String description) {
		this.code = code;
		this.description = description;
	}
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public static DistanceUnitType getByCode(String code) {
		for (DistanceUnitType type : DistanceUnitType.values()) {
			if (StringUtils.equalsIgnoreCase(type.getCode(), code)) {
				return type;
			}
		}
		
		return DistanceUnitType.KM;
	}
}
