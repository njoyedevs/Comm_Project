package com.njoye.comm.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.njoye.comm.models.Message;

@Repository
public interface MessageRepository extends CrudRepository<Message, Long> {
    
	List<Message> findAll();
	
    List<Message> findByChatThreadId(Long chatThreadId);
    
}
