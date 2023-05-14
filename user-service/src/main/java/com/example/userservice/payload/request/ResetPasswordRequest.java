package com.example.userservice.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class ResetPasswordRequest {

    @NotBlank
    @JsonProperty("phone_number")
    private String phoneNum;

    @NotBlank
    @JsonProperty("password")
    private String password;
}
