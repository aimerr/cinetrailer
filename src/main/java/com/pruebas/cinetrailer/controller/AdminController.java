/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pruebas.cinetrailer.controller;

import com.pruebas.cinetrailer.Service.FileSystemStorageService;
import com.pruebas.cinetrailer.entity.Genero;
import com.pruebas.cinetrailer.entity.Pelicula;
import com.pruebas.cinetrailer.repository.GeneroRepository;
import com.pruebas.cinetrailer.repository.PeliculaRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author aimer
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private PeliculaRepository peliculaRepository;

    @Autowired
    private GeneroRepository generoRepository;

    @Autowired
    private FileSystemStorageService fileSystemStorageService;

    @GetMapping("")
    ModelAndView index(@PageableDefault(sort = "titulo", size = 5) Pageable pageable) {
        Page<Pelicula> peliculas = peliculaRepository.findAll(pageable);
        return new ModelAndView("admin/index")
                .addObject("peliculas", peliculas);
    }

    @GetMapping("/peliculas/nuevo")
    ModelAndView nuevaPelicula() {

        List<Genero> generos = generoRepository.findAll(Sort.by("titulo"));

        return new ModelAndView("admin/nueva-pelicula")
                .addObject("pelicula", new Pelicula())
                .addObject("generos", generos);
    }

    @PostMapping("/peliculas/nuevo")
    ModelAndView crearPelicula(@Validated Pelicula pelicula, BindingResult bindingResult) {
          List<Genero> generos = generoRepository.findAll(Sort.by("titulo"));
        if (bindingResult.hasErrors() || pelicula.getPortada().isEmpty()) {

            if (pelicula.getPortada().isEmpty()) {
                bindingResult.rejectValue("portada", "MultipartNotEmpty");
            }

          

            return new ModelAndView("admin/nueva-pelicula")
                    .addObject("pelicula", pelicula)
                    .addObject("generos", generos);
        } else {
            
            Pelicula peliculaFromDb = peliculaRepository.findByTitulo(pelicula.getTitulo());
            if(peliculaFromDb != null){
                 bindingResult.rejectValue("titulo", "ExistsThisMovie");
                return new ModelAndView("admin/nueva-pelicula")
                    .addObject("pelicula", pelicula)
                    .addObject("generos", generos);
            }
            
            
            String rutaPortada = fileSystemStorageService.storage(pelicula.getPortada(),pelicula.getTitulo());

            pelicula.setRutaPortada(rutaPortada);

            peliculaRepository.save(pelicula);
            return new ModelAndView("redirect:/admin");
        }
    }

    @GetMapping("/peliculas/{id}/editar")
    ModelAndView editarPelicula(@PathVariable Integer id) {

        Pelicula pelicula = peliculaRepository.getById(id);
        List<Genero> generos = generoRepository.findAll(Sort.by("titulo"));

        return new ModelAndView("admin/editar-pelicula")
                .addObject("pelicula", pelicula)
                .addObject("generos", generos);
    }

    @PostMapping("/peliculas/{id}/editar")
    ModelAndView actualizarPelicula(@PathVariable Integer id,
            @Validated Pelicula pelicula, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            List<Genero> generos = generoRepository.findAll(Sort.by("titulo"));

            return new ModelAndView("admin/editar-pelicula")
                    .addObject("pelicula", pelicula)
                    .addObject("generos", generos);
        } else {

            Optional<Pelicula> peliculaFromDb = peliculaRepository.findById(id);
            
            peliculaFromDb.get().setTitulo(pelicula.getTitulo());
            peliculaFromDb.get().setSinopsis(pelicula.getSinopsis());
            peliculaFromDb.get().setFechaEstreno(pelicula.getFechaEstreno());
            peliculaFromDb.get().setYoutubeTrailerId(pelicula.getYoutubeTrailerId());
            peliculaFromDb.get().setGeneros(pelicula.getGeneros());

            if (!pelicula.getPortada().isEmpty()) {

                fileSystemStorageService.delete(peliculaFromDb.get().getRutaPortada());
                String rutaPortada = fileSystemStorageService.storage(pelicula.getPortada(),pelicula.getTitulo());
                peliculaFromDb.get().setRutaPortada(rutaPortada);
            }
            
            peliculaRepository.save(peliculaFromDb.get());
            
            return new ModelAndView("redirect:/admin");
        }

    }


    @GetMapping("/peliculas/{id}/eliminar")
    String eliminarPelicula(@PathVariable Integer id){
    
        Pelicula pelicula = peliculaRepository.getById(id);
        peliculaRepository.delete(pelicula);
        fileSystemStorageService.delete(pelicula.getRutaPortada());
    
        
         return ("redirect:/admin");
    }
}
