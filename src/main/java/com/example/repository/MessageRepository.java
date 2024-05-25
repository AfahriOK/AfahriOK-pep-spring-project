package com.example.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.entity.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {

    /**
     * Finds all messages based on the postedBy column
     * @param postedBy the id number of the postedBy column
     * @return a list of messages from the specified postedBy id
     */
    List<Message> findByPostedBy(Integer postedBy);
}
