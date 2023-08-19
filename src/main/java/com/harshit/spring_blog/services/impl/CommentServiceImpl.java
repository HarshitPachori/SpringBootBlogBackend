package com.harshit.spring_blog.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.harshit.spring_blog.exceptions.ResourceNotFoundException;
import com.harshit.spring_blog.models.Comment;
import com.harshit.spring_blog.models.Post;
import com.harshit.spring_blog.models.User;
import com.harshit.spring_blog.payloads.CommentDto;
import com.harshit.spring_blog.repositories.CommentRepository;
import com.harshit.spring_blog.repositories.PostRepository;
import com.harshit.spring_blog.repositories.UserRepository;
import com.harshit.spring_blog.services.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

  @Autowired
  PostRepository postRepository;

  @Autowired
  CommentRepository commentRepository;

  @Autowired
  UserRepository userRepository;

  @Autowired
  ModelMapper modelMapper;

  @Override
  public CommentDto createComment(CommentDto commentDto, Integer postId, Integer userId) {
    Post post = postRepository.findById(postId)
        .orElseThrow(() -> new ResourceNotFoundException("Post", "postId", postId));
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));
    Comment comment = modelMapper.map(commentDto, Comment.class);
    comment.setPost(post);
    comment.setUser(user);
    Comment savedComment = commentRepository.save(comment);
    return modelMapper.map(savedComment, CommentDto.class);
  }

  @Override
  public void deleteComment(Integer commentId) {
    Comment comment = commentRepository.findById(commentId)
        .orElseThrow(() -> new ResourceNotFoundException("Comment", "commentId", commentId));
    commentRepository.delete(comment);
  }
}
