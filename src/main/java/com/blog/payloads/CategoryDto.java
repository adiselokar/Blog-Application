package com.blog.payloads;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {

    private Long id;

    @NotEmpty
    @Size( min = 4, message ="Title Length must be minimum of 4 Charectors")
    private String title;

    @NotEmpty
    @Size( min = 10, message ="Title Length must be minimum of 10 Charectors")
    private String description;

}
