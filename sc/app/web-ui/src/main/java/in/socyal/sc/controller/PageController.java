package in.socyal.sc.controller;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import in.socyal.sc.api.merchant.dto.Location;
import in.socyal.sc.api.merchant.request.GetMerchantListRequest;
import in.socyal.sc.api.merchant.response.MerchantResponse;
import in.socyal.sc.api.org.Organization;
import in.socyal.sc.api.org.OrganizationPage;

@Controller
@RequestMapping("/")
public class PageController {
	@Autowired MerchantService merchantService;
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView home() {
		GetMerchantListRequest request = new GetMerchantListRequest();
		Location location = new Location();
		location.setLatitude(17.470325);
		location.setLongitude(78.368113);
		request.setLocation(location);
		request.setPage(1);
		ModelAndView modelAndView = new ModelAndView("index");
		MerchantResponse merchant = new MerchantResponse();
		merchant.setName("Sandeep");
		merchant.setShortAddress("Madhapur");
		
		Organization org = new Organization();
		org.setName("Bananaa");
		org.setTagline("Loreium ipsum dolor sit amet");
		org.setAddressLine1("ORG.ADDRESSLINE1");
		org.setAddressLine2("Madhapur, Hyderabad");
		org.setAddressLine3("123.456.7890");
		org.setWebsite("www.bananaa.in");
		OrganizationPage page = new OrganizationPage();
		page.setTitle("Home | Bananaa.in");
		
		modelAndView.addObject("merchants", Collections.singletonList(merchant));
		modelAndView.addObject("org", org);
		modelAndView.addObject("page", page);
		return modelAndView;
	}
}
