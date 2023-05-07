package com.njoye.comm.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.njoye.comm.models.User;
import com.njoye.comm.models.UserProfile;
import com.njoye.comm.services.UserProfileService;
import com.njoye.comm.services.UserService;
import com.njoye.comm.utils.FileUploadUtil;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class UserProfileController {
	
	@Autowired
    private UserProfileService userProfileService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
    private Environment env;
	
	@Value("${file.upload-dir}")
	private String fileUploadDir;
	
	// Add this method to check if the user is logged in
    private boolean isAuthenticated(HttpSession session) {
        return session.getAttribute("userId") != null;
    }
    
    // Add this method to check if the user has editing permissions
    private boolean hasEditPermission(Long userId, UserProfile userProfile) {
    	
    	System.out.println("This is the userId: " + userId);
    	System.out.println("This is the userProfile Id: " + userProfile.getUser().getId());
    	
    	if (userId != userProfile.getUser().getId()) {
    		return false;
    	}
    	
    	return true; 
    }
    

	@PostMapping("/userProfile/new")
	public String processNewUserProfile(
			@Valid @ModelAttribute("userProfileToAdd") UserProfile userProfile,
			BindingResult result,
			HttpSession session) {
		
		if (!isAuthenticated(session)) {
            return "redirect:/";
        }
		
		System.out.println(result);
		
		// Get userId and then user object to associate/set the user column in UserProfile 
		Long userId = (Long) session.getAttribute("userId");
		User userToAssociate = userService.findUser(userId);
		
		if(result.hasErrors()) {
			return "dashboard.jsp";
		} else {
			userProfile.setUser(userToAssociate);
			userProfileService.createUserProfile(userProfile);
			return "redirect:/dashboard";
		}
	}
	
	@PutMapping("/userProfile/{userId}/edit")
	public String processUpdateUserProfile(
			@PathVariable("userId") Long userId,
			@Valid @ModelAttribute("userProfileToUpdate") UserProfile userProfile,
			BindingResult result,
			HttpSession session,
			Model model,
			RedirectAttributes redirectAttributes) {
		
		System.out.println(result);
		
		if(result.hasErrors()) {
			
	        if (!isAuthenticated(session)) {
	            return "redirect:/";
	        }
	        
	        System.out.println("The edit profile had errors");

	        // Get the user object by calling userService.getUserById(userId) and add to model
	        User user = userService.findUser(userId);
	        model.addAttribute("user", user);
	        
	        // Get todays date and add to model
	        String todayDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
	        model.addAttribute("todaydate", todayDate);
	        
	        // Fetch user profile and add it to the model if it's present
	    	UserProfile userProfileToPopulate = userProfileService.getUserProfileByUserId(userId);
	    	System.out.println("This is the user profile: " + userProfileToPopulate);
	    	model.addAttribute("userProfileToPopulate", userProfileToPopulate);
	        
	        // Add BindingResult errors to redirectAttributes so the errors will persist accross refresh and populate in form
	        redirectAttributes.addFlashAttribute("errors", result.getAllErrors());
			
	        // redirect after adding errors into redirect attributes
//			return "dashboard.jsp";
			return "redirect:/dashboard";
			
		} else {
			System.out.println("Got to the Else Statement");
		
			UserProfile userProfileToUpdate = userProfileService.getUserProfileByUserId(userId);
			
			// Check whether logged in user is the same users as Book creator
			//if (!hasEditPermission(userId, userProfileToUpdate)) {
			//	return "redirect:/dashboard";
			//}
			
	        if (userProfileToUpdate != null) {
	        	userProfileToUpdate.setFirstName(userProfile.getFirstName());
	            userProfileToUpdate.setLastName(userProfile.getLastName());
	            userProfileToUpdate.setPhoneNumber(userProfile.getPhoneNumber());
	            userProfileToUpdate.setDateOfBirth(userProfile.getDateOfBirth());
	            userProfileToUpdate.setState(userProfile.getState());
	            userProfileToUpdate.setCountry(userProfile.getCountry());
	            userProfileService.updateUserProfile(userProfileToUpdate);
	        }
	        
			return "redirect:/dashboard";
		}
	}
	
	@PostMapping("userProfile/new/imageUpload")
    public String saveUserProfileImage(
            @RequestParam("uploadImage") MultipartFile multipartFile,
            HttpSession session,
            RedirectAttributes redirectAttributes
            ) throws IOException {
         
		
		if (multipartFile.isEmpty()) {
			// Handle the case when there's no user profile associated with the user
    	    System.out.println("No image was selected for upload");
    	    redirectAttributes.addFlashAttribute("alertMessage", "Please select an image first.");
  	    
    	    return "redirect:/dashboard";
		}
		
		// Get userId and then user object 
		Long userId = (Long) session.getAttribute("userId");
		User userToAssociate = userService.findUser(userId); 
		
		// Get fileName of multipartFile
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        System.out.println("This is the filename: " + fileName);
        
        // Fetch user profile and add it to the model if it's present
    	UserProfile userProfileToPopulate = userProfileService.getUserProfileByUserId(userId);
    	System.out.println("This is the user profile: " + userProfileToPopulate);
        
    	if (userProfileToPopulate != null) {
	        //String uploadDir = "src/main/resources/static/images/" + userToAssociate.getId();
	        //String baseUploadDir = env.getProperty("spring.servlet.multipart.location");
	        //String uploadDir = baseUploadDir + userToAssociate.getId();
    		String uploadDir = fileUploadDir + userToAssociate.getId();
	        System.out.println("This is the uploadDir " + uploadDir);
	        
	        // If userProfile avatar is not null and empty then generate filePath, and delete the file.
	        // This will help keep database space low
	        if (userProfileToPopulate.getAvatar() != null && !userProfileToPopulate.getAvatar().isEmpty()) {
	        	// Delete file here for 
	            System.out.println("Avatar already exists. Deleting old file so new one can be saved.");

	            Path filePath = Paths.get(uploadDir, userProfileToPopulate.getAvatar());
	            System.out.println("This is the filePath: " + filePath);
	            
	            // START HERE!!! File not being deleted because it does not exist and then the chain after that of saving is not working and then retrieving.
	            Files.delete(filePath); 
	        }
	        
	        // Set the userProfile avatar to the fileName
	        userProfileToPopulate.setAvatar(fileName);
	        
	        // Update the user profile userUpdateProfile from userProfileService
	        userProfileService.updateUserProfile(userProfileToPopulate);
	 
	        // Save file with the uploadDir, fileName, and image multipartFile
	        FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
	        
    	} else {
    	    // when there is no userProfile created, redirect with an alertMessage
    	    System.out.println("User profile not found for user ID: " + userId);
    	    redirectAttributes.addFlashAttribute("alertMessage", "Please create a profile first.");
  	    
    	    return "redirect:/dashboard";
    	}
    	
        return "redirect:/dashboard";
    }
	

}
