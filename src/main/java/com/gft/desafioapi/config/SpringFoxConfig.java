package com.gft.desafioapi.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.GrantType;
import springfox.documentation.service.OAuth;
import springfox.documentation.service.ResourceOwnerPasswordCredentialsGrant;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.ApiKeyVehicle;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SpringFoxConfig implements WebMvcConfigurer {

	private String accessTokenUri = "http://localhost:8080/oauth/token";

	@Bean
	public Docket api() {

		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.gft.desafioapi")).paths(PathSelectors.any()).build()
				.apiInfo(apiInfo())
				.tags(new Tag("Fornecedores", "Gerencia os fornecedores"), new Tag("Clientes", "Gerencia os clientes"),
						new Tag("Produtos", "Gerencia os produtos"), new Tag("Vendas", "Gerencia as vendas"))
				.securityContexts(Collections.singletonList(securityContext()))
				.securitySchemes(Arrays.asList(securitySchema()));
		// .globalOperationParameters(commonParameters())

	}

	private OAuth securitySchema() {

		List<AuthorizationScope> authorizationScopeList = new ArrayList<>();
		authorizationScopeList.add(new AuthorizationScope("read", "read all"));

		List<GrantType> grantTypes = new ArrayList<>();
		GrantType passwordCredentialsGrant = new ResourceOwnerPasswordCredentialsGrant(accessTokenUri);

		grantTypes.add(passwordCredentialsGrant);

		return new OAuth("oauth2", authorizationScopeList, grantTypes);

	}

	private SecurityContext securityContext() {
		return SecurityContext.builder().securityReferences(defaultAuth()).forPaths(PathSelectors.ant("/api/**"))
				.build();
	}

	private List<SecurityReference> defaultAuth() {

		final AuthorizationScope[] authorizationScopes = { new AuthorizationScope("read", "read all") };

		return Collections.singletonList(new SecurityReference("oauth2", authorizationScopes));
	}

	@Bean
	public SecurityConfiguration securityInfo() {
		return new SecurityConfiguration("admin", "admin", "", "", "Bearer access token", ApiKeyVehicle.HEADER,
				HttpHeaders.AUTHORIZATION, "");
	}

	public ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("GFT - Desafio API")
				.description("API construida para o desafio do programa START GFT").version("1")
				.contact(new Contact("GFT", "https://www.gft.com", "lucas.pedroso@gft.com")).build();
	}

	// MANUALLY ADD INPUT FOR ACCESS TOKEN TO ALL ENDPOINTS

	/*
	 * private List<Parameter> commonParameters() { List<Parameter> parameters = new
	 * ArrayList<Parameter>(); parameters.add(new
	 * ParameterBuilder().name("access_token").description("Token for
	 * authorization") .modelRef(new
	 * ModelRef("string")).parameterType("query").required(true).build());
	 * 
	 * return parameters; }
	 */

}
