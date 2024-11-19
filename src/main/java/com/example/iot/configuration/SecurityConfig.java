package com.example.iot.configuration;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
  private final String[] PUBLIC_ENDPOINTS = { "/users", "/auth/token", "/auth/introspect" };
  private final String[] AUTH_ENDPOINTS = { "/api/v1/auth/**", "/v3/api-docs/**", "/v3/api-docs.yaml", "/swagger-ui/**", "/swagger-ui.html" };

  @Value("${jwt.signerKey}")
  private String signerKey;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
    httpSecurity.cors(Customizer.withDefaults());

    httpSecurity.authorizeHttpRequests(request -> request
        .requestMatchers(HttpMethod.POST, PUBLIC_ENDPOINTS).permitAll()
        .requestMatchers("/ws/**").permitAll()
        .requestMatchers(AUTH_ENDPOINTS).permitAll()
        .anyRequest().authenticated());

    httpSecurity.oauth2ResourceServer(oauth2 -> oauth2.jwt(jwtConfigurer -> jwtConfigurer.decoder(jwtDecoder())
        .jwtAuthenticationConverter(jwtAuthenticationConverter()))
        .authenticationEntryPoint(new JwtAuthenticationEntryPoint()));
    httpSecurity.csrf(AbstractHttpConfigurer::disable);

    return httpSecurity.build();
  }

  @Bean
  JwtAuthenticationConverter jwtAuthenticationConverter() {
    JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
    jwtGrantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");

    JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
    jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);

    return jwtAuthenticationConverter;
  }

  // cau hinh giai ma token
  @Bean
  JwtDecoder jwtDecoder() {
    SecretKeySpec secretKeySpec = new SecretKeySpec(signerKey.getBytes(), "HS512");
    return NimbusJwtDecoder
        .withSecretKey(secretKeySpec)
        .macAlgorithm(MacAlgorithm.HS512)
        .build();
  }

  // cau hinh cors khi goi tu fontend
  @Bean
  public CorsFilter corsFilter() {
    CorsConfiguration corsConfiguration = new CorsConfiguration();

    corsConfiguration.addAllowedOrigin("http://localhost:3000");
    corsConfiguration.addAllowedMethod("*");
    corsConfiguration.addAllowedHeader("*");
    corsConfiguration.setAllowCredentials(true); // Quan trọng khi làm việc với WebSocket

    UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
    urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);

    return new CorsFilter(urlBasedCorsConfigurationSource);
  }

  // cau hinh encoder password
  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder(10);
  }
}