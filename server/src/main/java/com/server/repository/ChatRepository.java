package com.server.repository;

import com.server.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {

    @Modifying
    @Query(nativeQuery = true, value = "Select * FROM chat where chat.id in (Select chat_id From user_chat where user_id = ?1)")
    List<Chat> findAllByUserId(Long id);
}