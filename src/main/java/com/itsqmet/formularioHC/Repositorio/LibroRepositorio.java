package com.itsqmet.formularioHC.Repositorio;


import com.itsqmet.formularioHC.Entidad.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LibroRepositorio extends JpaRepository<Libro, Long> {
    List<Libro> findByTituloContainingIgnoreCase(String titulo);
    List<Libro> findByAutor_Id(Long id_autor);
}
