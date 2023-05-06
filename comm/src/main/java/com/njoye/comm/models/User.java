package com.njoye.comm.models;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;


@Entity
@Table(name="users")
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotEmpty(message="Username is required!")
    @Size(min=3, max=30, message="Username must be between 3 and 30 characters")
    @Column(nullable = false, unique = true)
    private String userName;
    
    @NotEmpty(message="Email is required!")
    @Email(message="Please enter a valid email!")
    @Size(min=3, max=30, message="Email must be between 3 and 30 characters")
    @Column(nullable = false, unique = true)
    private String email;
    
    @NotEmpty(message="Password is required!")
    @Size(min=8, max=128, message="Password must be between 8 and 128 characters")
    private String password;
    
    @Transient
    @NotEmpty(message="Confirm Password is required!")
    @Size(min=8, max=128, message="Confirm Password must be between 8 and 128 characters")
    private String confirm;
    
    @Column(updatable=false)
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date createdAt;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date updatedAt;
    
    @OneToOne(mappedBy="user", cascade=CascadeType.ALL, fetch=FetchType.LAZY)
    private UserProfile userProfile;
    
    @OneToMany(mappedBy="requestedContacts", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Contact> requestedContacts;
    
    @OneToMany(mappedBy="acceptedContacts", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Contact> acceptedContacts;
    
    @OneToMany(mappedBy="creator", fetch = FetchType.LAZY)
    private List<ChatThread> chatThreadCreators;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "user_chatThreads", 
        joinColumns = @JoinColumn(name = "user_id"), 
        inverseJoinColumns = @JoinColumn(name = "chatThread_id")
    )
    private List<ChatThread> chatThreads;
    
    @OneToMany(mappedBy="messageCreator", fetch=FetchType.LAZY)
    private List<Message> messages;
    
    public User() {
    	
    }
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirm() {
		return confirm;
	}

	public void setConfirm(String confirm) {
		this.confirm = confirm;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public UserProfile getUserProfile() {
		return userProfile;
	}

	public void setUserProfile(UserProfile userProfile) {
		this.userProfile = userProfile;
	}

	public List<Contact> getRequestedContacts() {
		return requestedContacts;
	}

	public void setRequestedContacts(List<Contact> requestedContacts) {
		this.requestedContacts = requestedContacts;
	}

	public List<Contact> getAcceptedContacts() {
		return acceptedContacts;
	}

	public void setAcceptedContacts(List<Contact> acceptedContacts) {
		this.acceptedContacts = acceptedContacts;
	}

	public List<ChatThread> getChatThreadCreators() {
		return chatThreadCreators;
	}

	public void setChatThreadCreators(List<ChatThread> chatThreadCreators) {
		this.chatThreadCreators = chatThreadCreators;
	}

	public List<ChatThread> getChatThreads() {
		return chatThreads;
	}

	public void setChatThreads(List<ChatThread> chatThreads) {
		this.chatThreads = chatThreads;
	}

	public List<Message> getMessages() {
		return messages;
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}

	@PrePersist
    protected void onCreate(){
        this.createdAt = new Date();
    }
    @PreUpdate
    protected void onUpdate(){
        this.updatedAt = new Date();
    }
    
}
