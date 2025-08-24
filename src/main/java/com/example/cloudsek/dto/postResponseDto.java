package com.example.cloudsek.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class postResponseDto {

    @NotNull(message = "Post Id cannot be blank")
    private Integer postId;

    @NotBlank(message = "Content cannot be blank")
    private String content;

    @NotNull(message = "User Id cannot be blank")
    private Integer userId;


    private List<commentResponse> comments;

}
