package com.example.aspblindajes.service;

import com.example.aspblindajes.exception.ResourceNotFoundException;
import com.example.aspblindajes.model.User;

import java.util.List;

public interface UserService {
    List<User> findAllUsers() throws ResourceNotFoundException;
    User findUserById(Long id) throws ResourceNotFoundException;
    void deleteUserById (Long id) throws ResourceNotFoundException;
    User updateUser(User user) throws ResourceNotFoundException;
}
