package com.example.cloudsek.controller;


import com.example.cloudsek.dto.addpostDto;
import com.example.cloudsek.dto.postResponseDto;
import com.example.cloudsek.dto.responseHandlerDto;
import com.example.cloudsek.model.postModel;
import com.example.cloudsek.service.authService;
import com.example.cloudsek.service.postService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/post")
public class postController {
    
    @Autowired
    private postService postservice;

    @Autowired
    private authService authservice;


    @GetMapping("/private")
    public String healthCheck() {
        return "Hello from the private endpoint!";
    }

    @PostMapping("/addPost")
    public postModel addPost(@RequestBody @Valid addpostDto req , HttpServletRequest http){

        Integer userId = authservice.getCurrentUserId(http);
        return  postservice.addPost(req , userId);

    }
    
    @PostMapping("/updatePost/{postId}")
    public postModel updatePost(@PathVariable Integer postId, @RequestBody @Valid addpostDto req , HttpServletRequest http){

        Integer userId = authservice.getCurrentUserId(http);
        return  postservice.updatePost(postId, req , userId);
    }

    @GetMapping("/getPost")
    public List<postResponseDto> getAllPosts(){

        return postservice.getAllPosts();
    }


    @GetMapping("/getUserPosts")
    public List<postResponseDto> getPostsByUserId( HttpServletRequest http ){

        Integer userId = authservice.getCurrentUserId(http);
        return postservice.getPostByUserId(userId);
    }

    @GetMapping("/getPost/{postId}")
    public postResponseDto getPost(@PathVariable Integer postId , HttpServletRequest http){

        Integer userId = authservice.getCurrentUserId(http);

        return postservice.getPostById(postId , userId );
    }

    @DeleteMapping("/softdeletePost/{postId}")
    public responseHandlerDto softDeletePost(@PathVariable Integer postId , HttpServletRequest http){
        Integer userId = authservice.getCurrentUserId(http);
        return postservice.softDeletePost(postId , userId);

    }

    @DeleteMapping("/deletePost/{postId}")
    public responseHandlerDto deletePost(@PathVariable Integer postId , HttpServletRequest http){

        Integer userId = authservice.getCurrentUserId(http);
         return postservice.deletePost(postId , userId );

    }

}
