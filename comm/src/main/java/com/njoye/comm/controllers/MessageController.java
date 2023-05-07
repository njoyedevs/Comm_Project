package com.njoye.comm.controllers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.njoye.comm.models.ChatThread;
import com.njoye.comm.models.Message;
import com.njoye.comm.models.User;
import com.njoye.comm.services.ChatThreadService;
import com.njoye.comm.services.MessageService;
import com.njoye.comm.services.UserService;
import com.njoye.comm.utils.FileUploadUtil;

import jakarta.servlet.http.HttpSession;

@Controller
public class MessageController {
	
	@Autowired
    private MessageService messageService;
	
	@Autowired
    private UserService userService;
	@Autowired
    private ChatThreadService chatThreadService;
	
	@Value("${file.upload-dir}")
	private String fileUploadDir;
	
    // Add this method to check if the user is logged in
    private boolean isAuthenticated(HttpSession session) {
        return session.getAttribute("userId") != null;
    }

	@PostMapping("/messages/new")
	public String processNewMessage(
			@ModelAttribute("newMessage") Message message,
			@RequestParam("selectedChatThreadId") Long chatThreadId,
			@RequestParam(value = "media", required = false) MultipartFile media,
			HttpSession session,
			Model model) throws IOException {
		
		if (!isAuthenticated(session)) {
            return "redirect:/";
        }
		
		// Print the contents of the newMessage attribute
	    System.out.println("Contents of newMessage attribute: message - " + message.toString());
		
		// Get userId and then user object to associate/set the user column in message 
		Long userId = (Long) session.getAttribute("userId");
		User userToAssociate = userService.findUser(userId);
		
		// Perform validation
		boolean hasErrors = false;
		
		// Validate message content
		if (message.getMessage() == null || message.getMessage().trim().isEmpty()) {
			model.addAttribute("messageError", "Message content cannot be empty.");
			hasErrors = true;
		}
		
		if(!hasErrors) {
			
			// Check if there is an uploaded media file
	        if (media != null && !media.isEmpty()) {

	            //String uploadDir = "src/main/resources/static/images/" + userToAssociate.getId();
	            String uploadDir = fileUploadDir + userToAssociate.getId();
	            String fileName = StringUtils.cleanPath(media.getOriginalFilename());
	            FileUploadUtil.saveFile(uploadDir, fileName, media);

		        //String mediaUrl = "/images/" + userToAssociate.getId() + "/" + fileName;
	            String mediaUrl = "/uploads/" + userToAssociate.getId() + "/" + fileName;
		        message.setMediaUrl(mediaUrl);
	            
	        }

            System.out.println("This is the message controller");
			
			// Set message creator with userToAssociate via session
			message.setMessageCreator(userToAssociate);
			
			// Get the chatThread object using the RequestParam chatThreadId
			ChatThread chatThread = chatThreadService.findChatThread(chatThreadId);
	        
			// Set chatThead with chatThread from RequestParam
			message.setChatThread(chatThread);
			
			// create message using messageService.createMessage
			messageService.createMessage(message);
			
			// Store the selected chat thread ID in the session to persist current chat thread on page reload
	        session.setAttribute("selectedChatThreadId", chatThreadId);
	        
		}	
		
		return hasErrors ? "dashboard.jsp" : "redirect:/dashboard";
	}
}
