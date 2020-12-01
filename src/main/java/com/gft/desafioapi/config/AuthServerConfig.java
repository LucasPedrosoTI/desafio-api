package com.gft.desafioapi.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import com.gft.desafioapi.service.ClienteService;

@SuppressWarnings("deprecation")
@Configuration
@EnableAuthorizationServer

public class AuthServerConfig extends AuthorizationServerConfigurerAdapter {

	private final String privateKey = "-----BEGIN RSA PRIVATE KEY-----\r\n"
			+ "MIIEogIBAAKCAQEAtLfK2RX/UMcDRWMnQ4eLntAlbtx0oQ0wgNZZcKdqVXIyxwj0\r\n"
			+ "9FIGE3PeE1ANp4b07ic3DSbmM3FlcYaVdIWF02aTswquIJFHzVIuA7QmJLHP/dB6\r\n"
			+ "m7Ac+Qo6ATbfVsgm8lXqlYoJ1KKADg+jqwf9eJeJWwkOrRNB63l1l1qbsLVrBoZg\r\n"
			+ "f7tjCS8VVJWE6Jqx3wKPxhgxsEynckobnGG83nS8HzZws/PNac+qxvlkQfwgnzlP\r\n"
			+ "ZyqtVpoJcefBdBqOGeb6ZV4bD62A+Z4QVV5smURKNh6yqlTqC5NZDgU9YVd+fNE1\r\n"
			+ "LG/XHsbRf0PxnkLcFOhn4cPUdjfK3kmvuSuA3QIDAQABAoIBAB+eEEVOze6KAw5+\r\n"
			+ "vH3aFxlf/UyaEWWj8xp3ZgqJn9b3Nd8QuBqOxzVcDjx+wwTOXcfU4JLSfW/+pOrp\r\n"
			+ "yvgp7DICPlESy0gzYsaPXDGVsR62ZVdYxDq84zTHD0LrIMQEHJxFX9dt4S5sh87t\r\n"
			+ "+YqQyU3x5TwiJ4ksV1olU+d5GMgfigFRQqWCoh7030kTkKV9SSmMI8YG4Q4TgFki\r\n"
			+ "Q/hSMw/0H9Eubc5U+T6XlFe1z08t3hYZNWvT4vaNi7kYaov6v1oiL4aOpjXZ/8/l\r\n"
			+ "Wikb7eoVCZBP6Te8oDEU555RodgJ9zOAcx2dlvrvqsju/OlBfVisZjSsctlJUuIp\r\n"
			+ "1iiZH5kCgYEA5UftIaQrzllVVQoFiMFvPjVo0CQCUxb1MuvueRlQAw1GbRFDbXR4\r\n"
			+ "Cedo7xgSVfmiGsjciof8iixRC5229X60afPeURSByGXsqLksMKg20se7Oal2dBf7\r\n"
			+ "RcHbI/d4C1ZfhFkGmZ2TskfRBqgXxqJJCNL7ASXRvXcP6JnmxZxYq/sCgYEAyccZ\r\n"
			+ "ZxLQSta4ypARwUabhKgv7y+IseEWeXebxxIAjERRAoiQIJGKJtQ7tfBRIV9ScrQ3\r\n"
			+ "znL/5y90HvKYwjcZtXoVIJ028/D4q2CGXllK/Qn03eHHbFmCaYe4GjlPFBZnfphB\r\n"
			+ "mJwEXRd4aStZlZE+lB0MjMzD+SEWu6cwZ2a91wcCgYA64E5vThYFpgD4lls3c9ya\r\n"
			+ "D+L7V9PU/Fc4nUceh7Z5IiaMiVtUNw1AFdFnXDoRI8pRsHSZijP6U0j4f6ZkwgWY\r\n"
			+ "E4xqK6bsC+hKp0pGsFtrouhb9T06sJHQpugcA0crOiHK/YVpWwJ7TF/LosyAMFoq\r\n"
			+ "zuqvr8N9MJ7ALw0If45xkwKBgFvK3X2Mi4FgSVStUcZxTpJ8yMGxCCiT01uS1CUt\r\n"
			+ "xV4KPig+5AJM82JSro9IMkBSUvmnjrO2kUAk0Fi72E6SLnnmffIGU1eKSjX8hWQq\r\n"
			+ "jviqLOE7szNRTQihG1mitJzYzGBE96JrZ3jKEpcVh32JQ8SxqmHrTUPqj6LyngyM\r\n"
			+ "KtTFAoGAJnT3/wr3EA193WMw4Rlju5eJ9WOG0QcAhd4Oa8mt/WxMi83amUYqc+DQ\r\n"
			+ "GxtZEZak5M720b23swIVrWptClAmZ7qdh+ascSHOPXO/FKoheGh16Byp6Ix4EfP+\r\n"
			+ "2tzHQfKmIJIGC/gVWNRgdkNW73qsH+4NTZFaJOyU9EhXPNnrGQI=" + "-----END RSA PRIVATE KEY-----";

	private final String publicKey = "-----BEGIN PUBLIC KEY-----\r\n"
			+ "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAtLfK2RX/UMcDRWMnQ4eL\r\n"
			+ "ntAlbtx0oQ0wgNZZcKdqVXIyxwj09FIGE3PeE1ANp4b07ic3DSbmM3FlcYaVdIWF\r\n"
			+ "02aTswquIJFHzVIuA7QmJLHP/dB6m7Ac+Qo6ATbfVsgm8lXqlYoJ1KKADg+jqwf9\r\n"
			+ "eJeJWwkOrRNB63l1l1qbsLVrBoZgf7tjCS8VVJWE6Jqx3wKPxhgxsEynckobnGG8\r\n"
			+ "3nS8HzZws/PNac+qxvlkQfwgnzlPZyqtVpoJcefBdBqOGeb6ZV4bD62A+Z4QVV5s\r\n"
			+ "mURKNh6yqlTqC5NZDgU9YVd+fNE1LG/XHsbRf0PxnkLcFOhn4cPUdjfK3kmvuSuA\r\n" + "3QIDAQAB\r\n"
			+ "-----END PUBLIC KEY-----";

	@Autowired
	@Lazy
	@Qualifier("authenticationManagerBean")
	private AuthenticationManager authenticationManager;

	@Autowired
	@Lazy
	private ClienteService clienteService;

	@Autowired
	@Qualifier("dataSource")
	private DataSource dataSource;

	@Bean
	public JwtAccessTokenConverter tokenEnhancer() {
		JwtAccessTokenConverter converter = new JwtAccessTokenConverter();

		converter.setSigningKey(privateKey);
		converter.setVerifierKey(publicKey);

		return converter;
	}

	@Bean
	public JwtTokenStore tokenStore() {
		return new JwtTokenStore(tokenEnhancer());
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints.authenticationManager(authenticationManager).tokenStore(tokenStore())
				.accessTokenConverter(tokenEnhancer()).userDetailsService(clienteService);
	}

	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		security.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()")
				.allowFormAuthenticationForClients();
	}

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.withClientDetails(new JdbcClientDetailsService(dataSource));
	}
}
