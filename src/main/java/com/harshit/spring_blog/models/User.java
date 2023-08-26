package com.harshit.spring_blog.models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class User implements UserDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(name = "user_name", nullable = false)
  private String name;
  private String email;
  private String password;
  private String about;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<Post> posts = new ArrayList<>();

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
  private List<Comment> comments = new ArrayList<>();

  /*
   * here used join table to create another table of user roles
   * tables
   * 1. role -> roleId, name
   * 2. user_role -> user, role (userId,roleId) and here user is join col and role
   * is inverse join col
   */
  @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "role", referencedColumnName = "roleId"))
  private Set<Roles> roles = new HashSet<>();

  @OneToOne(mappedBy = "user")
  private RefreshToken refreshToken ;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    List<SimpleGrantedAuthority> authorities = roles.stream()
        .map((role) -> new SimpleGrantedAuthority(role.getRoleName())).collect(Collectors.toList());
    return authorities;
  }

  @Override
  public String getUsername() {
    return email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

}
