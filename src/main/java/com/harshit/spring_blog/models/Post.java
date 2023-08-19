package com.harshit.spring_blog.models;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "post")
@Data
@NoArgsConstructor
public class Post {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer postId;

  @Column(name = "post_title", length = 100, nullable = false)
  private String title;

  @Column(length = 10000)
  private String content;

  private String imageName;

  private Date addedDate;

  @ManyToOne
  private User user;

  @ManyToOne
  @JoinColumn(name = "category_id")
  private Category category;

  @OneToMany(mappedBy = "post",cascade = CascadeType.ALL)
  private List<Comment> comments = new ArrayList<>();
}
