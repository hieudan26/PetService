package petservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import petservice.model.Entity.PetEntity;
import petservice.model.Entity.ServiceEntity;

import java.util.List;
import java.util.Optional;

@EnableJpaRepositories
public interface PetRepository extends JpaRepository<PetEntity, String> {
    Page<PetEntity> findAllByIdNotNull(Pageable pageable);
    Page<PetEntity> findAllByCategory(Pageable pageable, String category);
    Optional<PetEntity> findById(String id);
    void deleteById(String id);
}
