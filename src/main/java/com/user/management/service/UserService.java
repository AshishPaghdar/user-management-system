package com.user.management.service;

import com.user.management.DTO.JwtAuthenticationResponse;
import com.user.management.DTO.SigninRequest;
import com.user.management.entity.User;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

public interface UserService {
   Optional<User> finbyId(Long id, Locale locale);
   List<User> getAllUser();
   String registration(User user, Locale locale);
   //User updateUser(User user, Locale locale) throws BadRequestException;
   void deleteUser(Long userId, Locale locale);
   JwtAuthenticationResponse login(SigninRequest request, Locale locale);

}
