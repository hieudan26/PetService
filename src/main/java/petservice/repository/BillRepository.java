package petservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import petservice.model.Entity.BillEntity;
import petservice.model.Entity.PetEntity;
import petservice.model.Entity.ServiceEntity;
import petservice.model.Entity.UserEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@EnableJpaRepositories
public interface BillRepository extends JpaRepository<BillEntity, String> {
    Page<BillEntity> findAllByIdNotNull(Pageable pageable);
    Page<BillEntity> findAllByUserBuyPet(UserEntity user, Pageable pageable);
    Optional<BillEntity> findByPetSale(PetEntity pet);
    Page<BillEntity> findAllByPaymentDate(LocalDateTime time, Pageable pageable);
    Page<BillEntity> findAllByUserBuyPetAndPaymentDate(UserEntity user, LocalDateTime time, Pageable pageable);
    Page<BillEntity> findAllByMethodPayment(String methodPayment, Pageable pageable);
    Optional<BillEntity> findById(String id);
    Boolean existsByPetSale(PetEntity pet);
    void deleteById(String id);
    void deleteByPetSale(PetEntity pet);
}
