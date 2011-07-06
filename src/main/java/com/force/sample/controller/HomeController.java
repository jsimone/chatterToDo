package com.force.sample.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.force.sample.model.ChatterPost;
import com.force.sample.service.ChatterService;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	private ChatterService chatterService = new ChatterService();

	/**
	 * Simply selects the home view to render by returning its name.
	 * @throws IOException 
	 */
	@RequestMapping(value="/", method=RequestMethod.GET)
	public ModelAndView home(HttpServletRequest req, HttpServletResponse res) throws IOException {
	    logger.info("requesting home");
	    	
	    List<ChatterPost> posts = chatterService.getFeed();
	    System.out.println("TO DO POSTS...............");
	    for(ChatterPost post : posts) {
	        System.out.println(post.toString());
	    }
	    ModelAndView mv = new ModelAndView("home");
	    mv.addObject("posts", posts);
		return mv;
	}

}

