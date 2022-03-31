package petservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import petservice.model.Entity.UserEntity;

import java.util.Optional;

@EnableJpaRepositories
public interface UserRepository extends JpaRepository<UserEntity,String> {
    Page<UserEntity> findAllByIdNotNull(Pageable pageable);
    Optional<UserEntity> findByUserName(String username);
    Optional<UserEntity> findByEmail(String email);
    Boolean existsByUserName(String username);
    Boolean existsByEmail(String email);
    void deleteById(String id);
}
