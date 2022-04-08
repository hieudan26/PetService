package petservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.lang.Nullable;
import petservice.model.Entity.UserEntity;

import java.util.Optional;

@EnableJpaRepositories
public interface UserRepository extends JpaRepository<UserEntity,String>, JpaSpecificationExecutor<UserEntity>{
    Page<UserEntity> findAllByIdNotNull(Pageable pageable);
    Page<UserEntity> findAllByIdNotNull(Specification<UserEntity> spec,Pageable pageable);
    Optional<UserEntity> findByUserName(String username);
    Optional<UserEntity> findByEmail(String email);
    Boolean existsByUserName(String username);
    Boolean existsByEmail(String email);
    void deleteById(String id);
}
