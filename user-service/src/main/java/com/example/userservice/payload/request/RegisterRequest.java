package com.example.userservice.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Getter @Setter
public class RegisterRequest {

    @NotBlank
    @JsonProperty("username")
    private String username;

    @NotBlank
    @JsonProperty("password")
    private String password;

    @JsonProperty("first_name")
    private String fName;

    @NotBlank
    @JsonProperty("last_name")
    private String lName;

    @JsonProperty("address")
    private String address;

    @NotBlank
    @JsonProperty("phone_number")
    private String phoneNum;
}