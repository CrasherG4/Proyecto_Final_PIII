package com.itsqmet.formularioHC.Seguridad;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;
@Configuration
@EnableWebSecurity
public class AuthBibliotecario {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception{
        http
                .csrf(csrf->csrf.disable())
                .cors(cors->cors.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/**", "/libros").permitAll()
                        //.requestMatchers("/login","/nuevoUsuario","/guardarUser","/", "/nosotros", "/nuevoUsuario", "/guardarUser", "/bibliotecario", "/guardarBibliotecario").permitAll()
                        //.requestMatchers("/admin/**", "/editarBibliotecario/{id}", "/eliminarBibliotecario/{id}").hasRole("ADMIN")
                        //.requestMatchers("/prestamo", "/formularioPrestamo", "/guardarPrestamo").hasAnyRole("ADMIN", "CLIENTE", "EMPLEADO")
                        //.requestMatchers("/editarPrestamo/{id}", "/eliminarPrestamo/{id}", "/autores", "/formularioAutor", "/guardarAutor", "/editarAutor/{id}", "/eliminarAutor/{id}", "/autor/{id}", "/libros", "/formularioLibro", "/guardarLibro", "/editarLibro/{id}", "/eliminarLibro/{id}").hasAnyRole("ADMIN", "EMPLEADO")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .permitAll()
                        .defaultSuccessUrl("/postLogin",true)
                )
                .logout(logout ->logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                );
        return http.build();
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(List.of("*")); // Permitir todos los orígenes
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS")); // Métodos permitidos
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type", "Accept", "Origin", "X-Requested-With"));
        configuration.setAllowCredentials(true); // Permitir credenciales

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
