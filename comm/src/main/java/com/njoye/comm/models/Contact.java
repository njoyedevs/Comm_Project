package com.njoye.comm.models;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

@Entity
@Table(name="contacts")
public class Contact {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(updatable=false)
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "requesterContact_id")
    private User requestedContacts;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "acceptedContact_id")
    private User acceptedContacts;
    
    private Boolean accepted;
    
    public Contact() {
        
    }
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	public User getRequestedContacts() {
		return requestedContacts;
	}

	public void setRequestedContacts(User requestedContacts) {
		this.requestedContacts = requestedContacts;
	}

	public User getAcceptedContacts() {
		return acceptedContacts;
	}

	public void setAcceptedContacts(User acceptedContacts) {
		this.acceptedContacts = acceptedContacts;
	}

	public Boolean getAccepted() {
		return accepted;
	}

	public void setAccepted(Boolean accepted) {
		this.accepted = accepted;
	}

	@PrePersist
	protected void onCreate() {
	    this.createdAt = LocalDateTime.now();
	}

	@PreUpdate
	protected void onUpdate() {
	    this.updatedAt = LocalDateTime.now();
	}

}