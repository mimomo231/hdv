package com.example.security.model;

import lombok.*;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserAccountInfo {

    private Long userId;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String phoneNum;
    private List<String> roles;
}
