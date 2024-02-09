package com.nilesh.whatsappclone.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nilesh.whatsappclone.model.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {

    @Query("select m from Message m join m.chat c where c.id = :chatId")
    public List<Message> findByChatId(@Param("chatId") Integer chatId);

    @Query("select m from Message m join m.chat c where c.id = :chatId order by timestamp desc limit 1")
    public Message findLastMessageByChatId(@Param("chatId") Integer chatId);

}
