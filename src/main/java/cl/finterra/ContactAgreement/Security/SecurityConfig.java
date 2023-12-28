package cl.finterra.ContactAgreement.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {


    @Bean
    public JwtTokenProvider jwtTokenProvider() {
        return new JwtTokenProvider();
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authz) -> authz
                        .requestMatchers("/usuario/login").hasRole("ADMIN")
                        .requestMatchers("/usuario/encontrar-usuario").hasRole("ADMIN")
                        .requestMatchers("/usuario/{email}/actualizar-contrasena").hasRole("ADMIN")
                        .anyRequest().authenticated()
                );
        return http.build();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails user = User.withDefaultPasswordEncoder()
                //crea o instancia los roles, username y password
                .username("user")
                .password("password")
                .roles("ADMIN")
                .build();
        return new InMemoryUserDetailsManager(user);
    }
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        //permite las rutas especificadas y bloquea el resto
        return (web) -> web.ignoring().requestMatchers(
                "/usuario/login",
                "/usuario/encontrar-usuario",
                "/usuario/{email}/actualizar-contrasena"
        );
    }


}