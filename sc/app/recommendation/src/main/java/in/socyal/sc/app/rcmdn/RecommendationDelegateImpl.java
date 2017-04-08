package in.socyal.sc.app.rcmdn;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import in.socyal.sc.api.helper.exception.BusinessException;
import in.socyal.sc.api.merchant.response.RecommendationResponse;
import in.socyal.sc.api.recommendation.dto.RecommendationDto;
import in.socyal.sc.api.recommendation.request.GetRecommendationRequest;
import in.socyal.sc.app.rcmdn.mapper.RecommendationMapper;
import in.socyal.sc.helper.security.jwt.JwtTokenHelper;
import in.socyal.sc.persistence.RecommendationDao;

@Component
public class RecommendationDelegateImpl implements RecommendationDelegate {
	@Autowired
	RecommendationDao dao;
	@Autowired
	JwtTokenHelper jwtHelper;
	@Autowired
	RecommendationMapper mapper;
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
	public RecommendationResponse getMyRecommendations(GetRecommendationRequest request) throws BusinessException {
		RecommendationResponse response = new RecommendationResponse();
		List<RecommendationDto> result = dao.getMyRecommendations(
				jwtHelper.getUserId(), request.getMerchantId(), request.getPage());
		mapper.map(result, response);
		return response;
	}
}
