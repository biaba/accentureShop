package com.project.backend.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.service.Tag;

// Swagger is not fully implemented but being tested how it works
// Files used in swagger : VisitorController -> (method) /products/category/{name} and UserFront

@Configuration
public class SwaggerConfig {

    @Bean
    public Docket api() {
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.project.backend"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(info());
        return addTags(docket);
    }

    private Docket addTags(Docket docket) {
        return docket.tags(new Tag(DescriptiveVariables.VISITOR, "Controller for all not Logged in user functionalities"));
    }

    private ApiInfo info() {
        return new ApiInfoBuilder()
                .title("Web Shop API")
                .description("Web Shop for admin and customer usage")
                .build();
    }
}
