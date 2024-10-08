package com.user.management.service;

import com.user.management.DTO.JwtAuthenticationResponse;
import com.user.management.DTO.SigninRequest;
import com.user.management.entity.Role;
import com.user.management.entity.User;
import com.user.management.exception.BadRequestException;
import com.user.management.exception.ResourceNotFoundException;
import com.user.management.repository.UserRepository;
import com.user.management.util.UserAuthenticationUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private MessageSource messageSource;

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }


    @Override
    public String registration(User user, Locale locale) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new BadRequestException(messageSource.getMessage("user.email.exist.msg", null, locale) + " " + user.getEmail() + messageSource.getMessage("user.change.email.msg", null, locale));
        }
        var userDetails = User.builder().name(user.getName()).email(user.getEmail()).password(passwordEncoder.encode(user.getPassword()))
                .email(user.getEmail()).gender(user.getGender()).mobile(user.getMobile())
                .role(user.getRole()).build();

        Optional.ofNullable(userRepository.save(userDetails))
                .orElseThrow(() -> new ResourceNotFoundException(messageSource.getMessage("user.registration.unsuccessful.msg", null, locale)));
        return messageSource.getMessage("user.registration.successful.msg", null, locale);
    }

    @Override
    public void deleteUser(Long userId, Locale locale) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(messageSource.getMessage("user.not.found.msg", null, locale) + " " + userId));
        userRepository.delete(user);
        log.info("Deleted the user with Id: " + user.getId());
    }

    @Override
    public JwtAuthenticationResponse login(SigninRequest request, Locale locale) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException(messageSource.getMessage("user.invalid.email.msg", null, locale)));
        Long userId = Long.parseLong(request.getId());
        if (user.getId() != userId || !user.getRole().name().equals(request.getRole())) {
            throw new IllegalArgumentException(messageSource.getMessage("user.invalid.credential.msg", null, locale));
        }
        var jwt = jwtService.generateToken(user);
        return JwtAuthenticationResponse.builder().token(jwt).build();
    }
}