package com.example.cloudsek.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "post")
public class postModel extends baseModel {

    @Column(nullable = false)
    private Integer userId;

    @Column(nullable = false, length = 500)
    private String content;

}
