package com.example.demo.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.user.User;


public interface UserRepository extends JpaRepository<User, Integer> {

}