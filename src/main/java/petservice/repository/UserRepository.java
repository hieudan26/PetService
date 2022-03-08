package petservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import petservice.model.Entity.UserEntity;

import java.util.Optional;

@EnableJpaRepositories
public interface UserRepository extends JpaRepository<UserEntity,Integer> {
    Optional<UserEntity> findByUserName(String username);
    Optional<UserEntity> findByEmail(String email);
    Boolean existsByUserName(String username);
    Boolean existsByEmail(String email);
    Optional<UserEntity> deleteById(String id);
}
