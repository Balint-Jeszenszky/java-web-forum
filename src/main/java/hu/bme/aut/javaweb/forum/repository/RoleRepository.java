package hu.bme.aut.javaweb.forum.repository;

import hu.bme.aut.javaweb.forum.model.ERole;
import hu.bme.aut.javaweb.forum.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
