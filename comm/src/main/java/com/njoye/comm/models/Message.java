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
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Entity
@Table(name="messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotEmpty(message = "Message cannot be blank")
    @Size(min=1, max=500, message="ChatThread name must be between 1 and 500 characters")
    @Column(nullable = false)
    private String message;
    
    @Column(nullable = true, length = 200)
    private String mediaUrl;

    @Column(updatable=false)
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "messageCreator_id")
    private User messageCreator;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="chatThread_id")
    private ChatThread chatThread;

    public Message() {
        
    }
    
    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", message='" + message + '\'' +
                ", mediaUrl=" + mediaUrl + '\'' +
                ", chatThreadId=" + chatThread + '\'' +
                ", messageCreator='" + messageCreator + '\'' +
                '}';
    }
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMediaUrl() {
		return mediaUrl;
	}

	public void setMediaUrl(String mediaUrl) {
		this.mediaUrl = mediaUrl;
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

	public ChatThread getChatThread() {
		return chatThread;
	}

	public void setChatThread(ChatThread chatThread) {
		this.chatThread = chatThread;
	}

	public User getMessageCreator() {
		return messageCreator;
	}

	public void setMessageCreator(User messageCreator) {
		this.messageCreator = messageCreator;
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
