package com.proyectoFinal.portfolio.controllers;

import com.proyectoFinal.portfolio.models.EducacionModel;
import com.proyectoFinal.portfolio.services.EducacionService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
//@CrossOrigin(origins = "https://portfolioargprog.web.app")

@RequestMapping("/api/education")
public class EducacionController {

    @Autowired
    EducacionService educationService;

    @GetMapping("/all")
    public List<EducacionModel> getEducacion() {
        return educationService.getEducacion();
    }

    @GetMapping("/traerporid/{id}")
    public ResponseEntity<EducacionModel> findEducacion(@PathVariable("id") Long id) {
        EducacionModel edu = educationService.getEducationById(Long.MIN_VALUE);
        return new ResponseEntity(edu, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")//PARA Q SÓLO EL ROL "ADMIN" PUEDA EJECUTAR
    @PostMapping("/crear")
    public ResponseEntity createEducacion(@RequestBody EducacionModel edu) {
        educationService.saveEducacion(edu);
        return ResponseEntity.ok().body(edu);
    }
   
    @PreAuthorize("hasRole('ADMIN')")//PARA Q SÓLO EL ROL "ADMIN" PUEDA EJECUTAR
    @PutMapping("/editar/{id}")
    public ResponseEntity<?> editEducacion(@PathVariable("id") Long id, @RequestBody EducacionModel educacion) {
        EducacionModel edu = educationService.findEducacion(id);

        edu.setNivelInstitucion(educacion.getNivelInstitucion());
        edu.setFecha(educacion.getFecha());
        edu.setTitulo(educacion.getTitulo());

        educationService.saveEducacion(edu);
        return new ResponseEntity(HttpStatus.OK);
    }
    
    @PreAuthorize("hasRole('ADMIN')")//PARA Q SÓLO EL ROL "ADMIN" PUEDA EJECUTAR
    @DeleteMapping("/borrar/{id}")
    public ResponseEntity<?> eliminarEducacion(@PathVariable("id") Long id) {
        educationService.deleteEducacion(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}
