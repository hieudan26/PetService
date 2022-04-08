package petservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import petservice.model.Entity.ServiceEntity;

import java.util.List;
import java.util.Optional;

@EnableJpaRepositories
public interface ServiceRepository extends JpaRepository<ServiceEntity, String> {
    Page<ServiceEntity> findAllByIdNotNull(Pageable pageable);
    Optional<ServiceEntity> findByName(String name);
    Optional<ServiceEntity> findById(String id);
    List<ServiceEntity> findAllByNameContaining(String name);
    Boolean existsByName(String name);
    void deleteById(String id);
}
