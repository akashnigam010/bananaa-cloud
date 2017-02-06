package in.socyal.sc.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import in.socyal.sc.api.merchant.request.MerchantDetailsRequest;
import in.socyal.sc.api.merchant.response.MerchantDetailsResponse;
import in.socyal.sc.app.merchant.MerchantDelegate;
import in.socyal.sc.helper.security.jwt.JwtHelper;

@Controller
public class PageController {
	@Autowired MerchantDelegate merchantDelegate;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home() {
		return "redirect:hyderabad";
	}
	
	@RequestMapping(value = "/hyderabad", method = RequestMethod.GET)
	public ModelAndView city() {
		ModelAndView modelAndView = new ModelAndView("index");
		modelAndView.addObject("accessToken", JwtHelper.createJsonWebTokenForGuest());
		modelAndView.addObject("city", "hyderabad");
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
		MerchantDetailsRequest request = new MerchantDetailsRequest();
		request.setId(Integer.parseInt(id));
		MerchantDetailsResponse response = merchantDelegate.getMerchantDetails(request);
		
		ModelAndView modelAndView = new ModelAndView("detail");
		modelAndView.addObject("detail", response);
		modelAndView.addObject("userImage", "https://fb-s-a-a.akamaihd.net/h-ak-xfl1/v/t1.0-1/p160x160/15826261_1227586443984803_2081423736824561505_n.jpg?oh=c3604ca3d4199d5561c2eb4e2621ee3d&oe=5902B1FE&__gda__=1494528018_4c3d954c3fe507b4d4aa581df02de6e7");
		return modelAndView;
	}
}
