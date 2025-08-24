package com.example.cloudsek.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class commentModel extends baseModel{


    @Column(nullable = false , columnDefinition = "TEXT")
    private String comment;

    @Column(nullable = false)
    private Integer userId;

    @Column( nullable = false)
    private Integer postId;
}
