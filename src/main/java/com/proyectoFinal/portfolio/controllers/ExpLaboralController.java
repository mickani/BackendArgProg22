package com.proyectoFinal.portfolio.controllers;

import com.proyectoFinal.portfolio.models.ExpLaboralModel;
import com.proyectoFinal.portfolio.services.ExpLaboralService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@CrossOrigin (origins = "http://localhost:4200")
@RequestMapping("/api/explaboral")//Me ahorra escribir a todos los endpoint, la url.
public class ExpLaboralController {
    
    @Autowired
    ExpLaboralService expLaboralService;
    
    @GetMapping("/all")
    public List<ExpLaboralModel>getExpLaboral(){
        return expLaboralService.getExpLaboral();
    }
    
    @GetMapping("/traerporid/{id}")
    public ResponseEntity<ExpLaboralModel> findExpLaboral(@PathVariable("id") Long id) {
        ExpLaboralModel explab = expLaboralService.getExpLaboralById(Long.MIN_VALUE);
        return new ResponseEntity(explab, HttpStatus.OK);
    }
    
    @PostMapping("/crear")
    public ResponseEntity createExpLaboral(@RequestBody ExpLaboralModel lab){
        expLaboralService.saveExpLaboral(lab);
        return ResponseEntity.ok().body(lab);
    }
    
    @DeleteMapping("/borrar/{id}")
    public ResponseEntity<?> eliminarExpLaboral(@PathVariable("id") Long id) {
        expLaboralService.deleteExpLaboral(id);
        return new ResponseEntity(HttpStatus.OK);
    }
    
    @PutMapping("/editar/{id}")
    public ResponseEntity<?> editExpLaboral(@PathVariable("id") Long id, @RequestBody ExpLaboralModel expLaboral) {
        ExpLaboralModel expL = expLaboralService.findExpLaboral(id);

        expL.setEmpresa(expLaboral.getEmpresa());
        expL.setPeriodo(expLaboral.getPeriodo());
        expL.setDescripcion(expLaboral.getDescripcion());
        
        expLaboralService.saveExpLaboral(expL);
        return new ResponseEntity(HttpStatus.OK);
    }
}
