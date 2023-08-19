package com.harshit.spring_blog.services.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.harshit.spring_blog.exceptions.ResourceNotFoundException;
import com.harshit.spring_blog.models.Category;
import com.harshit.spring_blog.models.Post;
import com.harshit.spring_blog.models.User;
import com.harshit.spring_blog.payloads.PostDto;
import com.harshit.spring_blog.payloads.PostResponse;
import com.harshit.spring_blog.repositories.CategoryRepository;
import com.harshit.spring_blog.repositories.PostRepository;
import com.harshit.spring_blog.repositories.UserRepository;
import com.harshit.spring_blog.services.PostService;

@Service
public class PostServiceImpl implements PostService {

  // to make this class a component of the spring container we use service
  // annotation on the class

  @Autowired
  private PostRepository postRepository;

  @Autowired
  private ModelMapper modelMapper;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private CategoryRepository categoryRepository;

  @Override
  public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));
    Category category = categoryRepository.findById(categoryId)
        .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
    Post post = modelMapper.map(postDto, Post.class);
    post.setImageName("default.png");
    post.setAddedDate(new Date());
    post.setUser(user);
    post.setCategory(category);

    Post createdPost = postRepository.save(post);

    return modelMapper.map(createdPost, PostDto.class);
  }

  @Override
  public PostDto updatePost(PostDto postDto, Integer postId) {
    Post post = postRepository.findById(postId)
        .orElseThrow(() -> new ResourceNotFoundException("Post", "postId", postId));
    // postdto -> new data post -> new data
    post.setTitle(postDto.getTitle());
    post.setContent(postDto.getContent());
    post.setImageName(postDto.getImageName());
    Post updatedPost = postRepository.save(post);
    return modelMapper.map(updatedPost, PostDto.class);

  }

  @Override
  public void deletePost(Integer postId) {
    Post post = postRepository.findById(postId)
        .orElseThrow(() -> new ResourceNotFoundException("Post", "postId", postId));
    postRepository.delete(post);
  }

  @Override
  public PostResponse getAllPosts(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {

    // paging
    // sorting
    Sort sort = null;
    if (sortDir.equals("asc")) {
      sort = Sort.by(Direction.ASC, sortBy);
    } else {
      sort = Sort.by(Direction.DESC, sortBy);

    }
    // Pageable pageable = PageRequest.of(pageNumber, pageSize,Sort.by(sortBy)); ese
    // hi use kar sakte
    Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
    Page<Post> pagePosts = postRepository.findAll(pageable);

    List<Post> posts = pagePosts.getContent();

    List<PostDto> postDtos = posts.stream().map((post) -> modelMapper.map(post, PostDto.class))
        .collect(Collectors.toList());

    PostResponse postResponse = new PostResponse();
    postResponse.setContent(postDtos);
    postResponse.setPageNumber(pagePosts.getNumber());
    postResponse.setPageSize(pagePosts.getSize());
    postResponse.setTotalElements(pagePosts.getTotalElements());
    postResponse.setTotalPages(pagePosts.getTotalPages());
    postResponse.setLastPage(pagePosts.isLast());
    return postResponse;
  }

  @Override
  public PostDto getPostById(Integer postId) {
    Post post = postRepository.findById(postId)
        .orElseThrow(() -> new ResourceNotFoundException("Post", "postId", postId));
    return modelMapper.map(post, PostDto.class);
  }

  @Override
  public PostResponse getPostsByCategory(Integer categoryId, Integer pageNumber, Integer pageSize) {
    Category category = categoryRepository.findById(categoryId)
        .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));

    Pageable pageable = PageRequest.of(pageNumber, pageSize);

    Page<Post> categoryPagePosts = postRepository.findByCategory(category, pageable);
    List<Post> posts = categoryPagePosts.getContent();

    List<PostDto> postDtos = posts.stream().map((post) -> modelMapper.map(post, PostDto.class)).collect(
        Collectors.toList());

    PostResponse postResponse = new PostResponse();
    postResponse.setContent(postDtos);
    postResponse.setPageNumber(categoryPagePosts.getNumber());
    postResponse.setPageSize(categoryPagePosts.getSize());
    postResponse.setTotalElements(categoryPagePosts.getTotalElements());
    postResponse.setTotalPages(categoryPagePosts.getTotalPages());
    postResponse.setLastPage(categoryPagePosts.isLast());
    return postResponse;
  }

  @Override
  public PostResponse getPostsByUser(Integer userId, Integer pageNumber, Integer pageSize) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));

    Pageable pageable = PageRequest.of(pageNumber, pageSize);

    Page<Post> userPagePosts = postRepository.findByUser(user, pageable);

    List<Post> posts = userPagePosts.getContent();
    List<PostDto> postDtos = posts.stream().map((post) -> modelMapper.map(post, PostDto.class)).collect(
        Collectors.toList());

    PostResponse postResponse = new PostResponse();
    postResponse.setContent(postDtos);
    postResponse.setPageNumber(userPagePosts.getNumber());
    postResponse.setPageSize(userPagePosts.getSize());
    postResponse.setTotalElements(userPagePosts.getTotalElements());
    postResponse.setTotalPages(userPagePosts.getTotalPages());
    postResponse.setLastPage(userPagePosts.isLast());
    return postResponse;
  }

  @Override
  public List<PostDto> searchPosts(String keyword) {
    List<Post> searchedPosts = postRepository.findByTitleContaining(keyword);
    List<PostDto> searchedPostDtos = searchedPosts.stream().map((post) -> modelMapper.map(post, PostDto.class))
        .collect(Collectors.toList());
    return searchedPostDtos;
  }

}
