package com.harshit.spring_blog.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.harshit.spring_blog.payloads.ApiResponse;
import com.harshit.spring_blog.payloads.PostDto;
import com.harshit.spring_blog.payloads.PostResponse;
import com.harshit.spring_blog.services.FileService;
import com.harshit.spring_blog.services.PostService;
import com.harshit.spring_blog.utils.AppConstants;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api")
public class PostController {

  @Autowired
  private PostService postService;

  @Autowired
  private FileService fileService;

  @Value("${project.image}")
  private String imagePath;

  // POST create post

  @PostMapping("/users/{userId}/categories/{categoryId}/posts")
  public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto, @PathVariable Integer userId,
      @PathVariable Integer categoryId) {
    PostDto createdPost = postService.createPost(postDto, userId, categoryId);
    return new ResponseEntity<PostDto>(createdPost, HttpStatus.CREATED);
  }

  // GET get all posts

  @GetMapping("/posts")
  public ResponseEntity<PostResponse> getAllPost(
      @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
      @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
      @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
      @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir) {
    PostResponse allPosts = postService.getAllPosts(pageNumber, pageSize, sortBy, sortDir);
    return new ResponseEntity<PostResponse>(allPosts, HttpStatus.OK);
  }

  // GET get post by id

  @GetMapping("/posts/{postId}")
  public ResponseEntity<PostDto> getPostById(@PathVariable("postId") Integer postId) {
    PostDto post = postService.getPostById(postId);
    return new ResponseEntity<PostDto>(post, HttpStatus.OK);
  }

  // GET get all posts by user

  @GetMapping("/users/{userId}/posts")
  public ResponseEntity<PostResponse> getPostByUser(@PathVariable("userId") Integer userId,
      @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
      @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize) {
    PostResponse postByUser = postService.getPostsByUser(userId, pageNumber, pageSize);
    return new ResponseEntity<PostResponse>(postByUser, HttpStatus.OK);
  }

  // GET get all posts by category

  @GetMapping("/categories/{categoryId}/posts")
  public ResponseEntity<PostResponse> getPostByCategory(@PathVariable("categoryId") Integer categoryId,
      @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
      @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize) {
    PostResponse postByCategory = postService.getPostsByCategory(categoryId, pageNumber, pageSize);
    return new ResponseEntity<PostResponse>(postByCategory, HttpStatus.OK);
  }

  // DELETE delete post by id

  @DeleteMapping("/posts/{postId}")
  public ResponseEntity<ApiResponse> deletePost(@PathVariable("postId") Integer postId) {
    postService.deletePost(postId);
    return new ResponseEntity<ApiResponse>(new ApiResponse("post is deleted successfully", true), HttpStatus.OK);
  }

  // UPDATE update post by id

  @PutMapping("/posts/{postId}")
  public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable("postId") Integer postId) {
    PostDto updatedPost = postService.updatePost(postDto, postId);
    return new ResponseEntity<PostDto>(updatedPost, HttpStatus.OK);
  }

  // SEARCH we are taking the search keyword in the url in this method
  @GetMapping("/posts/search/{keywords}")
  public ResponseEntity<List<PostDto>> searchPostByTitle(@PathVariable("keywords") String keywords) {
    List<PostDto> searchPosts = postService.searchPosts(keywords);
    return new ResponseEntity<List<PostDto>>(searchPosts, HttpStatus.OK);
  }

  // IMAGE UPLOAD

  @PostMapping("/posts/image/upload/{postId}")
  public ResponseEntity<PostDto> uploadPostImage(@RequestParam("image") MultipartFile image,
      @PathVariable("postId") Integer postId) throws IOException {
    PostDto postDto = postService.getPostById(postId);
    String uploadImage = fileService.uploadImage(imagePath, image);
    postDto.setImageName(uploadImage);
    postService.updatePost(postDto, postId);
    return new ResponseEntity<PostDto>(postDto, HttpStatus.OK);
  }

  // IMAGE SERVE
  @GetMapping(value = "/posts/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
  public void downloadImage(
      @PathVariable("imageName") String imageName,
      HttpServletResponse response) throws IOException {
    InputStream resource = fileService.getResoure(imagePath, imageName);
    response.setContentType(MediaType.IMAGE_JPEG_VALUE);
    StreamUtils.copy(resource, response.getOutputStream());
  }
}
