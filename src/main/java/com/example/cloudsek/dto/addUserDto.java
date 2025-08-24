package com.example.cloudsek.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class addUserDto {

    @Email(message = "Email is mandatory")
    private  String email;

    @NotBlank(message = "Full name is mandatory")
    private  String fullName ;

    @NotBlank(message = "Username is mandatory")
    private String username ;

    @NotBlank(message = "Password is mandatory")
    private String password ;

    @NotBlank(message = "Phone number is mandatory")
    private  String phone ;

}
