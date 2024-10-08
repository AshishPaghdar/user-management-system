package com.user.management.controller;

import com.user.management.entity.User;
import com.user.management.exception.ResourceNotFoundException;
import com.user.management.repository.UserRepository;
import com.user.management.service.UserServiceImpl;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

@Slf4j
@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    UserServiceImpl userService;

    @Autowired
    private MessageSource messageSource;

//    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/registration")
    public ResponseEntity<Object> registration(@Valid @RequestBody User user, @RequestHeader(name = "Accept-Language", required = false) Locale locale) {
        log.info("Processing user registration {}", user);
        try {
            return ResponseEntity.ok(userService.registration(user, locale));
        } catch (Exception e) {
            log.error("Error processing user registration for request: {}. Error: {}", user, e.getMessage(), e);
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUser() {
        List<User> users = userService.getAllUser();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

//    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") Long userId, @RequestHeader(name = "Accept-Language", required = false) Locale locale) {
        log.info("Deleting user with ID {}", userId);
        try {
            userService.deleteUser(userId, locale);
            log.info("Deleted user with ID {}", userId);
            return ResponseEntity.status(HttpStatus.OK).body(messageSource.getMessage("user.deleted.successfully.msg", null, locale) + " " + userId);
        } catch (ResourceNotFoundException ex) {
            log.error("User with ID {} not found for deletion", userId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(messageSource.getMessage("user.not.access.delete.user.msg", null, locale) + "" + userId);
        }
    }
}