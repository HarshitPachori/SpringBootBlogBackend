package com.harshit.spring_blog.services;

import java.util.List;

import com.harshit.spring_blog.payloads.PostDto;
import com.harshit.spring_blog.payloads.PostResponse;

public interface PostService {

  PostDto createPost(PostDto postDto, Integer userId, Integer categoryId);

  PostDto updatePost(PostDto postDto, Integer postId);

  void deletePost(Integer postId);

  PostResponse getAllPosts(Integer pageNumber, Integer pageSize,String sortBy,String sortDir);

  PostDto getPostById(Integer postId);

  PostResponse getPostsByCategory(Integer categoryId,Integer pageNumber, Integer pageSize);

  PostResponse getPostsByUser(Integer userId,Integer pageNumber, Integer pageSize);

  List<PostDto> searchPosts(String keyword);

}
