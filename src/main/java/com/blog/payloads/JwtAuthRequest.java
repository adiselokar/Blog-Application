package com.blog.payloads;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class JwtAuthRequest {

    private String username;
    private String password;

}
