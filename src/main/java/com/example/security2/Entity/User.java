package com.example.security2.Entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int user_id;

    @Column
    private String username;

    @Column
    private String password;

    @Column
    private String email;

    @Column
    private String roles;

    @Column
    private String provider;

    @Column
    private String providerId;

    @Column
    @CreationTimestamp
    private Timestamp createDate;

    @Builder
    public User(String username, String email, String roles, String provider, String providerId, Timestamp createDate) {
        this.username = username;
        this.email = email;
        this.roles = roles;
        this.provider = provider;
        this.providerId = providerId;
        this.createDate = createDate;
    }

    public List<String> getRoleList(){
        if(this.roles.length() >0){
            return Arrays.asList(this.roles.split(","));
        }
        return new ArrayList<>();
    }
}
