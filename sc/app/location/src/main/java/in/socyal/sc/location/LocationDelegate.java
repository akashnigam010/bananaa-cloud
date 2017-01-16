package in.socyal.sc.location;

import in.socyal.sc.api.location.request.SearchLocationRequest;
import in.socyal.sc.api.location.response.GetLocalitiesResponse;

public interface LocationDelegate {
	public GetLocalitiesResponse getLocalities();
	public GetLocalitiesResponse searchLocalities(SearchLocationRequest request);

}
