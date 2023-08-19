package com.harshit.spring_blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.harshit.spring_blog.payloads.ApiResponse;
import com.harshit.spring_blog.payloads.CommentDto;
import com.harshit.spring_blog.services.CommentService;

@RestController
@RequestMapping("/api")
public class CommentController {

  @Autowired
  private CommentService commentService;

  @PostMapping("/users/{userId}/posts/{postId}/comments")
  public ResponseEntity<CommentDto> createComment(
      @RequestBody CommentDto commentDto,
      @PathVariable("userId") Integer userId,
      @PathVariable("postId") Integer postId) {
    CommentDto createdComment = commentService.createComment(commentDto, postId, userId);
    return new ResponseEntity<CommentDto>(createdComment, HttpStatus.CREATED);
  }

  @DeleteMapping("/posts/comments/{commentId}")
  public ResponseEntity<ApiResponse> deleteComment(
      @PathVariable("commentId") Integer commentId) {
    commentService.deleteComment(commentId);
    return new ResponseEntity<>(new ApiResponse("Comment deleted successfully", true), HttpStatus.OK);
  }

}
