package com.example.cloudsek.service;

import com.example.cloudsek.dto.addCommentDto;
import com.example.cloudsek.dto.responseHandlerDto;
import com.example.cloudsek.exceptions.NotFoundException;
import com.example.cloudsek.model.commentModel;
import com.example.cloudsek.model.postModel;
import com.example.cloudsek.repo.commentRepo;
import com.example.cloudsek.repo.postRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class commentService {

    @Autowired
    private commentRepo commentrepo;

    @Autowired
    private postRepo postrepo;

    public commentModel getComment(Integer commentId, Integer userId) {
        Optional<commentModel> optionalComment = commentrepo.findByIdAndUserId(commentId, userId);

        if (optionalComment.isEmpty()) {
            throw new NotFoundException("Comment with ID " + commentId + " not found for this user.");
        }

        return optionalComment.get();
    }

    public commentModel addComment(addCommentDto req, Integer userId) {
        Integer postId = req.getPostId();

        Optional<postModel> optionalPost = postrepo.findByIdAndIsActiveTrue(postId);
        if (optionalPost.isEmpty()) {
            throw new NotFoundException("Post with ID " + postId + " does not exist or is inactive.");
        }

        commentModel newComment = new commentModel();
        newComment.setComment(req.getComment());
        newComment.setUserId(userId);
        newComment.setPostId(postId);

        commentrepo.save(newComment);
        return newComment;
    }

    public commentModel updateComment(Integer commentId, addCommentDto req, Integer userId) {
        Optional<commentModel> optionalComment = commentrepo.findByIdAndUserId(commentId, userId);

        if (optionalComment.isEmpty()) {
            throw new NotFoundException("Comment with ID " + commentId + " does not exist for this user.");
        }

        commentModel existingComment = optionalComment.get();
        existingComment.setComment(req.getComment());

        commentrepo.save(existingComment);
        return existingComment;
    }

    public responseHandlerDto deleteComment(Integer commentId, Integer userId) {
        Optional<commentModel> optionalComment = commentrepo.findByIdAndUserId(commentId, userId);

        if (optionalComment.isEmpty()) {
            throw new NotFoundException("Comment with ID " + commentId + " does not exist for this user.");
        }

        commentModel comment = optionalComment.get();
        commentrepo.delete(comment);

        responseHandlerDto res = new responseHandlerDto();
        res.setMessage("Comment deleted successfully!");
        return res;
    }
}
