package com.njoye.comm.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.njoye.comm.models.Contact;

@Repository
public interface ContactRepository extends CrudRepository<Contact, Long> {
    
	List<Contact> findAll();
	
	List<Contact> findByRequestedContacts_Id(Long userId);
    
    List<Contact> findByAcceptedContacts_Id(Long userId);
    
    List<Contact> findByRequestedContacts_Email(String email);

    List<Contact> findByAcceptedContacts_Email(String email);
    
}
