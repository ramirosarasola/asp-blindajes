package com.example.aspblindajes.controller;

import com.example.aspblindajes.exception.ResourceNotFoundException;
import com.example.aspblindajes.model.Brand;
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

    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers() throws ResourceNotFoundException{
        List<User> userList = userService.findAllUsers();
        return ResponseEntity.ok(userList);
    }

    @GetMapping
    public ResponseEntity<User> findUserById(@RequestParam(value = "id") Long id ) throws ResourceNotFoundException{
        return ResponseEntity.ok(userService.findUserById(id));
    }

    @PutMapping("/update")
    public ResponseEntity<User> updateUserById(@RequestBody User user) throws ResourceNotFoundException{
        return ResponseEntity.ok(userService.updateUser(user));
    }

    @DeleteMapping
    public ResponseEntity<String> deleteUserById(@RequestParam(value = "id") Long id) throws ResourceNotFoundException {
        userService.deleteUserById(id);
        return ResponseEntity.ok("The user with id " + id + " has been deleted");
    }

}
