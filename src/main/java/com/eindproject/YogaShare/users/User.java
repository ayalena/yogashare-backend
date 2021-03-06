package com.eindproject.YogaShare.users;

import com.eindproject.YogaShare.authorities.Role;
import com.eindproject.YogaShare.files.FileDB;
import com.eindproject.YogaShare.userprofiles.UserProfile;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username"),
                @UniqueConstraint(columnNames = "email")
        })
public class User {

    //attributes
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private String email;


    //relations
    //one user has one profile
    @OneToOne(fetch = FetchType.LAZY,
            mappedBy = "user",
            cascade = CascadeType.ALL)
    private UserProfile userProfile;

    //multiple users can have multiple authorities
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    //one user(teacher) can have many files
    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL)
    @JsonIgnore
    private List<FileDB> fileDB;


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
    //profile
    public UserProfile getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

    //authorities
    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    //files
    public List<FileDB> getFileDB() {
        return fileDB;
    }

    public void setFileDB(List<FileDB> fileDB) {
        this.fileDB = fileDB;
    }

    public void addFileDB(FileDB fileDB) {
        this.fileDB.add(fileDB);
        fileDB.setUser(this);
    }
}
