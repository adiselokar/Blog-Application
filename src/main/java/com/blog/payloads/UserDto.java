package com.blog.payloads;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private long id;

    @NotEmpty
    @Size(min = 4, message = "Name Must be Minimum of 4 Characters !!!")
    private String name;

    @Email(message = "Email address is not valid !!!")
    private String email;

    @NotEmpty
    @Size(min = 5, max = 12, message = "Password Must be minimum of 5 characters and maximum of 12 characters !!!")
    private String password;

}
