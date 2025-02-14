package com.itsqmet.formularioHC.Seguridad;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class AuthBibliotecario {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception{
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login","/nuevoUsuario","/guardarUser","/", "/nosotros", "/nuevoUsuario", "/guardarUser", "/bibliotecario", "/guardarBibliotecario").permitAll()
                        .requestMatchers("/admin/**", "/editarBibliotecario/{id}", "/eliminarBibliotecario/{id}").hasRole("ADMIN")
                        .requestMatchers("/prestamo", "/formularioPrestamo", "/guardarPrestamo").hasAnyRole("ADMIN", "CLIENTE", "EMPLEADO")
                        .requestMatchers("/editarPrestamo/{id}", "/eliminarPrestamo/{id}", "/autores", "/formularioAutor", "/guardarAutor", "/editarAutor/{id}", "/eliminarAutor/{id}", "/autor/{id}", "/libros", "/formularioLibro", "/guardarLibro", "/editarLibro/{id}", "/eliminarLibro/{id}").hasAnyRole("ADMIN", "EMPLEADO")
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

}
