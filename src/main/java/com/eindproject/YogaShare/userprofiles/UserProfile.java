package com.eindproject.YogaShare.userprofiles;

import com.eindproject.YogaShare.users.User;

import javax.persistence.*;

@Entity
@Table(name = "user_profiles")
public class UserProfile {

    //attributes
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String firstName;
    private String lastName;
    private int age;
    private String address;
    private String postalCode;
    private String country;

    //relations
    @OneToOne(fetch = FetchType.LAZY)
    private User user;

    //constructors
    public UserProfile() {
    }

    public UserProfile(String email,
                       String firstName,
                       String lastName,
                       int age, String address,
                       String postalCode,
                       String country,
                       User user) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.address = address;
        this.postalCode = postalCode;
        this.country = country;
        this.user = user;
    }

    //getters&setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }


    //relation getters&setters
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


}
