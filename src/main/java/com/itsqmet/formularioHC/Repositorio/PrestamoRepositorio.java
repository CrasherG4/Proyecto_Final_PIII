package com.itsqmet.formularioHC.Repositorio;

import com.itsqmet.formularioHC.Entidad.Prestamo;
import com.itsqmet.formularioHC.Entidad.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PrestamoRepositorio extends JpaRepository<Prestamo, Long> {
    //---xxxxxxxx---------------------------
    List<Prestamo> findByLibro_Id(Long id_libro);

    List<Prestamo> findByBibliotecario_Id(Long id_bibliotecario);
}
