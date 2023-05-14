package com.example.userdatamodel.entity.enumtype;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Arrays;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum AuthProviderEnum {

    LOCAL("Local"),
    GOOGLE("Google"),
    FACEBOOK("Facebook");

    private String code;

    public static AuthProviderEnum findByCode(String code) {
        return Arrays.stream(values()).filter(v -> v.code.equals(code)).findAny().orElse(null);
    }
}
