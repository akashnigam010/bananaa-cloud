package in.socyal.sc.api.firebase;

import java.util.ArrayList;
import java.util.List;

public class User {
	private String uid;
	private String email;
	private boolean emailVerified;
	private String displayName;
	private String photoURL;
	private String disabled;
	private Metadata metadata;
	private List<ProviderData> providerData;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getEmail() {
		return providerData.get(0).getEmail();
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isEmailVerified() {
		return emailVerified;
	}

	public void setEmailVerified(boolean emailVerified) {
		this.emailVerified = emailVerified;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getDisabled() {
		return disabled;
	}

	public void setDisabled(String disabled) {
		this.disabled = disabled;
	}

	public String getPhotoURL() {
		return photoURL;
	}

	public void setPhotoURL(String photoURL) {
		this.photoURL = photoURL;
	}

	public Metadata getMetadata() {
		return metadata;
	}

	public void setMetadata(Metadata metadata) {
		this.metadata = metadata;
	}

	public List<ProviderData> getProviderData() {
		if (this.providerData == null) {
			this.providerData = new ArrayList<>();
		}
		return providerData;
	}

	public void setProviderData(List<ProviderData> providerData) {
		this.providerData = providerData;
	}

}
