package com.example.cloudsek.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class commentResponse {

    @NotBlank(message = "Comment cannot be blank")
    private String comment;

    @NotNull(message = "User Id cannot be blank")
    private Integer userId;
}
