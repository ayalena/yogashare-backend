package com.eindproject.YogaShare.users;

import com.eindproject.YogaShare.authorities.Authority;
import com.eindproject.YogaShare.userprofiles.UserProfile;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {

    //attributes
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private String email;

    //authority attribute
    private boolean enabled;


    //relations

    //one user has one profile
    @OneToOne(fetch = FetchType.LAZY)
    private UserProfile userProfile;

    //one user can have multiple authorities
    @OneToMany(
            targetEntity = Authority.class,
            mappedBy = "username",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER)
    private Set<Authority> authorities = new HashSet<>();


    //constructors
    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public User() {

    }


    //getters&setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    //relation getters&setters
    //for profile relations
    public UserProfile getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

    //for authorities relations
    public Set<Authority> getAuthorities() { return authorities; }

    public void setAuthorities(Set<Authority> authorities) { this.authorities = authorities; }

    public void addAuthority(Authority authority) {
        this.authorities.add(authority);
    }

    public void addAuthority(String authorityString) {
        this.authorities.add(new Authority(this.username, authorityString));
    }

    public void removeAuthority(Authority authority) {
        this.authorities.remove(authority);
    }

    public void removeAuthority(String authorityString) {
        this.authorities.removeIf(authority -> authority.getAuthority().equalsIgnoreCase(authorityString));
    }

    //authority getter&setter
    public boolean isEnabled() {
        return enabled;
    }
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
