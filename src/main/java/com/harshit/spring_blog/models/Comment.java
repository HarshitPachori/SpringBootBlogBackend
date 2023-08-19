package com.harshit.spring_blog.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "comments")
@Data
public class Comment {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  private String content;

  @ManyToOne
  @JoinColumn(name = "post_id")
  private Post post;

  @ManyToOne
  private User user;
}
