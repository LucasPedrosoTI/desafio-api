package com.gft.desafioapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SpringFoxConfig implements WebMvcConfigurer {

	@Bean
	public Docket apiDocket() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.gft.desafioapi.resource")).paths(PathSelectors.any())
				.build().apiInfo(apiInfo()).tags(new Tag("Clientes", "Recurso para gerenciar clientes"));
	}

	public ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("GFT - Desafio API")
				.description("API construida para o desafio do programa START GFT").version("1")
				.contact(new Contact("GFT", "https://www.gft.com", "lucas.pedroso@gft.com")).build();
	}

}
