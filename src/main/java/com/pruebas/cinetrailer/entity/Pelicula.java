/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pruebas.cinetrailer.entity;

import java.time.LocalDate;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author aimer
 */
@Data
@Entity
@Table(name = "pelicula")
public class Pelicula {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idpelicula")
    private Integer id;
    
    @NotBlank
    @Column(name = "titulo")
    private String titulo;
    
    @NotBlank
    @Column(name = "sinopsis")
    private String sinopsis;
    
    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(name = "fecha_estreno")
    private LocalDate fechaEstreno;
    
    @NotBlank
    @Column(name = "youtube_trailer_id")
    private String youtubeTrailerId;
    
    
    @Column(name = "ruta_portada")
    private String rutaPortada;
    
    @NotEmpty
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "generopelicula", 
            joinColumns = @JoinColumn(name = "idpelicula"),
            inverseJoinColumns = @JoinColumn(name = "idgenero")
    )
    private List<Genero> generos;
    
    
    @Transient
    private MultipartFile portada;
    
}
