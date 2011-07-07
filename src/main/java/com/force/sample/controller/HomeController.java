package com.force.sample.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.force.sample.model.ChatterPost;
import com.force.sample.service.ChatterService;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	private ChatterService chatterService;

	/**
	 * Simply selects the home view to render by returning its name.
	 * @throws IOException 
	 */
	@RequestMapping(value="/", method=RequestMethod.GET)
	public ModelAndView home(HttpServletRequest req, HttpServletResponse res) throws IOException {
	    logger.info("requesting home");
	    	
	    List<ChatterPost> posts = chatterService.getToDoFeed();
	    ModelAndView mv = new ModelAndView("home");
	    mv.addObject("posts", posts);
		return mv;
	}
	
	@RequestMapping(value="/completeItem/{postId}", method=RequestMethod.GET)
	public String completeItem(@PathVariable String postId) {
	    chatterService.setPostToDone(Integer.valueOf(postId));
	    return "redirect:/";
	}
    
    @RequestMapping(value="/unCompleteItem/{postId}", method=RequestMethod.GET)
    public String unCompleteItem(@PathVariable String postId) {
        chatterService.setPostToNotDone(Integer.valueOf(postId));
        return "redirect:/";
    }
}

