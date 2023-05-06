package com.njoye.comm.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.njoye.comm.models.User;

// The primary key type is the Long in the public line

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    
	List<User> findAll();
	
    Optional<User> findByEmail(String email);
    
    List<User> findByEmailContainingIgnoreCase(String email);
    
}
