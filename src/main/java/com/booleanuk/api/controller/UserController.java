package com.booleanuk.api.controller;

import com.booleanuk.api.model.User;
import com.booleanuk.api.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("users")
public class UserController {
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") Integer id) {
        User user = this.userRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No user with that id was found")
        );
        return ResponseEntity.ok(user);
    }

    @PostMapping
    public ResponseEntity<User> createNewUser(@RequestBody User user) {
        return new ResponseEntity<User>(this.userRepository.save(user), HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<User> updateUser(@PathVariable int id, @RequestBody User user) {
        User userToUpdate = this.userRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No user with that id found")
        );
        userToUpdate.setEmail(user.getEmail());
        userToUpdate.setFirstName(user.getFirstName());
        userToUpdate.setLastName(user.getLastName());
        userToUpdate.setUsername(user.getUsername());
        userToUpdate.setPhone(user.getPhone());
        return new ResponseEntity<User>(this.userRepository.save(userToUpdate), HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<User> deleteUser(@PathVariable int id) {
        User userToDelete = this.userRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No user with that id found")
        );
        this.userRepository.delete(userToDelete);
        return ResponseEntity.ok(userToDelete);
    }
}
