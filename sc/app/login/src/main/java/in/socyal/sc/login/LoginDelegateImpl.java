package in.socyal.sc.login;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.socyal.sc.api.login.dto.FacebookUser;
import in.socyal.sc.api.login.request.LoginRequest;
import in.socyal.sc.api.login.response.LoginResponse;
import in.socyal.sc.helper.exception.BusinessException;
import in.socyal.sc.login.dao.LoginDao;
import in.socyal.sc.login.type.LoginErrorCodeType;

@Service
public class LoginDelegateImpl implements LoginDelegate {
	private static final String JWT = "ASDJask876ah48y93nLIAUD972OHlijpsojwiejrwjb298273409UAJHDF9P8273YKJHASJNUGILU"
			+ "Kkugkjgigi787798JKH98Y98YOUHJKKJBIYt876ugG87687GHVGFXDTRe64364eytdyte65RUYFKJBMNLiu98787676RYTCNVJYt76"
			+ "65E75RFiyg87574y5erytcgvuyt76576";
	private static final String JWT_LOW_RISK = "ASDJask876ah48y93nLIAUD972OHlijpsojwiejrwjb298273409UAJHDF9P8273YKJHASJNUGILU"
			+ "Kkugkjgigi787798JKH98Y98YOUHJKKJBIYt876ugG87687GHVGFXDTRe64364eytdyte65RUYFKJBMNLiu98787676RYTCNVJYt76"
			+ "65E75RFiyg87574y5erytcgvuyt76576";
	@Autowired
	LoginDao dao;
	@Autowired
	LoginMapper mapper;
	@Autowired
	OAuth2FbHelper fbHelper;
	
	@Override
	public LoginResponse skipLogin() {
		LoginResponse response = new LoginResponse();
		response.setAccessToken(JWT_LOW_RISK);
		response.setUser(mapper.mapGuestUser());
		return response;
	}

	@Override
	public LoginResponse fbLogin(LoginRequest request) throws BusinessException {
		LoginResponse response = new LoginResponse();
		try {
			FacebookUser fbUser = fbHelper.getUserGraphDataWithAccessToken(request.getFbAccessToken());
			if (fbUser.getId().equals(request.getFbId())) {
				response.setAccessToken(JWT);
				response.setUser(mapper.mapFbUserToUserDto(fbUser));
			} else {
				throw new BusinessException(LoginErrorCodeType.USER_NOT_FOUND);
			}
		} catch (IOException e) {
			throw new BusinessException(LoginErrorCodeType.INCORRECT_FB_TOKEN);
		}
		return response;
	}
	
	@Override
	public LoginResponse fbLoginWithCode(String code) throws BusinessException {
		LoginResponse response = new LoginResponse();
		try {
			FacebookUser fbUser = fbHelper.getUserGraphDataWithCode(code);
			response.setAccessToken(JWT);
			response.setUser(mapper.mapFbUserToUserDto(fbUser));
		} catch (IOException e) {
			throw new BusinessException(LoginErrorCodeType.INCORRECT_FB_TOKEN);
		}
		return response;
	}

}
