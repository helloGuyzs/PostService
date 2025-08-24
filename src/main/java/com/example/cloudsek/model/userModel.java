package com.example.cloudsek.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class userModel extends baseModel {


    @Column(unique = true, nullable = false)
    private String email;

    @Column( nullable = false)
    private  String fullName ;

    @Column(unique = true, nullable = false)
    private String username ;

    @Column( nullable = false)
    private String password ;

    @Column( nullable = false)
    private String phone ;

}
