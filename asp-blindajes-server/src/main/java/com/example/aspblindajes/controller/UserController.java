package com.example.aspblindajes.controller;

import com.example.aspblindajes.exception.ResourceNotFoundException;
import com.example.aspblindajes.model.User;
import com.example.aspblindajes.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;


    @GetMapping
    public ResponseEntity<User> findUserByUsername (@RequestParam (value = "username")String username) throws ResourceNotFoundException {
       return ResponseEntity.ok(userService.findUserByUserName(username));
    }
    @GetMapping ("/all")
    public ResponseEntity<List<User>> findAllUser () throws ResourceNotFoundException {
        return ResponseEntity.ok(userService.findAllUsers());
    }
    @PutMapping
    public ResponseEntity<User> updateUser (@RequestBody User user) throws ResourceNotFoundException{
        return ResponseEntity.ok(userService.updateUser(user));

    }
    @DeleteMapping
    public ResponseEntity<String> deleteUserById (@RequestParam (value = "id") Long id) throws ResourceNotFoundException {
        userService.deleteUserById(id);
        return ResponseEntity.ok("The user with id " + id + "has been deleted succesfully");
    }
}
