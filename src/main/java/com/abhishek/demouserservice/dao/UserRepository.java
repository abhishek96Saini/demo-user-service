package com.abhishek.demouserservice.dao;

import com.abhishek.demouserservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByName(String username);
}
