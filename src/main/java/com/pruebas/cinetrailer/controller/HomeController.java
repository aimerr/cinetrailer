/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pruebas.cinetrailer.controller;

import com.pruebas.cinetrailer.entity.Pelicula;
import com.pruebas.cinetrailer.repository.PeliculaRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.PathVariable;
/**
 *
 * @author aimer
 */
@Controller
@RequestMapping("")
public class HomeController {
    
    @Autowired
    private PeliculaRepository peliculaRepository;
    
    
    @GetMapping("")
    ModelAndView index() {
        List<Pelicula> ultimasPeliculas = peliculaRepository
                .findAll(PageRequest.of(0, 4, Sort.by("fechaEstreno").descending()))
                .toList();

        return new ModelAndView("index")
                .addObject("ultimasPeliculas", ultimasPeliculas);
    }
    
     @GetMapping("/peliculas")
    ModelAndView listaPeliculas(@PageableDefault(sort = "fechaEstreno", direction = Sort.Direction.DESC)
                                        Pageable pageable) {
        Page<Pelicula> peliculas = peliculaRepository.findAll(pageable);
        return new ModelAndView("peliculas")
                .addObject("peliculas", peliculas);
    }

    @GetMapping("/peliculas/{id}")
    ModelAndView detallesPelicula(@PathVariable Integer id) {
        Pelicula pelicula = peliculaRepository.getById(id);
        return new ModelAndView("pelicula")
                .addObject("pelicula", pelicula);
    }
    
    
}
