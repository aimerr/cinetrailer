/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pruebas.cinetrailer.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

/**
 *
 * @author aimer
 */

@Data
@Entity
@Table(name = "genero")
public class Genero {
    
    @Id
    @Column(name = "idgenero")
    private Integer id;
    
    @Column(name = "titulo")
    private String titulo;
}
