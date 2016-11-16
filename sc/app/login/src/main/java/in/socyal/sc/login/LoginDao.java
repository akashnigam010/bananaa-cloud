package in.socyal.sc.login;

import java.security.NoSuchAlgorithmException;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import in.socyal.sc.api.bo.personnel.dto.PersonnelDto;
import in.socyal.sc.api.bo.personnel.entity.Personnel;
import in.socyal.sc.api.bo.personnel.request.UserSignOnRequest;
import in.socyal.sc.helper.exception.BusinessException;
import in.socyal.sc.helper.hash.HashUtil;
import in.socyal.sc.login.mapper.LoginMapper;
import in.socyal.sc.login.type.LoginErrorCodeType;

@Repository
public class LoginDao {
	private static final Logger LOG = Logger.getLogger(LoginDao.class);
	private static final String PIN = "pin";
	private static final String ACCESS_CODE = "accessCode";
	@Autowired
	LoginMapper mapper;

	public PersonnelDto login(UserSignOnRequest request, Boolean isPinSignOn) throws BusinessException {
		try {
			Query query = new Query(Criteria.where(PIN).is(HashUtil.hash(request.getPin())));
			if (!isPinSignOn) {
				query.addCriteria(Criteria.where(ACCESS_CODE).is(HashUtil.hash(request.getAccessCode())));
			}
			Personnel user = new Personnel();//senseMongoTemplate.findOne(query, Personnel.class);
			user.setId(123);
			user.setName("Admin User");
			user.setRoleId(1);
			/*if (user == null) {
				throw new BusinessException(LoginErrorCodeType.USER_PIN_ACCESSCODE_DOESNOT_MATCH);
			}*/
			return mapper.map(user);
		} catch (NoSuchAlgorithmException e) {
			LOG.error("No encryption algorithm found while logging in");
			throw new BusinessException(LoginErrorCodeType.ENCRYPT_ALGO_NOT_FOUND, e.getMessage());
		}
	}
}
