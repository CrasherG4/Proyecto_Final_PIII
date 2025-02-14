package com.itsqmet.formularioHC.Repositorio;

import com.itsqmet.formularioHC.Entidad.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AutorRepositorio extends JpaRepository<Autor, Long> {
    List<Autor> findByNombreContainingIgnoreCase(String nombre);
}
