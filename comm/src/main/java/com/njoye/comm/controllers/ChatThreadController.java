package com.njoye.comm.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.njoye.comm.dto.MessageDTO;
import com.njoye.comm.models.ChatThread;
import com.njoye.comm.models.Message;
import com.njoye.comm.models.User;
import com.njoye.comm.models.UserProfile;
import com.njoye.comm.services.ChatThreadService;
import com.njoye.comm.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class ChatThreadController {
	
	@Autowired
    private ChatThreadService chatThreadService;
	
	@Autowired
    private UserService userService;
	
    // Add this method to check if the user is logged in
    private boolean isAuthenticated(HttpSession session) {
        return session.getAttribute("userId") != null;
    }
	
    // Add this method to check if the user has editing permissions
    private boolean hasEditPermission(Long userId, ChatThread chatThread) {
    	
    	System.out.println("This is the userId: " + userId);
    	System.out.println("This is the chatThreadId: " + chatThread.getCreator().getId());
    	
    	if (userId != chatThread.getCreator().getId()) {
    		return false;
    	}
    	
    	return true; 
    }

	@GetMapping("/firstChatThreadParticipants")
	public String firstChatThreadParticipants(
			Model model,
			HttpSession session) {
		
		
		if (!isAuthenticated(session)) {
            return "redirect:/";
        }
		
	    List<User> participants = chatThreadService.getFirstChatThreadParticipants();
	    model.addAttribute("participants", participants);
	    return "firstChatThreadParticipants.jsp";
	}
	
	@GetMapping("/chatThreads/{chatThreadId}/participants")
	public ResponseEntity<List<String>> getChatThreadParticipants(
			@PathVariable Long chatThreadId
			) {
		
	    ChatThread chatThread = chatThreadService.findChatThread(chatThreadId);
	    
	    // if chatThread is null, meaning it does not exist, 
	    // return response notFound status to chatThread.js AJAX promise response
	    if (chatThread == null) {
	        return ResponseEntity.notFound().build();
	    }
	    
	    List<String> participantUsernames = chatThread.getParticipants()
	            .stream().map(User::getUserName).collect(Collectors.toList());
	    return ResponseEntity.ok(participantUsernames);
	}
	
	@PostMapping("/chatThreads/new")
	public String processNewChatThread(
			@Valid @ModelAttribute("chatThread") ChatThread chatThread,
			BindingResult result,
			HttpSession session) {
		
		if (!isAuthenticated(session)) {
            return "redirect:/";
        }
		
		System.out.println(result);
		
		// Get userId and then user object to associate/set the creator column in ChatThread 
		Long userId = (Long) session.getAttribute("userId");
		User userToAssociate = userService.findUser(userId);
		
		if(result.hasErrors()) {
			return "dashboard.jsp";
		} else {
			
			// Set userToAssociate as creator
			chatThread.setCreator(userToAssociate);
			
			// initialize list of participants for main chatThread 
			List<User> participants = new ArrayList<>();
		   
			// add user to participants
			participants.add(userToAssociate);
		   
			// set newChatThread participants with new participant list
			chatThread.setParticipants(participants);
			
			// Create Chat Thread
			chatThreadService.createChatThread(chatThread);
		
			return "redirect:/dashboard";
		}
	}
	
	@GetMapping("/chatThreads/{chatThreadId}/messages")
	@ResponseBody
	public ResponseEntity<List<MessageDTO>> getMessagesForChatThread(
			@PathVariable("chatThreadId") Long chatThreadId,
			HttpSession session
			) {
		
		// Check if the user is authenticated
		if (!isAuthenticated(session)) {
	        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	    }
		
		// Add error handling
		System.out.println("This is the chatThreadId " + chatThreadId);
		
		// Get chatThread based on chatThreadId from @PathVariable
	    ChatThread chatThread = chatThreadService.findChatThread(chatThreadId);
	    System.out.println("This is the chatThreadObject " + chatThread);
	    
	    // if chatThread is null, meaning it does not exist, 
	    // return response notFound status to chatThread.js AJAX promise response
	    if (chatThread == null) {
	        return ResponseEntity.notFound().build();
	    }
        
	    List<Message> messages = chatThread.getMessages();
	    List<MessageDTO> messageDTOs = messages.stream().map(message -> {
	    	MessageDTO dto = new MessageDTO();
	        dto.setId(message.getId());
	        dto.setMessage(message.getMessage());
	        dto.setMediaUrl(message.getMediaUrl());
	        dto.setCreatedAt(message.getCreatedAt());
	        dto.setUpdatedAt(message.getUpdatedAt());
	        dto.setChatThreadId(message.getChatThread().getId());
	        dto.setMessageCreatorId(message.getMessageCreator().getId());
	        dto.setMessageCreatorUserName(message.getMessageCreator().getUserName());
	        if (message.getMessageCreator().getUserProfile() != null) {
	            dto.setMessageCreatorAvatar(message.getMessageCreator().getUserProfile().getAvatar());
	        } else {
	            dto.setMessageCreatorAvatar(""); // Or set a default value here "/images/testImage.png"
	        }

	        return dto;
	    }).collect(Collectors.toList());

	    return ResponseEntity.ok(messageDTOs);
	}
	
	@PostMapping("/setSelectedChatThreadId")
	@ResponseBody
	public ResponseEntity<String> setSelectedChatThreadId(
			HttpSession session,
			@RequestParam("chatThreadId") Long chatThreadId
			) {
		
		// Check if the user is authenticated
		if (!isAuthenticated(session)) {
	        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	    }
		
	    session.setAttribute("selectedChatThreadId", chatThreadId);
	    return ResponseEntity.ok("Selected chat thread ID updated in session.");
	}
	
	
	@PostMapping("/contacts/addParticipants")
	public String addParticipants(
			@RequestParam("emailToSearch") String emailToSearch,
			@RequestParam("chatThreadToSearch") String chatThreadToSearch,
			HttpSession session
			) {
		
		if (!isAuthenticated(session)) {
            return "redirect:/";
        }
		
		// Add error handling
		System.out.println("This is the chatThreadToSearch: " + chatThreadToSearch);
		System.out.println("This is the emailToSearch: " + emailToSearch);
		
		// Obtain userID from session
        Long userId = (Long) session.getAttribute("userId");
        User userToAssociate = userService.findUser(userId);
        
        // Get chatThread based on chatThreadId from @PathVariable
		ChatThread chatThread = chatThreadService.findByChatThreadName(chatThreadToSearch);
    	System.out.println("This is the chatThread object: " + chatThread);
    	
	    // Check whether logged in user is the same users as chatThread creator
		if (!hasEditPermission(userId, chatThread)) {
			return "redirect:/dashboard";
		}
		
    	// Get chatThreadId
    	Long chatThreadId = chatThread.getId();
    	
		// Get user based on emailToSearch from @RequestParam
		User participantToAdd = userService.findUserByEmail(emailToSearch);
		System.out.println("This is the participantToAdd object: " + participantToAdd);
		
		
        // No one can add participants from the public chat
 		if (chatThreadId != 1L) {
 			// Add error handling
     			
 			// The creator of the chatThread can add participants only
 			if (userId.equals(chatThread.getCreator().getId()))
        
			    // add user to participants
			    chatThread.getParticipants().add(participantToAdd);
			   
			    // save newChatThread to database
			    chatThreadService.updateChatThread(chatThread);
		    
 		}

	    return "redirect:/dashboard";
	}
	
	@PostMapping("/contacts/removeParticipants")
	public String removeParticipants(
			@RequestParam("emailToSearch") String emailToSearch,
			@RequestParam("chatThreadToSearch") String chatThreadToSearch,
			HttpSession session
			) {
		
		if (!isAuthenticated(session)) {
            return "redirect:/";
        }
		
		System.out.println("This is the chatThreadToSearch: " + chatThreadToSearch);
		System.out.println("This is the emailToSearch: " + emailToSearch);
		
		// Obtain userID from session
        Long userId = (Long) session.getAttribute("userId");
        User userToAssociate = userService.findUser(userId);
        
        // Get chatThread based on chatThreadId from @PathVariable
		ChatThread chatThread = chatThreadService.findByChatThreadName(chatThreadToSearch);
    	System.out.println("This is the chatThread object: " + chatThread);
    	
	    // Check whether logged in user is the same users as chatThread creator
		if (!hasEditPermission(userId, chatThread)) {
			return "redirect:/dashboard";
		}
    	
    	// Get chatThreadId
    	Long chatThreadId = chatThread.getId();
    	
		// Get user based on emailToSearch from @RequestParam
		User participantToAdd = userService.findUserByEmail(emailToSearch);
		System.out.println("This is the participantToAdd object: " + participantToAdd);
		
		// No one can remove participants from the public chat
		if (chatThreadId != 1L) {
			// Add error handling
			
			// The creator of the chatThread can remove participants only
			if (userId.equals(chatThread.getCreator().getId()))
				
			    // add user to participants
			    chatThread.getParticipants().remove(participantToAdd);
			   
			    // save newChatThread to database
			    chatThreadService.updateChatThread(chatThread);
		}
		
		return "redirect:/dashboard";
		
	}
}
