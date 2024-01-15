package cl.finterra.ContactAgreement.Security;

import cl.finterra.ContactAgreement.dao.UsuarioMongoDAO;
import cl.finterra.ContactAgreement.dto.UsuarioDTO;
import cl.finterra.ContactAgreement.entity.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private UsuarioDTO Usuario;

    @Bean
    public JwtTokenProvider jwtTokenProvider() {
        return new JwtTokenProvider();
    }
    @Bean
    //BCrypt Password encoder
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                //configuraciones de seguridad
                .formLogin(AbstractHttpConfigurer::disable)
//                .requestCache(AbstractHttpConfigurer::disable)
//                .csrf(csrf -> csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
                .cors(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Sesiones sin estado para APIs REST
                        .invalidSessionUrl("/admin")
                        .maximumSessions(1) //maximo de sesiones
                        .expiredUrl("/admin") //url para sesion expirada
                        .maxSessionsPreventsLogin(true)
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/admin")
                        .invalidateHttpSession(true) // Invalida la sesión al cerrar sesión
                        .deleteCookies("JSESSIONID") // Elimina cookies al cerrar sesión
                )
                .exceptionHandling( exception -> exception
                        .accessDeniedHandler(new myAccessDeniedHandler())
                        .authenticationEntryPoint(new myAuthenticationEntryPoint())
                )
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
        // Crea o instancia los roles, username y password
        if (Usuario == null) {
            Usuario = new UsuarioDTO();
            String plainPassword = "1234Abcd."; // Establecer la contraseña en texto plano
            Usuario.setPassword(plainPassword);
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String plainPassword = Usuario.getPassword(); // Obtener la contraseña en texto plano antes de la encriptación
        String hashedPassword = encoder.encode(plainPassword); // Encriptar la contraseña

        // Se puede usar WithDefaultPasswordEncoder pero está obsoleto y es preferible usar BCrypt de SpringSecurity
        UserDetails user = User.withUsername("user")
                .password(passwordEncoder().encode(hashedPassword))
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
