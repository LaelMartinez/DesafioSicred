package com.sicred.associado.components;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.RestController;

import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerConfig {
	
	public static final String AUTHORIZATION_HEADER="Authorization";
	
	private ApiKey apiKeys() {
		return new ApiKey("JWT", AUTHORIZATION_HEADER, "header");
	}
	
	private List<SecurityContext> securityContext() {
		return Arrays.asList(SecurityContext.builder().securityReferences(securityReference()).build());
	}

	private List<SecurityReference> securityReference() {
		AuthorizationScope scope = new AuthorizationScope("global", "accessEverything");
		return Arrays.asList(new SecurityReference("JWT", new AuthorizationScope[] { scope }));
	}

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
        		.host("localhost:8080")
        		.securityContexts(securityContext())
        		.securitySchemes(Arrays.asList(apiKeys()))
        		.select()
        		.apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
        		.paths(PathSelectors.any())
        		.build()
			.apiInfo(metaData());
    }
    
    private ApiInfo metaData() {
		return new ApiInfoBuilder().title("Sicred Pagamentos API")
			.description("\"Spring Boot REST API\"").version("1.0.0")
			.build();
	}
}
