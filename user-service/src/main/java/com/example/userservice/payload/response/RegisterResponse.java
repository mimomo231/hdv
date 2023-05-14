package com.example.userservice.payload.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterResponse {

    @JsonProperty("exist_username")
    private Integer existUsername;

    @JsonProperty("exist_phone_number")
    private Integer existPhoneNumber;
}
