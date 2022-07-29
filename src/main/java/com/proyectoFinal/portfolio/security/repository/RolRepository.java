
package com.proyectoFinal.portfolio.security.repository;

import com.proyectoFinal.portfolio.security.entity.Rol;
import com.proyectoFinal.portfolio.security.enums.RolNombre;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolRepository extends JpaRepository<Rol, Integer>{
    Optional<Rol>findByRolNombre(RolNombre rolNombre);
}
