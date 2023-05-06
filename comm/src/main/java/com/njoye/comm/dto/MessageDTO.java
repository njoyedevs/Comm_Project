package com.njoye.comm.dto;

import java.time.LocalDateTime;

public class MessageDTO {

	private Long id;
    private String message;
    private String mediaUrl;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long chatThreadId;
    private Long messageCreatorId;
    private String messageCreatorUserName;
    private String messageCreatorAvatar;

    // Constructors, getters, and setters

    public MessageDTO() {

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

	public Long getChatThreadId() {
		return chatThreadId;
	}

	public void setChatThreadId(Long chatThreadId) {
		this.chatThreadId = chatThreadId;
	}

	public Long getMessageCreatorId() {
		return messageCreatorId;
	}

	public void setMessageCreatorId(Long messageCreatorId) {
		this.messageCreatorId = messageCreatorId;
	}

	public String getMessageCreatorUserName() {
		return messageCreatorUserName;
	}

	public void setMessageCreatorUserName(String messageCreatorUserName) {
		this.messageCreatorUserName = messageCreatorUserName;
	}

	public String getMessageCreatorAvatar() {
		return messageCreatorAvatar;
	}

	public void setMessageCreatorAvatar(String messageCreatorAvatar) {
		this.messageCreatorAvatar = messageCreatorAvatar;
	}
}
