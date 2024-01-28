package com.example.security2.repository;

import com.example.security2.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    // findBy규칙 -> Username문법
    // select * from user where username = 1?
    public User findByUsername(String username);
}
