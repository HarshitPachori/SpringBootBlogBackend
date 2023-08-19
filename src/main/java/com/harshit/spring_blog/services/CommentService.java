package com.harshit.spring_blog.services;

import com.harshit.spring_blog.payloads.CommentDto;

public interface CommentService {
  CommentDto createComment(CommentDto commentDto, Integer postId,Integer userId);

  void deleteComment(Integer commentId);
}
