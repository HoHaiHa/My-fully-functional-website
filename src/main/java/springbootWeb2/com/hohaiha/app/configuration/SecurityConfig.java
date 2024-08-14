package springbootWeb2.com.hohaiha.app.configuration;


import javax.crypto.spec.SecretKeySpec;

import org.springframework.web.filter.CorsFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
	private final String[] PUBLIC_POST_ENDPOINTS = { "/users", "/auth/token", "/auth/introspect", "/auth/logout",
			"/auth/refresh" ,"/carts/**","/orders"};

	private final String[] PUBLIC_GET_ENDPOINTS = { "/favicon.ico", "/login", "/admin/users", "/admin/adduser",
			"/admin/detailuser", "/admin/updateuser", "/admin/roles", "/admin/categories","/products/**","/products","/carts/**","/orders","/orders/**"};

	private final String[] PUBLIC_PUT_ENDPOINTS = {"/carts/**"};
	
	private final String[] PUBLIC_DELETE_ENDPOINTS = {"/carts/**"};
	
	@Value("${jwt.signerKey}")
	private String SIGNER_KEY;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.authorizeHttpRequests(request -> request
				.requestMatchers(HttpMethod.POST, PUBLIC_POST_ENDPOINTS).permitAll()
				.requestMatchers(HttpMethod.GET, PUBLIC_GET_ENDPOINTS).permitAll()
				.requestMatchers(HttpMethod.PUT, PUBLIC_PUT_ENDPOINTS).permitAll()
				.requestMatchers(HttpMethod.DELETE, PUBLIC_DELETE_ENDPOINTS).permitAll()
				.requestMatchers("/images/**", "/css/**", "/js/**").permitAll()
				.anyRequest().authenticated());

		httpSecurity.oauth2ResourceServer(oauth2 -> oauth2
				.jwt(jwtConfigurer -> jwtConfigurer.decoder(jwtDecoder())
						.jwtAuthenticationConverter(jwtAuthenticationConverter()))
				.authenticationEntryPoint(new JwtAuthenticationEntryPoint()));

		httpSecurity.csrf(AbstractHttpConfigurer::disable);

		return httpSecurity.build();
	}

	@Bean
	public CorsFilter corsFilter() {
		CorsConfiguration corsConfiguration = new CorsConfiguration();
		corsConfiguration.addAllowedOrigin("*");
		corsConfiguration.addAllowedMethod("*");
		corsConfiguration.addAllowedHeader("*");

		UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
		urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);

		return new CorsFilter(urlBasedCorsConfigurationSource);
	}

	@Bean
	JwtAuthenticationConverter jwtAuthenticationConverter() {
		JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
		jwtGrantedAuthoritiesConverter.setAuthorityPrefix("");

		JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
		jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);

		return jwtAuthenticationConverter;
	}

	@Bean
	JwtDecoder jwtDecoder() {
		SecretKeySpec secretKeySpec = new SecretKeySpec(SIGNER_KEY.getBytes(), "HS512");
		return NimbusJwtDecoder.withSecretKey(secretKeySpec).macAlgorithm(MacAlgorithm.HS512).build();

	}
}
