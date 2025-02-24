package com.fizalise.taskmngr.service;

import com.fizalise.taskmngr.entity.User;
import com.fizalise.taskmngr.exception.UserAlreadyExistsException;
import com.fizalise.taskmngr.exception.UserNotFoundException;
import com.fizalise.taskmngr.repository.UserRepository;
import com.fizalise.taskmngr.repository.sort.UserSort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j(topic = "Сервис пользователей")
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    public List<User> findAll() {
        return userRepository.findAll(UserSort.NAME_ASC.getSort());
    }
    public User findById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id.toString()));
    }
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));
    }
    public UserDetailsService getUserDetailsService() {
        return this::findByEmail;
    }
    @Transactional
    public User saveUser(User user) {
        User savedUser = userRepository.save(user);
        log.info("Пользователь {} сохранен в базу данных", savedUser);
        return savedUser;
    }
    @Transactional
    public User createUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new UserAlreadyExistsException("Пользователь с такой почтой уже существует");
        }
        return saveUser(user);
    }
}
