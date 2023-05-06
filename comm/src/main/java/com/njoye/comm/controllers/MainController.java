package com.njoye.comm.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.njoye.comm.models.ChatThread;
import com.njoye.comm.models.LoginUser;
import com.njoye.comm.models.User;
import com.njoye.comm.services.ChatThreadService;
import com.njoye.comm.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class MainController {

	// Add once service is implemented:
    @Autowired
    private UserService userService;
    
    @Autowired
    private ChatThreadService chatThreadService;
	   
	@GetMapping("/")
    public String index(Model model) {
   
	    // Bind empty User and LoginUser objects to the JSP
	    // to capture the form input
	    model.addAttribute("newUser", new User());
	    model.addAttribute("newLogin", new LoginUser());
	    return "index.jsp";
    }
   
	@PostMapping("/register")
	public String register(@Valid @ModelAttribute("newUser") User newUser, 
			BindingResult result, Model model, HttpSession session) {

		// Send newUser and results to UserService for processing
		User registeredUser = userService.register(newUser, result);
	   
		if(result.hasErrors()) {
			// Be sure to send in the empty LoginUser before 
			// re-rendering the page.
			
			model.addAttribute("newLogin", new LoginUser());
			
			return "index.jsp";
			
	   } else {
		   
		   setSessionAttributes(session, registeredUser, 1L);
		   
		   // get userId for first user
		   // Get userId and then user object to associate/set the user column in UserProfile 
		   Long userId = (Long) session.getAttribute("userId");
		   // get user object
		   User userToAssociate = userService.findUser(userId);
		   
		   // Check if there are any chat threads in the database
		   List<ChatThread> allChatThreads = chatThreadService.allChatThreads();

		   // If no chatthreads, then create one and set first user to creator and participant
		   if (allChatThreads.isEmpty()) {
			   
			   // Create chat thread
			   ChatThread newChatThread = new ChatThread();
			   
			   // set user for creator
			   newChatThread.setCreator(userToAssociate);
			   
			   // set name of chatThread
			   newChatThread.setName("Public Chat Forum");
				
			   // initialize list of participants for main chatThread 
			   List<User> participants = new ArrayList<>();
			   
			   // add user to participants
			   participants.add(userToAssociate);
			   
			   // set newChatThread participants with new participant list
			   newChatThread.setParticipants(participants);
			   
			   // save newChatThread to database
			   chatThreadService.createChatThread(newChatThread);
			   
			// else there is already a main chatthread , then add user to participants of main thread
		   } else {
			   
			   // get main chatChatThread to add user 
			   ChatThread mainChatThread = chatThreadService.findFirstChatThread();
		   	   
			   // add user to participants
			   mainChatThread.getParticipants().add(userToAssociate);
			    
			   // update mainChatThread in the database
			   chatThreadService.updateChatThread(mainChatThread);
		   }
		   
		   return "redirect:/dashboard";
	   }
	   
	}
	   
	@PostMapping("/login")
	public String login(@Valid @ModelAttribute("newLogin") LoginUser newLogin, 
	       BindingResult result, Model model, HttpSession session) {
	
		// Send newUser and results to UserService for processing
        User loggedInUser = userService.login(newLogin, result);
	   
        if(result.hasErrors()) {
        	
        	model.addAttribute("newUser", new User());
        	
	        return "index.jsp";
	        
	    } else {
	    	
	    	setSessionAttributes(session, loggedInUser, 1L);
	    	return "redirect:/dashboard";
	    	
	    }
	   
	}
	   
    private void setSessionAttributes(HttpSession session, User user, Long selectedChatThreadId) {
    	session.setAttribute("userId", user.getId());
    	session.setAttribute("userName", user.getUserName());
    	session.setAttribute("selectedChatThreadId", selectedChatThreadId);
    }
	  
	// Add this method to check if the user is logged in
	private boolean isAuthenticated(HttpSession session) {
	    return session.getAttribute("userId") != null;
	}
	
	@PostMapping("/clearSession")
	public String clearSession(HttpSession session) {
		
		if (!isAuthenticated(session)) {
			return "redirect:/";
		}
		
		session.invalidate();
		return "redirect:/";
	}
	
}
