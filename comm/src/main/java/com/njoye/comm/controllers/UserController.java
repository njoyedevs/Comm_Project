package com.njoye.comm.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.njoye.comm.models.User;
import com.njoye.comm.services.ContactService;

import jakarta.servlet.http.HttpSession;

public class UserController {
	
	@Autowired
    private ContactService contactService;
	
	// Add this method to check if the user is logged in
    private boolean isAuthenticated(HttpSession session) {
        return session.getAttribute("userId") != null;
    }

    @GetMapping("/searchUsers")
    public ResponseEntity<List<User>> searchUsersByEmail(
            @RequestParam String email,
            HttpSession session
            ) {

        // Check if the user is authenticated
        if (!isAuthenticated(session)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        
        List<User> users = contactService.searchUsersByEmail(email);
        return ResponseEntity.ok(users);
    }
	
}
