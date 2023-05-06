package com.njoye.comm.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.njoye.comm.models.Contact;
import com.njoye.comm.models.User;
import com.njoye.comm.repositories.ContactRepository;
import com.njoye.comm.repositories.UserRepository;

@Service
public class ContactService {

    @Autowired
    private ContactRepository contactRepository;
    
    @Autowired
    private UserRepository userRepository;

    public List<Contact> allContacts() {
        return contactRepository.findAll();
    }

    public Contact createContact(Contact p) {
        return contactRepository.save(p);
    }

    public Contact findContact(Long id) {
        Optional<Contact> optionalContact = contactRepository.findById(id);
        return optionalContact.orElse(null);
    }

    public Contact updateContact(Contact p) {
        return contactRepository.save(p);
    }

    public void deleteContact(Long id) {
    	contactRepository.deleteById(id);
    }
    
    public List<Contact> getRequestedContactsByUserId(Long userId) {
        return contactRepository.findByRequestedContacts_Id(userId);
    }
    
    public List<Contact> getAcceptedContactsByUserId(Long userId) {
        return contactRepository.findByAcceptedContacts_Id(userId);
    }
    
    public List<Contact> getContactsByRequesterEmail(String email) {
        return contactRepository.findByRequestedContacts_Email(email);
    }
    
    public List<Contact> getContactsByReceiverEmail(String email) {
        return contactRepository.findByAcceptedContacts_Email(email);
    }
    
    public List<User> searchUsersByEmail(String email) {
        return userRepository.findByEmailContainingIgnoreCase(email);
    }
	
}
