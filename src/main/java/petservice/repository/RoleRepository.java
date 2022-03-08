package petservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import petservice.model.Entity.RoleEntity;

import java.util.Optional;

@EnableJpaRepositories
public interface RoleRepository extends JpaRepository<RoleEntity,Integer> {
    Optional<RoleEntity> findByName(String roleName);
    Boolean existsByName(String roleName);
}
