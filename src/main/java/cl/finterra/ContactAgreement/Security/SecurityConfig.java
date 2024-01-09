package cl.finterra.ContactAgreement.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public JwtTokenProvider jwtTokenProvider() {
        return new JwtTokenProvider();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .formLogin(AbstractHttpConfigurer::disable)
                .requestCache(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((authz) -> authz
                        .requestMatchers("/usuario/login",
                                         "/usuario/encontrar-usuario").permitAll()
                        .requestMatchers("/usuario/{email}/actualizar-contrasena",
                                         "/usuario/nombre",
                                         "/deudor/{rut}",
                                         "/deudor",
                                         "/version",
                                         "/version/correo",
                                         "/actualiza-rut-deudor",
                                         "/**").permitAll()
                        .anyRequest().authenticated()
                ).httpBasic(AbstractHttpConfigurer::disable);
        return http.build();
    }
        @Bean
        public InMemoryUserDetailsManager userDetailsService() {
            //crea o instancia los roles, username y password
            //Se puede usar WithDefaultPasswordEncoder pero esta obsoleto y es preferible usar BCript
            UserDetails user = User.withUsername("user")
                    .password(passwordEncoder().encode("password"))
                    .roles("ADMIN")
                    .build();
            return new InMemoryUserDetailsManager(user);
        }
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("OPTIONS");
        config.addAllowedMethod("GET");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("DELETE");
        source.registerCorsConfiguration("/**", config);
        return source;
    }
//    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer() {
//        //permite las rutas especificadas y bloquea el resto
//        return (web) -> web.ignoring().requestMatchers(
//                "/usuario/login",
//                "/usuario/encontrar-usuario",
//                "/*",
//                "/**",
//                "/usuario/{email}/actualizar-contrasena"
//        );
//    }

}
