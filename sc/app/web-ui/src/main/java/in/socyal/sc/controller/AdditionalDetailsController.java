package in.socyal.sc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AdditionalDetailsController {

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
}
