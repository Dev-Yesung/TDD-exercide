package com.example.tddexercise.ch07;

public interface UserRepository {
    void save(User user);
    User findById(String id);
}
