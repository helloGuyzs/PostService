package com.example.cloudsek.service;

import com.example.cloudsek.dto.addUserDto;
import com.example.cloudsek.dto.responseHandlerDto;
import com.example.cloudsek.dto.userLoginDto;
import com.example.cloudsek.exceptions.BadRequestException;
import com.example.cloudsek.exceptions.NotFoundException;
import com.example.cloudsek.exceptions.UnauthorizedException;
import com.example.cloudsek.model.userModel;
import com.example.cloudsek.repo.autheRepo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class authService {

    @Autowired
    private autheRepo authrepo;

    public userModel register(addUserDto req) {
        if (authrepo.findByUsername(req.getUsername()).isPresent()) {
            throw new BadRequestException("Username '" + req.getUsername() + "' is already taken.");
        }

        if (authrepo.findByEmail(req.getEmail()).isPresent()) {
            throw new BadRequestException("Email '" + req.getEmail() + "' is already registered.");
        }

        userModel newUser = new userModel();
        newUser.setUsername(req.getUsername());
        newUser.setPassword(req.getPassword());
        newUser.setEmail(req.getEmail());
        newUser.setFullName(req.getFullName());
        newUser.setPhone(req.getPhone());

        authrepo.save(newUser);

        return newUser;
    }

    public responseHandlerDto login(userLoginDto req, HttpServletRequest http) {
        String username = req.getUsername();
        String password = req.getPassword();

        Optional<userModel> optionalUser = authrepo.findByUsername(username);

        if (optionalUser.isEmpty()) {
            throw new NotFoundException("User with username '" + username + "' does not exist.");
        }

        userModel user = optionalUser.get();

        if (!user.getPassword().equals(password)) {
            throw new UnauthorizedException("Invalid password. Please try again.");
        }

        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(username, null, List.of());

        SecurityContextHolder.getContext().setAuthentication(token);

        HttpSession session = http.getSession(true);
        session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());
        session.setAttribute("user", user);

        responseHandlerDto res = new responseHandlerDto();
        res.setMessage("User logged in successfully!");

        return res;
    }

    public responseHandlerDto logout(HttpServletRequest http) {
        HttpSession session = http.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        SecurityContextHolder.clearContext();

        responseHandlerDto res = new responseHandlerDto();
        res.setMessage("User logged out successfully!");
        return res;
    }

    public userModel getCurrentUser(HttpServletRequest request) {
        Object userObj = request.getSession().getAttribute("user");
        if (userObj == null || !(userObj instanceof userModel)) {
            throw new UnauthorizedException("User is not logged in. Please log in first.");
        }

        return (userModel) userObj;
    }

    public Integer getCurrentUserId(HttpServletRequest request) {
        userModel user = getCurrentUser(request);
        return user.getId();
    }
}
