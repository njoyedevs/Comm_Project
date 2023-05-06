package com.njoye.comm.controllers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Enumeration;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.njoye.comm.models.ChatThread;
import com.njoye.comm.models.Message;
import com.njoye.comm.models.User;
import com.njoye.comm.models.UserProfile;
import com.njoye.comm.services.ChatThreadService;
import com.njoye.comm.services.UserProfileService;
import com.njoye.comm.services.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class DashboardController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private UserProfileService userProfileService;
	
	@Autowired
	private ChatThreadService chatThreadService;
	
    // Add this method to check if the user is logged in
    private boolean isAuthenticated(HttpSession session) {
        return session.getAttribute("userId") != null;
    }
    
    @GetMapping("/testTheme")
    public String testTheme(){
    	
    	return "testTheme.jsp";
    }
    
    @GetMapping("/dashboard")
    public String dashboard(
    		@ModelAttribute("userProfileToAdd") UserProfile userProfileToAdd,
    	    @ModelAttribute("userProfileToPopulate") UserProfile userProfile,
    		@ModelAttribute("chatThread") ChatThread chatThread,
    		@ModelAttribute("newMessage") Message message,
    		Model model,
    		HttpSession session
    		) {
    	
        if (!isAuthenticated(session)) {
            return "redirect:/";
        }

        // Obtain userID from session
        Long userId = (Long) session.getAttribute("userId");
        
        // If there is no userId then redirect to index.jsp
        if (userId == null) {
            return "redirect:/";
        }
        // Get the user object by calling userService.getUserById(userId) and insert into model
        User user = userService.findUser(userId);
        model.addAttribute("user", user);
        
        // get user object
		User userToAssociate = userService.findUser(userId);
		
        // Fetch user profile and add it to the model if it's present
    	UserProfile userProfileToPopulate = userProfileService.getUserProfileByUserId(userId);
    	System.out.println("This is the user profile: " + userProfileToPopulate);
    	model.addAttribute("userProfileToPopulate", userProfileToPopulate);
        
        // Get todays date and insert into model
        String todayDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        model.addAttribute("todaydate", todayDate);
        
        // Get list of chatThreads to populate associated with the user
        List<ChatThread> userAssociatedChatThreads = userToAssociate.getChatThreads();
        model.addAttribute("userAssociatedChatThreads", userAssociatedChatThreads);
        
        // Get the selected chat thread ID from the session
        //Long selectedChatThreadId = session.getAttribute("selectedChatThreadId");
        
        // Print all session attributes
        Enumeration<String> attributeNames = session.getAttributeNames();
        while (attributeNames.hasMoreElements()) {
            String attributeName = attributeNames.nextElement();
            System.out.println("Session attribute: " + attributeName + " = " + session.getAttribute(attributeName));
        }
        
        // Get the selected chat thread ID from the session as an Object
        Object selectedChatThreadIdObj = session.getAttribute("selectedChatThreadId");
        Long selectedChatThreadId;
        ChatThread selectedChatThread;

        if (selectedChatThreadIdObj != null) {

            // Cast session object to Long
            selectedChatThreadId = (Long) selectedChatThreadIdObj;
                
            // Get the appropriate chat thread based on the selectedChatThreadId stored in the session
            selectedChatThread = chatThreadService.findChatThread(selectedChatThreadId);
        } else {
            // Set the selectedChatThreadId to 1 and update the session attribute
        	selectedChatThreadId = 1L;
			session.setAttribute("selectedChatThreadId", selectedChatThreadId);
            
            // Get the appropriate chat thread based on the selectedChatThreadId (1L) and add it to the model attribute
            selectedChatThread = chatThreadService.findChatThread(selectedChatThreadId);
        }

        // Get the appropriate chat thread based on the selectedChatThreadId stored in the session and add to model attribute
        //ChatThread selectedChatThread = chatThreadService.findChatThread(selectedChatThreadId);

        if (selectedChatThread != null) {
            System.out.println("This is the selected ChatThread Id: " + selectedChatThread.getId());
            System.out.println("This is the selected ChatThread Messages (raw): " + selectedChatThread.getMessages());
        } else {
            System.out.println("The selected chat thread is null.");
        }
        
        // Add error handling here
        System.out.println("This is the selected ChatThread Id: " + selectedChatThread.getId());
        System.out.println("This is the selected ChatThread Messages (raw): " + selectedChatThread.getMessages());
        model.addAttribute("selectedChatThread", selectedChatThread);

        // Instantiate a list for selectedChatThreadMessages as null
        List<Message> selectedChatThreadMessages = null;

        // If the selectedChatThread is not null, meaning there is a selectedChatThread in database
        if (selectedChatThread != null) {
            // Get the list of selectedChatThreadMessages
            selectedChatThreadMessages = selectedChatThread.getMessages();
        }
        // Add error handling here
        System.out.println("This is the selected ChatThread Messages (fetched): " + selectedChatThreadMessages);
        model.addAttribute("selectedChatThreadMessages", selectedChatThreadMessages);


        // Add the theme preference to the session if it doesn't exist
        if (session.getAttribute("theme") == null) {
            session.setAttribute("theme", "colorTheme1");
        }
        
        // Add the theme to the model
        model.addAttribute("theme", session.getAttribute("theme"));
        
        
        return "dashboard.jsp";
    }
    
    @PostMapping("/toggleTheme")
    public String toggleTheme(HttpSession session) {
    	
    	if (!isAuthenticated(session)) {
            return "redirect:/";
        }
    	
        String currentTheme = (String) session.getAttribute("theme");
        
        System.out.println("This is the current theme: " + currentTheme);

        if (currentTheme.equals("colorTheme1")) {
            session.setAttribute("theme", "colorTheme2");
        } else {
            session.setAttribute("theme", "colorTheme1");
        }
        
        String newCurrentTheme = (String) session.getAttribute("theme");
        System.out.println("This is now the current theme: " + newCurrentTheme);

        return "redirect:/dashboard";
    }
    
}
