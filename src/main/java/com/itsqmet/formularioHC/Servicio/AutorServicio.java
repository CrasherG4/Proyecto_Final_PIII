package com.itsqmet.formularioHC.Servicio;

import com.itsqmet.formularioHC.Entidad.Autor;
import com.itsqmet.formularioHC.Repositorio.AutorRepositorio;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AutorServicio {
    @Autowired
    AutorRepositorio autorRepositorio;

    public List<Autor> listarAutores() {

        return autorRepositorio.findAll();
    }

    public List<Autor> buscarAutorNombre(String buscarAutor) {
        if (buscarAutor == null || buscarAutor.isEmpty()) {
            return autorRepositorio.findAll();
        } else {
            return autorRepositorio.findByNombreContainingIgnoreCase(buscarAutor);
        }
    }

    public Optional<Autor> buscarAutor(Long id) {

        return autorRepositorio.findById(id);
    }

    public Autor guardarAutor(Autor autor) {

        return autorRepositorio.save(autor);
    }

    public void eliminarAutor(Long id) {
        autorRepositorio.deleteById(id);
    }

    //METODO PARA OBTENER PROVEEDOR CON PRODUCTOS
    @Transactional
    public Autor obtenerAutorConLibros(Long id) {
        Autor autor = autorRepositorio.findById(id).orElseThrow();
        return autor;
    }

}

