package com.example.cloudsek.controller;


import com.example.cloudsek.dto.addUserDto;
import com.example.cloudsek.dto.responseHandlerDto;
import com.example.cloudsek.dto.userLoginDto;
import com.example.cloudsek.model.userModel;
import com.example.cloudsek.service.authService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
public class authController {


    @Autowired
    private authService authservice;

    @PostMapping("/register")
    public userModel register(@RequestBody @Valid addUserDto req){

        return authservice.register(req);
    }

    @PostMapping("/login")
    public responseHandlerDto login(@RequestBody @Valid userLoginDto req , HttpServletRequest http){


        return authservice.login(req , http);
    }

    @PostMapping("/logout")
    public responseHandlerDto logout(HttpServletRequest http){


        return  authservice.logout( http);
    }

    @GetMapping("/public")
    public String publicEndpoint() {
        return "This is a public endpoint accessible without authentication.";
    }

    @GetMapping("/health")
    public String healthCheck() {

        return "Post service is up and running!";
    }





}
