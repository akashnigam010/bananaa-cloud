package in.socyal.sc.app.rcmdn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import in.socyal.sc.api.SearchRequest;
import in.socyal.sc.api.helper.exception.BusinessException;
import in.socyal.sc.api.item.response.ItemsResponse;
import in.socyal.sc.app.rcmdn.mapper.ItemMapper;
import in.socyal.sc.persistence.DishDao;

@Component
public class ItemDelegateImpl implements ItemDelegate {
	@Autowired
	DishDao dishDao;
	@Autowired
	ItemMapper mapper;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
	public ItemsResponse searchItems(SearchRequest request) {
		ItemsResponse response = new ItemsResponse();
		response.setItems(
				mapper.map(dishDao.searchDishAtARestaurant(request.getSearchString(), request.getMerchantId())));
		return response;
	}

}
