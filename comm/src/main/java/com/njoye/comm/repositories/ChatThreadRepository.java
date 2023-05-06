package com.njoye.comm.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.njoye.comm.models.ChatThread;

@Repository
public interface ChatThreadRepository extends CrudRepository<ChatThread, Long> {
    
	List<ChatThread> findAll();
	
    List<ChatThread> findByCreator_Id(Long userId);
    
    ChatThread findFirstByOrderByIdAsc();
    
    ChatThread findByNameContainingIgnoreCase(String name);
    
}
