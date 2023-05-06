//package com.njoye.comm.controllers;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//
//import com.njoye.comm.models.User;
//import com.njoye.comm.services.UserService;
//
//import jakarta.servlet.http.HttpSession;

//public class Home {
//	@Autowired
//	private UserService userService;
//	
//	// Add this method to check if the user is logged in
//    private boolean isAuthenticated(HttpSession session) {
//        return session.getAttribute("userId") != null;
//    }
//    
//   //  Modify the home() method to check if the user is logged in before rendering the private wall
//    @GetMapping("/home")
//    public String home(Model model, HttpSession session) {
//    	if (!isAuthenticated(session)) {
//    		return "redirect:/";
//    	}
//    	
//    	// Obtain userID from session
//    	Long userId = (Long) session.getAttribute("userId");
//    	
//    	// If there is no userId then redirect to index.jsp
//    	if (userId == null) {
//    		return "redirect:/";
//    	}
//    	
//    	// Get the user object by calling userService.getUserById(userId)
//    	User user = userService.findUser(userId);
//    	
//    	
//    	// Insert User information into model
//    	model.addAttribute("user", user);
//    	
//    	return "home.jsp";
//    }
//    
//}
