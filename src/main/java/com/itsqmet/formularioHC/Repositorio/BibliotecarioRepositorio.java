package com.itsqmet.formularioHC.Repositorio;

import com.itsqmet.formularioHC.Entidad.Bibliotecario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BibliotecarioRepositorio extends JpaRepository<Bibliotecario, Long> {
    List<Bibliotecario> findByNombreContainingIgnoreCase(String nombre);
    Bibliotecario findByUsername(String username);
}
