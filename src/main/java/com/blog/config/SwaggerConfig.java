package com.blog.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    String schemaName = "bearerScheme";

    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement()
                                         .addList(schemaName)
                )
                .components(new Components()
                                    .addSecuritySchemes(schemaName, new SecurityScheme()
                                            .name(schemaName)
                                            .type(SecurityScheme.Type.HTTP)
                                            .bearerFormat("JWT")
                                            .scheme("bearer")
                                    )
                )
                .info(new Info().title("Blog Application API")
                              .description("Blog Application allows user to write Blogs")
                              .version("v0.0.1")
                              .contact(new Contact().name("Aditya").email("aditya@gmail.com"))
                              .license(new License().name("Apache 2.0").url("http://springdoc.org")))
                .externalDocs(new ExternalDocumentation()
                                      .description("SpringShop Wiki Documentation")
                                      .url("https://springshop.wiki.github.orgf/docs"));
    }

}
