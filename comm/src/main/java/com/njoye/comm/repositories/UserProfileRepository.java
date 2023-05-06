package com.njoye.comm.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.njoye.comm.models.UserProfile;

@Repository
public interface UserProfileRepository extends CrudRepository<UserProfile, Long> {
    
	List<UserProfile> findAll();
	
	UserProfile findByUser_Id(Long userId);
	
    Optional<UserProfile> findByUser_Email(String email);
    
}
