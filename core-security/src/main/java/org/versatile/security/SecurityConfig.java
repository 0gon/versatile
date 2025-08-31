package org.versatile.security;

import java.nio.file.Path;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest.H2ConsoleRequestMatcher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.versatile.domain.member.UserDetailsManagerImpl;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserDetailsService memberDetailsService;

    // You are asking Spring Security to ignore org.springframework.boot.autoconfigure.security.servlet.StaticResourceRequest$StaticResourceRequestMatcher@426c0486. This is not recommended -- please use permitAll via HttpSecurity#authorizeHttpRequests instead.
    // @Bean
    // public WebSecurityCustomizer webSecurityCustomizer() {
    //     return (web -> web.ignoring()
    //             .requestMatchers(PathRequest.toStaticResources().atCommonLocations())); // 정적 리소스 보안 필터 제외
    // }

    // @Bean
    // DataSource dataSource() {
    //     return new EmbeddedDatabaseBuilder()
    //             .setType(EmbeddedDatabaseType.H2)
    //             .addScript(JdbcDaoImpl.DEFAULT_USER_SCHEMA_DDL_LOCATION)
    //             .build();
    // }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .cors(cors -> cors.disable())
                .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin())) // iframe 허용
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll() // 정적 리소스 허용
                        .requestMatchers(PathRequest.toH2Console()).permitAll() // h2-console 허용
                        .requestMatchers("/.well-known/**").permitAll()
                        .requestMatchers("/", "/login", "/signup", "/error", "/join").permitAll()

                        .requestMatchers("/test/**").permitAll()
                        .requestMatchers("/admin").hasRole("ADMIN")
                        .requestMatchers("/seller").hasRole("SELLER")
                        .anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults())
                .formLogin(Customizer.withDefaults())
                // .formLogin(form -> form
                //         .loginPage("/login")
                //         .defaultSuccessUrl("/"))
                //         .usernameParameter("email"));
                // .authenticationManager(authenticationManager())
                ;

        return http.build();
    }

    // @Bean
    // public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    //     http
    //             .authorizeHttpRequests((authorize) -> authorize
    //                     .requestMatchers("/login").permitAll()
    //                     .anyRequest().authenticated())
    //             .httpBasic(Customizer.withDefaults())
    //             .authenticationManager(authenticationManager());

    //     return http.build();
    // }

    // @Bean
    // UserDetailsManager userDetailsmanager(DataSource dataSource) {
    //     JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);
    //     return users;
    // }

    // @Bean
    // public AuthenticationManager authenticationManager() {
    //     DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider(userDetailsService());
    //     authenticationProvider.setPasswordEncoder(passwordEncoder());

    //     ProviderManager providerManager = new ProviderManager(authenticationProvider);
    //     providerManager.setEraseCredentialsAfterAuthentication(false);

    //     return providerManager;
    // }

    // @Bean
    // public UserDetailsService userDetailsService() {
    //     UserDetails userDetails = User.withDefaultPasswordEncoder()
    //             .username("user")
    //             .password("password")
    //             .roles("USER")
    //             .build();

    //     return new InMemoryUserDetailsManager(userDetails);
    // }

    @Bean
    public PasswordEncoder passwordEncoder() {
    return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    // @Bean
    // public PasswordEncoder passwordEncoder() {
    //     return new BCryptPasswordEncoder();
    // }

}