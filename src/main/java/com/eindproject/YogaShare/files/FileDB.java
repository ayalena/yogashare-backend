package com.eindproject.YogaShare.files;

import com.eindproject.YogaShare.users.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "files")
public class FileDB {

    //attributes
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String type;

    @JsonIgnore
    private byte[] data;

    //relations
    @ManyToOne(fetch = FetchType.EAGER,
            optional = false)
    @JoinColumn(name = "user_id",
            nullable = false)
    @JsonIgnore
    private User user;

    //constructors
    public FileDB() {
    }

    public FileDB(String name, String type, byte[] data) {
        this.name = name;
        this.type = type;
        this.data = data;
    }

    public FileDB(User user) {
        this.user = user;
    }

    //getters&setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    //relation getters&setters
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    //for adding file to user
    public void add(FileDB fileDB) {
    }
}
