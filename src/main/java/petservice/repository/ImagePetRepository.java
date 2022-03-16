package petservice.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import petservice.model.Entity.ImagePetEntity;
import petservice.model.Entity.PetEntity;

import java.util.List;
import java.util.Optional;

@EnableJpaRepositories
public interface ImagePetRepository extends JpaRepository<ImagePetEntity, String> {
    List<PetEntity> findAllByIdNotNull(Pageable pageable);
    Optional<ImagePetEntity> findById(String id);
    List<ImagePetEntity> findByPet(PetEntity pet);
    void deleteByPet(PetEntity pet);
}
