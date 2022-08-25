package com.proyectoFinal.portfolio.controllers;

import com.proyectoFinal.portfolio.models.ProyectoModel;
import com.proyectoFinal.portfolio.services.ProyectoService;
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
//@CrossOrigin(origins = "http://localhost:4200")
@CrossOrigin(origins = "https://portfolioargprog.firebaseapp.com")

@RequestMapping("/api/proyecto")
public class ProyectoController {
    
    @Autowired
    ProyectoService proyectoService;
    
    @GetMapping("/all")
    public List<ProyectoModel>getProyecto(){
        return proyectoService.getProyecto();
    }
    
    @GetMapping("/traerporid")
    public ProyectoModel findProyecto(){
        return proyectoService.findProyecto(Long.MIN_VALUE);
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/crear")
    public ResponseEntity createProyecto(@RequestBody ProyectoModel proyec){
        proyectoService.saveProyecto(proyec);
        return ResponseEntity.ok().body(proyec);
    }
   
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/borrar/{id}")
    public ResponseEntity<?> eliminarProyecto(@PathVariable("id") Long id) {
        proyectoService.deleteProyecto(id);
        return new ResponseEntity(HttpStatus.OK);
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/editar/{id}")
    public ResponseEntity<?> editProyecto(@PathVariable("id") Long id, @RequestBody ProyectoModel proyecto) {
        ProyectoModel proy = proyectoService.findProyecto(id);

        proy.setNombre(proyecto.getNombre());
        proy.setFecha(proyecto.getFecha());
        proy.setImagen(proyecto.getImagen());
        proy.setDescripcion(proyecto.getDescripcion());
        proy.setLink(proyecto.getLink());

        proyectoService.saveProyecto(proy);
        return new ResponseEntity(HttpStatus.OK);
    }
    
}
