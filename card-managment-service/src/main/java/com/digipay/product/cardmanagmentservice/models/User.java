package com.digipay.product.cardmanagmentservice.models;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "user")
@SuperBuilder
public class User extends ParentEntity {
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "username", nullable = false)
    private String username;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "mobile", nullable = false)
    private String mobile;

    public User() {
    }
}
