package in.socyal.sc.api.type;

public enum RoleType {
	USER(1, "USER"), 
	GUEST(2, "GUEST"),
	MERCHANT(3, "MERCHANT");

	private Integer id;
	private String role;

	RoleType(Integer id, String role) {
		this.id = id;
		this.role = role;
	}

	public Integer getId() {
		return id;
	}

	public String getRole() {
		return role;
	}

	public static RoleType getRoleById(Integer id) {
		for (RoleType type : RoleType.values()) {
			if (type.getId() == id) {
				return type;
			}
		}

		return null;
	}
	
	public static RoleType getRole(String role) {
		for (RoleType type : RoleType.values()) {
			if (role.equalsIgnoreCase(type.getRole())) {
				return type;
			}
		}

		return null;
	}
}
