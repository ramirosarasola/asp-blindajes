package com.example.aspblindajes.service;

import com.example.aspblindajes.exception.ResourceNotFoundException;
import com.example.aspblindajes.model.User;
import com.example.aspblindajes.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;


    @Override
    public User findUserByUserName(String username) throws ResourceNotFoundException {
        if (userRepository.findUserByUsername(username).isPresent()){
            return userRepository.findUserByUsername(username).get();
        }
        throw new ResourceNotFoundException("User not found");

    }

    @Override
    public List<User> findAllUsers() throws ResourceNotFoundException {
        if (userRepository.findAll().size() > 0){
            return userRepository.findAll();
        }
        throw new ResourceNotFoundException("There are no users in the database");
    }

    @Override
    public void deleteUserById(Long id) throws ResourceNotFoundException {
        if (userRepository.findById(id).isPresent()){
            userRepository.deleteById(id);
        }
        throw new ResourceNotFoundException("there is no user in the database with the provided id");
    }

    @Override
    public User updateUser(User user) throws ResourceNotFoundException {
        if (userRepository.findById(user.getId()).isPresent()){
            return userRepository.save(user);
        }
        throw new ResourceNotFoundException("The user you are trying to update doesnt exist");
    }

}
