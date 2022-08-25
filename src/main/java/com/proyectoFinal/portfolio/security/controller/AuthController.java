package com.proyectoFinal.portfolio.security.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import com.proyectoFinal.portfolio.security.dto.JwtDto;
import com.proyectoFinal.portfolio.security.dto.LoginUsuario;
import com.proyectoFinal.portfolio.security.dto.NuevoUsuario;
import com.proyectoFinal.portfolio.security.entity.Rol;
import com.proyectoFinal.portfolio.security.entity.Usuario;
import com.proyectoFinal.portfolio.security.enums.RolNombre;
import com.proyectoFinal.portfolio.security.jwt.JwtProvider;
import com.proyectoFinal.portfolio.security.service.RolService;
import com.proyectoFinal.portfolio.security.service.UsuarioService;
import java.util.HashSet;
import java.util.Set;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
//@CrossOrigin(origins = "http://localhost:4200")
@CrossOrigin(origins = "https://portfolioargprog.web.app")
public class AuthController {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    RolService rolService;

    @Autowired
    JwtProvider jwtProvider;

    
    @PostMapping("nuevo")      
    public ResponseEntity<?> nuevo(@Valid @RequestBody NuevoUsuario nuevoUsuario,
            BindingResult bindingResult) {
        String m = "Campos mal puestos o mail inválido";
        if (bindingResult.hasErrors()) {
            return new ResponseEntity(m, HttpStatus.BAD_REQUEST);
        }

        String m2 = "Ese nombre ya existe";
        if (usuarioService.existsByNombreUsuario(nuevoUsuario.getNombreUsuario())) {
            return new ResponseEntity(m2, HttpStatus.BAD_REQUEST);
        }

        String m3 = "Ese mail ya existe";
        if (usuarioService.existsByMail(nuevoUsuario.getMail())) 
            return new ResponseEntity(m3, HttpStatus.BAD_REQUEST);

        Usuario usuario = new Usuario(nuevoUsuario.getNombreUsuario(), nuevoUsuario.getMail(),
                passwordEncoder.encode(nuevoUsuario.getPassword()));
        Set<Rol> roles = new HashSet<>();
        roles.add(rolService.getByRolnombre(RolNombre.ROLE_USER).get());

        if (nuevoUsuario.getRoles().contains("admin")) 
            roles.add(rolService.getByRolnombre(RolNombre.ROLE_ADMIN).get());

        usuario.setRoles(roles);
        usuarioService.save(usuario);

        String m4 = "Usuario Guardado";
        return new ResponseEntity(m4, HttpStatus.CREATED);
    }

    @PostMapping("/login")             
    public ResponseEntity<JwtDto> login(@Valid @RequestBody LoginUsuario loginUsuario,
            BindingResult bindingResult) {
        String m5="Campos Mal Puestos";
        if (bindingResult.hasErrors()) 
            return new ResponseEntity(m5, HttpStatus.BAD_REQUEST);
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUsuario.getNombreUsuario(), loginUsuario.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtProvider.generateToken(authentication);
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            JwtDto jwtDto = new JwtDto(jwt, userDetails.getUsername(), userDetails.getAuthorities());
            return new ResponseEntity(jwtDto, HttpStatus.OK);
    }
}
