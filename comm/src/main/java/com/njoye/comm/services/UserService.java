package com.njoye.comm.services;

import java.util.List;
import java.util.Optional;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.njoye.comm.models.LoginUser;
import com.njoye.comm.models.User;
import com.njoye.comm.repositories.UserRepository;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    // This method will be called from the controller
    // whenever a user submits a registration form.
    public User register(User newUser, BindingResult result) {
    	
    	// Locate user in database
    	Optional<User> potentialUser = userRepository.findByEmail(newUser.getEmail());
    	
    	// If in database then reject
    	if(potentialUser.isPresent()) {
    		result.rejectValue("email", "unique", "This email is already registered");
    		return null;
    	}
    	
    	// If form password does not match confirm password then reject
    	if(!newUser.getPassword().equals(newUser.getConfirm())) {
    		result.rejectValue("confirm", "matches", "This confirm password does not match the password");
    	}
    	
    	// Return null if the result has errors
    	if(result.hasErrors()) {
    		return null;
    	}
    	
    	// Hash, Salt, set password, and then save to database
    	String hashed = BCrypt.hashpw(newUser.getPassword(), BCrypt.gensalt());
    	newUser.setPassword(hashed);
    	return userRepository.save(newUser);
    }
    
    // This method will be called from the controller
    // whenever a user submits a login form.
    public User login(LoginUser newLogin, BindingResult result) {
    	
    	// Get Password
//    	String passwordEntered = newLoginObject.getPassword();
    	
    	// Locate user in database
    	Optional<User> potentialUser = userRepository.findByEmail(newLogin.getEmail());
    	
    	// If not in database then reject
    	if(!potentialUser.isPresent()) {
    		result.rejectValue("email", "unique", "This email is not registered");
    		return null;
    	}
    	
    	// Get user from optionalUser object
    	User user = potentialUser.get();
    	
    	// Get user password from form and check if it matches hashed password
    	if(!BCrypt.checkpw(newLogin.getPassword(), user.getPassword())) {
    		result.rejectValue("password", "matches", "The password is not right");
    	}
    	
    	// Return if there are errors
    	if(result.hasErrors()) {
    		return null;
    	}
    	
    	// return User object if successful
    	return user;
    	
    }
    
    public List<User> allUsers() {
        return userRepository.findAll();
    }

    public User findUser(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        return optionalUser.orElse(null);
    }
    
    public User findUserByEmail(String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        return optionalUser.orElse(null);
    }
}
