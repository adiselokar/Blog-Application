package com.blog.payloads;

import lombok.Builder;
import lombok.Data;

@Data
public class JwtAuthResponse {

    private String token;

}
