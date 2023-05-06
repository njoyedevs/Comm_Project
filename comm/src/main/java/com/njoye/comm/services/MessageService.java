package com.njoye.comm.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.njoye.comm.models.Message;
import com.njoye.comm.repositories.MessageRepository;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    public List<Message> allMessages() {
        return messageRepository.findAll();
    }

    public Message createMessage(Message p) {
        return messageRepository.save(p);
    }

    public Message findMessage(Long id) {
        Optional<Message> optionalMessage = messageRepository.findById(id);
        return optionalMessage.orElse(null);
    }

    public Message updateMessage(Message p) {
        return messageRepository.save(p);
    }

    public void deleteMessage(Long id) {
    	messageRepository.deleteById(id);
    }
    
    public List<Message> getMessagesByChatThreadId(Long chatThreadId) {
        return messageRepository.findByChatThreadId(chatThreadId);
    }
	
}
