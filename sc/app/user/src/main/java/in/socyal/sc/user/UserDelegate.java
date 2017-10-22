package in.socyal.sc.user;

import in.socyal.sc.api.engine.request.IdRequest;
import in.socyal.sc.api.helper.exception.BusinessException;
import in.socyal.sc.api.merchant.response.UserDetailsResponse;
import in.socyal.sc.api.type.TagType;
import in.socyal.sc.api.user.dto.Profile;
import in.socyal.sc.api.user.request.StatusRequest;

public interface UserDelegate {
	UserDetailsResponse getUserDetails(String userNameId) throws BusinessException;
	Integer getVegnonvegPreference() throws BusinessException;
	void saveVegnonvegPreference(Integer vegnonvegId) throws BusinessException;
	void updateTagPreference(Integer id, TagType type, boolean isRemove) throws BusinessException;
	Profile getUserProfile(IdRequest request) throws BusinessException;
	void saveStatus(StatusRequest request) throws BusinessException;
}
