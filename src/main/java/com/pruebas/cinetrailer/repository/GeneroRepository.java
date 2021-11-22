/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pruebas.cinetrailer.repository;

import com.pruebas.cinetrailer.entity.Genero;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author aimer
 */
public interface GeneroRepository extends JpaRepository<Genero,Integer>{
    
}
