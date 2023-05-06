package com.njoye.comm.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.njoye.comm.models.UserProfile;
import com.njoye.comm.repositories.UserProfileRepository;

@Service
public class UserProfileService {

    @Autowired
    private UserProfileRepository userProfileRepository;

    public List<UserProfile> allUserProfiles() {
        return userProfileRepository.findAll();
    }

    public UserProfile createUserProfile(UserProfile p) {
        return userProfileRepository.save(p);
    }

    public UserProfile findUserProfile(Long id) {
        Optional<UserProfile> optionalUserProfile = userProfileRepository.findById(id);
        return optionalUserProfile.orElse(null);
    }

    public UserProfile updateUserProfile(UserProfile p) {
        return userProfileRepository.save(p);
    }

    public void deleteUserProfile(Long id) {
    	userProfileRepository.deleteById(id);
    }
    
    public UserProfile getUserProfileByUserId(Long userId) {
        return userProfileRepository.findByUser_Id(userId);
    }
    
    public Optional<UserProfile> getUserProfileByEmail(String email) {
        return userProfileRepository.findByUser_Email(email);
    }
	
}
