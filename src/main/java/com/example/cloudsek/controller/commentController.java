package com.example.cloudsek.controller;

import com.example.cloudsek.dto.addCommentDto;
import com.example.cloudsek.dto.responseHandlerDto;
import com.example.cloudsek.model.commentModel;
import com.example.cloudsek.service.authService;
import com.example.cloudsek.service.commentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/comment")
public class commentController {

    @Autowired
    private commentService commentservice;

    @Autowired
    private authService authservice;

    @PostMapping("/addComment")
    public commentModel addComment(@RequestBody  @Valid addCommentDto request , HttpServletRequest http) {

        Integer userId = authservice.getCurrentUserId(http);
        return commentservice.addComment(request , userId);
    }

    @PostMapping("/updateComment/{commentId}")
    public commentModel updateComment(@PathVariable Integer commentId, @RequestBody  @Valid addCommentDto request , HttpServletRequest http) {

        Integer userId = authservice.getCurrentUserId(http);
        return commentservice.updateComment( commentId , request , userId);

    }

    @GetMapping("/getComment/{commentId}")
    public commentModel getComment(@PathVariable Integer commentId , HttpServletRequest http) {

        Integer userId = authservice.getCurrentUserId(http);
        return commentservice.getComment(commentId , userId);
    }


    @DeleteMapping("/deleteComment/{commentId}")
    public responseHandlerDto deleteComment(@PathVariable Integer commentId, HttpServletRequest http) {

        Integer userId = authservice.getCurrentUserId(http);
        return commentservice.deleteComment(commentId , userId);
    }

}
