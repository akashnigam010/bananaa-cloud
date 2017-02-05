package in.socyal.sc.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import in.socyal.sc.api.merchant.dto.Location;
import in.socyal.sc.api.merchant.request.GetMerchantListRequest;
import in.socyal.sc.api.merchant.response.MerchantDetailsResponse;
import in.socyal.sc.api.merchant.response.MerchantResponse;
import in.socyal.sc.api.org.Organization;
import in.socyal.sc.api.org.OrganizationPage;

@Controller
public class PageController {
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home() {
		return "redirect:hyderabad";
	}
	
	@RequestMapping(value = "/hyderabad", method = RequestMethod.GET)
	public ModelAndView city() {
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
	
	@RequestMapping(value = "/about", method = RequestMethod.GET)
	public ModelAndView about() {
		ModelAndView modelAndView = new ModelAndView("about");
		return modelAndView;
	}
	
	@RequestMapping(value = "/contact", method = RequestMethod.GET)
	public ModelAndView contact() {
		ModelAndView modelAndView = new ModelAndView("contact");
		return modelAndView;
	}
	
	@RequestMapping(value = "/privacy", method = RequestMethod.GET)
	public ModelAndView privacy() {
		ModelAndView modelAndView = new ModelAndView("privacy");
		return modelAndView;
	}
	
	@RequestMapping(value = "/terms", method = RequestMethod.GET)
	public ModelAndView terms() {
		ModelAndView modelAndView = new ModelAndView("terms");
		return modelAndView;
	}
	
	@RequestMapping(value = "/hyderabad/{id}", method = RequestMethod.GET)
	public ModelAndView details(@PathVariable String id) {
		ModelAndView modelAndView = new ModelAndView("detail");
		List<String> cusines = new ArrayList<>();
		cusines.add("Mexican");
		cusines.add("Asian");
		cusines.add("Italian");
		List<String> type = new ArrayList<>();
		type.add("Open Bar");
		type.add("Roof Top");
		type.add("Dance Floor");		
		MerchantDetailsResponse response = new MerchantDetailsResponse();
		response.setId(12345);
		response.setName("Free Flow");
		response.setShortAddress("Jubilee Hils, Hyderabad");
		response.setCheckins(193);
		response.setImageUrl("https://s3.ap-south-1.amazonaws.com/bananaimages/12351.png");
		response.setRating(4.5);
		response.setIsOpen(true);
		response.setOpeningHours(Collections.singletonList("10:00 AM to 09:00 PM"));
		response.setCuisines(cusines);
		response.setType(type);
		response.setAverageCost(1400.00);
		response.setLongAddress("Near Jubilee Checkpost, Hyderabad, 500098");
		response.setPreviousCheckinCount(12);
		response.setPhone("0409123832600");
		modelAndView.addObject("detail", response);
		return modelAndView;
	}
}
