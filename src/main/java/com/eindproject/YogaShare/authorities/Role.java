package com.eindproject.YogaShare.authorities;

import javax.persistence.*;

@Entity
@Table(name = "authorities")
public class Role {

    //attributes
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Enumerated(EnumType.STRING)
    private ERole name;

    //constructors
    public Role() {
    }

    public Role(ERole name) {
        this.name = name;
    }

    //getters&setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ERole getName() {
        return name;
    }

    public void setName(ERole name) {
        this.name = name;
    }



}
