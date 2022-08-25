package com.proyectoFinal.portfolio.controllers;

import com.proyectoFinal.portfolio.models.SkillModel;
import com.proyectoFinal.portfolio.services.SkillService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@CrossOrigin(origins = "http://localhost:4200")
@CrossOrigin(origins = "https://portfolioargprog.firebaseapp.com")

@RequestMapping("/api/skill")
public class SkillController {

    @Autowired
    SkillService skillService;

    @GetMapping("/all")
    public List<SkillModel> getAllSkills() {
        return skillService.getSkill();
    }
    
     @GetMapping("/traerporid/{id}")
    public ResponseEntity<SkillModel> findSkill(@PathVariable("id") Long id) {
        SkillModel skill = skillService.getSkillById(id);
        return new ResponseEntity(skill, HttpStatus.OK);
    }
    
    @GetMapping("/query")
    //ATENCIÓN...acá se tuvo q crear método en Repository para luego llamarlo en el Service y luego poder llamarlo acá.
    public List<SkillModel> getSkillByTipo(@RequestParam("tipo") String tipo) {
        return skillService.getSkillByTipo(tipo);
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/crear")
    public ResponseEntity createSkill(@RequestBody SkillModel skill){
        skillService.saveSkill(skill);
        return ResponseEntity.ok().body(skill);
    }
   
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/borrar/{id}")
    public ResponseEntity<?> eliminarSkill(@PathVariable("id") Long id) {
        skillService.deleteSkill(id);
        return new ResponseEntity(HttpStatus.OK);
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/editar/{id}")
    public ResponseEntity<?> editSkill(@PathVariable("id") Long id, @RequestBody SkillModel skill) {
        SkillModel sk = skillService.findSkill(id);

        sk.setTipo(skill.getTipo());
        sk.setNombre(skill.getNombre());
        sk.setPorcentaje(skill.getPorcentaje());

        skillService.saveSkill(sk);
        return new ResponseEntity(HttpStatus.OK);
    }
    
}
