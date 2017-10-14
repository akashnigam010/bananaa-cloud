package in.socyal.sc.controller;

import java.util.ResourceBundle;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AdditionalDetailsController {
	private ResourceBundle resource = ResourceBundle.getBundle("environment");
	private static final String USERNAME = "bna.manage.username";
	private static final String PASSWORD = "bna.manage.password";
	private static final String USERNAME_VENDOR = "bna.vendor.username";
	private static final String PASSWORD_VENDOR = "bna.vendor.password";
	private static final String GOOGLE_PLAYSTORE_URL = "https://play.google.com/store/apps/details?id=in.bananaa";
	
	@RequestMapping(value = "/blogin", method = RequestMethod.GET)
	public ModelAndView managementConsole() {
		ModelAndView modelAndView = new ModelAndView("blogin");
		return modelAndView;
	}
	
	@RequestMapping(value = "/bna/manage/managementConsole", method = RequestMethod.GET)
	public ModelAndView blogin() {
		ModelAndView modelAndView = new ModelAndView("blogin");
		return modelAndView;
	}
	
	@RequestMapping(value = "/bna/manage/managementConsole", method = RequestMethod.POST)
	public ModelAndView blogin(
			@RequestParam(value = "username", required = false) String username,
			@RequestParam(value = "password", required = false) String password) {
		ModelAndView modelAndView = new ModelAndView();
		if (StringUtils.isNotBlank(username) && StringUtils.isNotBlank(password)) {
			if (username.equals(resource.getString(USERNAME)) && password.equals(resource.getString(PASSWORD))) {
				modelAndView.setViewName("manage");
			} else if (username.equals(resource.getString(USERNAME_VENDOR)) && password.equals(resource.getString(PASSWORD_VENDOR))) {
				modelAndView.setViewName("vendor");
			} else {
				modelAndView.setViewName("blogin");
			}			
		}
		return modelAndView;
	}

	@RequestMapping(value = "/google6f82628d4ebc8d30.html", method = RequestMethod.GET)
	public ModelAndView googleWebmasterVerify() {
		ModelAndView modelAndView = new ModelAndView("google6f82628d4ebc8d30");
		return modelAndView;
	}
	
	@RequestMapping(value = "/about", method = RequestMethod.GET)
	public ModelAndView about() {
		ModelAndView modelAndView = new ModelAndView("about");
		modelAndView.addObject("description", "About Bananaa");
		modelAndView.addObject("fbDescription", "About Bananaa");
		modelAndView.addObject("title", "About | Bananaa");
		modelAndView.addObject("url", "http://www.bananaa.in/about");
		modelAndView.addObject("imageUrl", "https://bna-s3.s3.amazonaws.com/img/what-mini.jpg");
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
	
	@RequestMapping(value = "/how", method = RequestMethod.GET)
	public ModelAndView how() {
		ModelAndView modelAndView = new ModelAndView("how");
		modelAndView.addObject("description", "Recommendations. But wait..how ?");
		modelAndView.addObject("fbDescription", "Recommendations. But wait..how ?");
		modelAndView.addObject("title", "Recommendations. But wait..how ? | Bananaa");
		modelAndView.addObject("url", "http://www.bananaa.in/how");
		modelAndView.addObject("imageUrl", "https://bna-s3.s3.amazonaws.com/img/next.jpg");
		return modelAndView;
	}
	
	@RequestMapping(value = "/next", method = RequestMethod.GET)
	public ModelAndView next() {
		ModelAndView modelAndView = new ModelAndView("next");
		return modelAndView;
	}
	
	@RequestMapping(value = "/app", method = RequestMethod.GET)
	public ModelAndView app() {
		ModelAndView modelAndView = new ModelAndView("app");
		return modelAndView;
	}
}
