package com.fizalise.taskmngr.mapper;

import com.fizalise.taskmngr.dto.RegistrationRequest;
import com.fizalise.taskmngr.dto.user.UserResponse;
import com.fizalise.taskmngr.entity.Role;
import com.fizalise.taskmngr.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring", imports = UUID.class)
public abstract class UserMapper {
    @Autowired
    protected PasswordEncoder passwordEncoder;
    @Mapping(target = "userId", expression = "java(UUID.randomUUID())")
    @Mapping(target = "password", qualifiedByName = "getEncodedPassword")
    public abstract User toUser(RegistrationRequest registrationRequest, Role role);
    public abstract UserResponse toResponse(User user);
    public abstract List<UserResponse> toResponses(Page<User> users);
    @Named("getEncodedPassword")
    protected String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }
}
