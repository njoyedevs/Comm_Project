package com.njoye.comm.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.njoye.comm.models.ChatThread;
import com.njoye.comm.models.User;
import com.njoye.comm.repositories.ChatThreadRepository;

@Service
public class ChatThreadService {

    @Autowired
    private ChatThreadRepository chatThreadRepository;

    public List<ChatThread> allChatThreads() {
        return chatThreadRepository.findAll();
    }

    public ChatThread createChatThread(ChatThread p) {
        return chatThreadRepository.save(p);
    }

    public ChatThread findChatThread(Long id) {
        Optional<ChatThread> optionalChatThread = chatThreadRepository.findById(id);
        return optionalChatThread.orElse(null);
    }

    public ChatThread updateChatThread(ChatThread p) {
        return chatThreadRepository.save(p);
    }

    public void deleteChatThread(Long id) {
    	chatThreadRepository.deleteById(id);
    }
    
    public List<ChatThread> getChatThreadsByUserId(Long userId) {
        return chatThreadRepository.findByCreator_Id(userId);
    }
    
    public ChatThread findFirstChatThread() {
        return chatThreadRepository.findFirstByOrderByIdAsc();
    }
    
    public List<User> getFirstChatThreadParticipants() {
        ChatThread firstChatThread = findFirstChatThread();
        return firstChatThread.getParticipants();
    }
    
    public ChatThread findByChatThreadName(String name) {
        return chatThreadRepository.findByNameContainingIgnoreCase(name);
    }
}

