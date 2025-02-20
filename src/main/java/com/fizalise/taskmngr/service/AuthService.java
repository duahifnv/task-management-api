package com.fizalise.taskmngr.service;

import com.fizalise.taskmngr.dto.AuthenticationRequest;
import com.fizalise.taskmngr.dto.JwtResponse;
import com.fizalise.taskmngr.dto.RegistrationRequest;
import com.fizalise.taskmngr.entity.Role;
import com.fizalise.taskmngr.entity.User;
import com.fizalise.taskmngr.exception.CustomBadCredentialsException;
import com.fizalise.taskmngr.exception.UserNotFoundException;
import com.fizalise.taskmngr.mapper.UserMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public record AuthService(JwtService jwtService,
                          UserService userService,
                          UserMapper userMapper,
                          AuthenticationManager authenticationManager) {
    public JwtResponse registerNewUser(RegistrationRequest registrationRequest) {
        User user = userService.create(
                userMapper.toUser(registrationRequest, Role.ROLE_USER)
        );
        return new JwtResponse(
                jwtService.generateToken(user)
        );
    }
    public JwtResponse authenticate(AuthenticationRequest authenticationRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authenticationRequest.email(),
                            authenticationRequest.password()
                    )
            );
            return new JwtResponse(
                    jwtService.generateToken(
                            userService().getUserByEmail(authenticationRequest.email())
                    )
            );
        } catch (BadCredentialsException e) {
            throw new CustomBadCredentialsException();
        } catch (InternalAuthenticationServiceException e) {
            throw new UserNotFoundException();
        }
    }
}
