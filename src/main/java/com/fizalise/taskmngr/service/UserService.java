package com.fizalise.taskmngr.service;

import com.fizalise.taskmngr.entity.User;
import com.fizalise.taskmngr.exception.UserAlreadyExistsException;
import com.fizalise.taskmngr.exception.UserNotFoundException;
import com.fizalise.taskmngr.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j(topic = "Сервис пользователей")
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));
    }
    public UserDetailsService getUserDetailsService() {
        return this::getUserByEmail;
    }
    @Transactional
    public User save(User user) {
        User savedUser = userRepository.save(user);
        log.info("Пользователь {} сохранен в базу данных", savedUser);
        return savedUser;
    }
    @Transactional
    public User create(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new UserAlreadyExistsException("Пользователь с такой почтой уже существует");
        }
        return save(user);
    }
}
