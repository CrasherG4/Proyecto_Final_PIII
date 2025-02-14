package com.itsqmet.formularioHC.Servicio;

import com.itsqmet.formularioHC.Entidad.Usuario;
import com.itsqmet.formularioHC.Repositorio.UsuarioRepositorio;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServicio {
    @Autowired
    UsuarioRepositorio usuarioRepositorio;

    public List<Usuario> listarUsuarios() {

        return usuarioRepositorio.findAll();
    }

    public List<Usuario> buscarUsuarioNombre(String buscarUsuario) {
        if (buscarUsuario == null || buscarUsuario.isEmpty()) {
            return usuarioRepositorio.findAll();
        } else {
            return usuarioRepositorio.findByNombreContainingIgnoreCase(buscarUsuario);
        }
    }

    public Optional<Usuario> buscarUsuario(Long id) {

        return usuarioRepositorio.findById(id);
    }

    public void guardarUsuario(Usuario usuario) {

        usuarioRepositorio.save(usuario);
    }

    public void eliminarUsuario(Long id) {
        usuarioRepositorio.deleteById(id);
    }

    @Transactional
    public Usuario obtenerUsuarioConPrestamo(Long id) {
        Usuario usuario = usuarioRepositorio.findById(id).orElseThrow();
        return usuario;
    }

}
