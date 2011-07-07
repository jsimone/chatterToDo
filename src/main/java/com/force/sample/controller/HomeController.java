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
 * Handles requests for the Chatter To Do home page.
 * 
 * @author John Simone
 */
@Controller
public class HomeController {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	//The chatter service will be created and injected in by Spring
	@Autowired
	private ChatterService chatterService;

	/**
	 * This method will render the home page. The chatter service is used to retrieve the list of posts that.
	 * 
	 * @throws IOException 
	 * @return the ModelAndView object for the home page
	 */
	@RequestMapping(value="/", method=RequestMethod.GET)
	public ModelAndView home(HttpServletRequest req, HttpServletResponse res) throws IOException {
	    logger.info("requesting home");
	    	
	    List<ChatterPost> posts = chatterService.getToDoFeed();
	    ModelAndView mv = new ModelAndView("home");
	    mv.addObject("posts", posts);
		return mv;
	}
	
	/**
	 * Sets the post as completed and re-loads the home page.
	 * 
	 * @param postId
	 * @return a redirect to load the home page
	 */
	@RequestMapping(value="/completeItem/{postId}", method=RequestMethod.GET)
	public String completeItem(@PathVariable String postId) {
	    logger.info("completing post: " + postId);
	    chatterService.setPostToDone(Integer.valueOf(postId));
	    return "redirect:/";
	}
    
	/**
	 * Sets the post as not completed and re-loads the home page.
	 * 
	 * @param postId
	 * @return a redirect to load the home page
	 */
    @RequestMapping(value="/unCompleteItem/{postId}", method=RequestMethod.GET)
    public String unCompleteItem(@PathVariable String postId) {
        logger.info("un-completing post: " + postId);
        chatterService.setPostToNotDone(Integer.valueOf(postId));
        return "redirect:/";
    }
}

