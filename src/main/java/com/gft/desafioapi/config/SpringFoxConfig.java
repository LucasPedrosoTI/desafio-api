package com.gft.desafioapi.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SpringFoxConfig implements WebMvcConfigurer {

	@Bean
	public Docket api() {

		List<ResponseMessage> list = new java.util.ArrayList<>();
		list.add(new ResponseMessageBuilder().code(500).message("500 message").responseModel(new ModelRef("Result"))
				.build());
		list.add(new ResponseMessageBuilder().code(401).message("Unauthorized").responseModel(new ModelRef("Result"))
				.build());
		list.add(new ResponseMessageBuilder().code(406).message("Not Acceptable").responseModel(new ModelRef("Result"))
				.build());

		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.gft.desafioapi")).paths(PathSelectors.any()).build()
				.globalOperationParameters(commonParameters()).apiInfo(apiInfo())
				.tags(new Tag("Fornecedores", "Gerencia os fornecedores"), new Tag("Clientes", "Gerencia os clientes"),
						new Tag("Produtos", "Gerencia os produtos"));
		// .securitySchemes(Collections.singletonList(securitySchema()))
		// .securityContexts(Collections.singletonList(securityContext())).pathMapping("/")
		// .useDefaultResponseMessages(false)
		// .globalResponseMessage(RequestMethod.GET,
		// list).globalResponseMessage(RequestMethod.POST, list);

	}

	public ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("GFT - Desafio API")
				.description("API construida para o desafio do programa START GFT").version("1")
				.contact(new Contact("GFT", "https://www.gft.com", "lucas.pedroso@gft.com")).build();
	}

	private List<Parameter> commonParameters() {
		List<Parameter> parameters = new ArrayList<Parameter>();
		parameters.add(new ParameterBuilder().name("access_token").description("Token for authorization")
				.modelRef(new ModelRef("string")).parameterType("query").required(true).build());

		return parameters;
	}

//	private OAuth securitySchema() {
//
//		List<AuthorizationScope> authorizationScopeList = new ArrayList<>();
//		authorizationScopeList.add(new AuthorizationScope("read", "read all"));
//		authorizationScopeList.add(new AuthorizationScope("trust", "trust all"));
//		authorizationScopeList.add(new AuthorizationScope("write", "access all"));
//
//		List<GrantType> grantTypes = new ArrayList<>();
//		GrantType creGrant = new ResourceOwnerPasswordCredentialsGrant("http://localhost:8080/oauth/token");
//
//		grantTypes.add(creGrant);
//
//		return new OAuth("oauth2schema", authorizationScopeList, grantTypes);
//
//	}
//
//	private SecurityContext securityContext() {
//		return SecurityContext.builder().securityReferences(defaultAuth()).forPaths(PathSelectors.ant("/api/**"))
//				.build();
//	}
//
//	private List<SecurityReference> defaultAuth() {
//
//		final AuthorizationScope[] authorizationScopes = new AuthorizationScope[3];
//		authorizationScopes[0] = new AuthorizationScope("read", "read all");
//		authorizationScopes[1] = new AuthorizationScope("trust", "trust all");
//		authorizationScopes[2] = new AuthorizationScope("write", "write all");
//
//		return Collections.singletonList(new SecurityReference("oauth2schema", authorizationScopes));
//	}
//
//	@Bean
//	public SecurityConfiguration securityInfo() {
//		return new SecurityConfiguration("admin", "admin", "", "", "", ApiKeyVehicle.HEADER, "", " ");
//	}

}
