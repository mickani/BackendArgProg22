package com.proyectoFinal.portfolio.controllers;

import com.proyectoFinal.portfolio.models.PersonaModel;
import com.proyectoFinal.portfolio.services.PersonaService;
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
@CrossOrigin(origins = "https://portfolioargprog.web.app")

@RequestMapping("/api/persona")//Me ahorra escribir a todos los endpoint, la url.
public class PersonaController {

    @Autowired
    PersonaService personaService;

    @GetMapping("/all")
    public List<PersonaModel> getPersona() {
        return personaService.getPersona();
    }

    @GetMapping("/traer")
    public PersonaModel findPersona() {
        return personaService.findPersona((long) 3);
    }

    @GetMapping("/traerporid/{id}")
    public ResponseEntity<PersonaModel> findPersonById(@PathVariable("id") Long id) {
        PersonaModel perso = personaService.findById(id);
        return new ResponseEntity(perso, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/crear")
    public String createPersona(@RequestBody PersonaModel persona) {
        personaService.savePersona(persona);
        return "La persona se creó correctamente.";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/borrar/{id}")
    public String deletePersona(@PathVariable Long id) {
        personaService.deletePersona(id);
        return "La persona se eliminó correctamente.";
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/editar/{id}")
    public ResponseEntity <?> editPersona(@PathVariable("id") Long id, @RequestBody PersonaModel persona) {
        PersonaModel perso = personaService.findPersona(id);

        perso.setNombre(persona.getNombre());
        perso.setApellido(persona.getApellido());
        perso.setTitulo(persona.getTitulo());
        perso.setDescripcion(persona.getDescripcion());
        perso.setImage_perfil(persona.getImage_perfil());
        perso.setBanner(persona.getBanner());

        personaService.savePersona(perso);
        return new ResponseEntity(HttpStatus.OK);
    }
    
}
