package com.itsqmet.formularioHC.Servicio;

import com.itextpdf.text.*;

import com.itsqmet.formularioHC.Entidad.Usuario;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;





import com.itsqmet.formularioHC.Entidad.Bibliotecario;
import com.itsqmet.formularioHC.Repositorio.BibliotecarioRepositorio;
import com.itsqmet.formularioHC.Roles.Rol;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Optional;

@Service
public class BibliotecarioServicio  implements UserDetailsService{


    @Autowired
    private BibliotecarioRepositorio bibliotecarioRepositorio;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<Bibliotecario> listarUsuarios(){

        return bibliotecarioRepositorio.findAll();
    }

    public List<Bibliotecario> buscarBibliotecarioNombre(String buscarBbibliotecario){
        if(buscarBbibliotecario==null || buscarBbibliotecario.isEmpty()){
            return bibliotecarioRepositorio.findAll();
        }else{
            return bibliotecarioRepositorio.findByNombreContainingIgnoreCase(buscarBbibliotecario);
        }
    }


    public Optional<Bibliotecario> buscarBibliotecario(Long id){

        return bibliotecarioRepositorio.findById(id);
    }



    @Transactional
    public Bibliotecario registrar(Long id, String nombre, String username, String password, Rol rol) {
        Bibliotecario bibliotecario;

        // Si el ID no es nulo, busca un bibliotecario existente, si no, crea uno nuevo.
        if (id != null) {
            bibliotecario = bibliotecarioRepositorio.findById(id).orElse(new Bibliotecario());
        } else {
            bibliotecario = new Bibliotecario();
        }

        // Asignación de propiedades.
        bibliotecario.setNombre(nombre);
        bibliotecario.setUsername(username);

        // Codificación de la contraseña solo si es válida.
        if (password != null && !password.isEmpty()) {
            bibliotecario.setPassword(passwordEncoder.encode(password));
        }

        bibliotecario.setRol(rol);

        // Guarda el bibliotecario y retorna el objeto guardado.
        return bibliotecarioRepositorio.save(bibliotecario);
    }



    public void eliminarBbibliotecario(Long id){
        bibliotecarioRepositorio.deleteById(id);
    }

    public List<Bibliotecario> listarBibliotecariosPorIds(List<Long> ids) {
        return bibliotecarioRepositorio.findAllById(ids);
    }


    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException{
        Bibliotecario bibliotecario= bibliotecarioRepositorio.findByUsername(username);
        if (bibliotecario!=null){
            return new User(bibliotecario.getUsername(), bibliotecario.getPassword(),
                    List.of(new SimpleGrantedAuthority("ROLE_"+bibliotecario.getRol().toString())));
        }else {
            throw new UsernameNotFoundException("Usuario no encontrado");
        }

    }

    @Transactional
    public Bibliotecario obtenerBibliotecarioConPrestamo(Long id) {
        Bibliotecario bibliotecario = bibliotecarioRepositorio.findById(id).orElseThrow();
        return bibliotecario;
    }

}
