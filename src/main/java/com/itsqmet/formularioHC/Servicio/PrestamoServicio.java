package com.itsqmet.formularioHC.Servicio;

import com.itsqmet.formularioHC.Entidad.Prestamo;
import com.itsqmet.formularioHC.Repositorio.PrestamoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PrestamoServicio {

    @Autowired
    PrestamoRepositorio prestamoRepositorio;

    public List<Prestamo> listarPrestamos() {
        return prestamoRepositorio.findAll();
    }

    public Optional<Prestamo> buscarPrestamo(Long id){
        return prestamoRepositorio.findById(id);
    }

    public void guardarPrestamo(Prestamo prestamo){
        prestamoRepositorio.save(prestamo);
    }

    public void eliminarPrestamo(Long id){
        prestamoRepositorio.deleteById(id);
    }
}
