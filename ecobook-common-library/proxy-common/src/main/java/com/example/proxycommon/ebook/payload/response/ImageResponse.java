package com.example.proxycommon.ebook.payload.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ImageResponse {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("img")
    private String img;
}
