package in.socyal.sc.app.merchant;

import org.apache.commons.lang3.text.WordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import in.socyal.sc.api.SearchRequest;
import in.socyal.sc.api.helper.exception.BusinessException;
import in.socyal.sc.api.manage.request.AddItemRequest;
import in.socyal.sc.api.manage.request.AddRequest;
import in.socyal.sc.api.manage.response.GetCuisinesResponse;
import in.socyal.sc.api.manage.response.GetSuggestionsResponse;
import in.socyal.sc.persistence.ManagementDao;

@Service
public class ManagementDelegateImpl implements ManagementDelegate {

	@Autowired
	ManagementDao dao;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
	public void addItem(AddItemRequest request) throws BusinessException {
		request.setName(WordUtils.capitalizeFully(request.getName()));
		request.setNameId(createNameId(request.getName()));
		dao.addItem(request);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
	public void addCuisine(AddRequest request) {
		dao.addCuisine(request);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
	public void addSuggestion(AddRequest request) {
		dao.addSuggestion(request);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
	public GetCuisinesResponse getCuisines(SearchRequest request) {
		GetCuisinesResponse response = new GetCuisinesResponse();
		response.setCuisines(dao.getCuisines(request));
		return response;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
	public GetSuggestionsResponse getSuggestions(SearchRequest request) {
		GetSuggestionsResponse response = new GetSuggestionsResponse();
		response.setSuggestions(dao.getSuggestions(request));
		return response;
	}

	private String createNameId(String name) {
		String[] nameSegments = name.split(" ");
		StringBuilder nameId = new StringBuilder();
		int i;
		for (i = 0; i < nameSegments.length - 1; i++) {
			nameId.append(nameSegments[i].toLowerCase() + "-");
		}
		nameId.append(nameSegments[i].toLowerCase());
		return nameId.toString();
	}
}
