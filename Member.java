package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "members")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    private boolean isDeleted = false;

    // Constructors
    public Member() {

    }

    public Member(String name, String email) {
        this.name = name;
        this.email = email;
    }

    // Getters and Setters
    public Long getId() { 
        return id; 
    }
    public String getName() { 
        return name; 
    }
    public void setName(String name) { 
        this.name = name; 
    }
    public String getEmail() { 
        return email; 
    }
    public void setEmail(String email) { 
        this.email = email; 
    }
    public boolean isDeleted() { 
        return isDeleted; 
    }
    public void setDeleted(boolean deleted) { 
        isDeleted = deleted; 
    }
}


