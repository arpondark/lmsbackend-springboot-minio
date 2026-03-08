//package site.shazan.lmsbackend.config;
//
//
//import lombok.Data;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.oauth2.client.oidc.web.logout.OidcClientInitiatedLogoutSuccessHandler;
//import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
//import org.springframework.security.web.SecurityFilterChain;
//import site.shazan.lmsbackend.service.CustomOidcService;
//
//
//@Configuration
//@EnableWebSecurity
//@Data
//public class SecurityConfig {
//    private final ClientRegistrationRepository clientRegistrationRepository;
//    private final CustomOidcService customOidcService;
//
//
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) {
//        OidcClientInitiatedLogoutSuccessHandler handler = new OidcClientInitiatedLogoutSuccessHandler(clientRegistrationRepository);
//        handler.setPostLogoutRedirectUri("{baseUrl}");
//
//        http
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/login","/error").permitAll()
//                        .requestMatchers("/user").hasRole("USER")
//                        .requestMatchers("/manager").hasRole("TEACHER")
//                        .requestMatchers("/admin").hasRole("ADMIN")
//                        .anyRequest().authenticated()
//                )
//                .oauth2Login(oauth2 -> oauth2
//                        .loginPage("/login")
//                        .defaultSuccessUrl("/home", true)
//                        .userInfoEndpoint(userInfo -> userInfo.oidcUserService(customOidcService))
//                )
//                .logout(logout -> logout.logoutSuccessHandler(handler));
//
//        return http.build();
//    }
//}
