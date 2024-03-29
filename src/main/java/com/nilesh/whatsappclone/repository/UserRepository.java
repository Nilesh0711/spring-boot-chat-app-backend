package com.nilesh.whatsappclone.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nilesh.whatsappclone.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    public User findByEmail(String email);

    @Query("SELECT u from User u where u.full_name like %:query% or u.email like %:query%")
    List<User> searchUsers(@Param("query") String query);

}
