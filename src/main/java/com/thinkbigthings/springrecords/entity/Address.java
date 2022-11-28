package com.thinkbigthings.springrecords.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "address")
public class Address {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, insertable = false, nullable = false)
    private Long id;

    @NotNull
    private String line1 = "";

    @NotNull
    private String city = "";

    @NotNull
    private String state = "";

    @NotNull
    private String zip = "";

    @NotNull
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    public Address() {

    }

    public Long getId() {
        return id;
    }

    public String getLine1() {
        return line1;
    }

    public void setLine1(String line1) {
        this.line1 = line1;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }
}
