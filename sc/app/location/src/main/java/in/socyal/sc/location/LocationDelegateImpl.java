package in.socyal.sc.location;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.socyal.sc.api.location.request.SearchLocationRequest;
import in.socyal.sc.api.location.response.GetLocalitiesResponse;
import in.socyal.sc.persistence.LocationDao;

@Service
public class LocationDelegateImpl implements LocationDelegate {
	@Autowired
	LocationDao dao;

	@Override
	public GetLocalitiesResponse getLocalities() {
		GetLocalitiesResponse response = new GetLocalitiesResponse();
		response.setLocalities(dao.getLocalities());
		return response;
	}

	@Override
	public GetLocalitiesResponse searchLocalities(SearchLocationRequest request) {
		GetLocalitiesResponse response = new GetLocalitiesResponse();
		response.setLocalities(dao.searchLocalities(request.getSearchString()));
		return response;
	}
}
