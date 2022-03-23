package petservice.repository;

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
    List<BillEntity> findAllByIdNotNull(Pageable pageable);
    List<BillEntity> findAllByUserBuyPet(UserEntity user, Pageable pageable);
    Optional<BillEntity> findByPetSale(PetEntity pet);
    List<BillEntity> findAllByPaymentDate(LocalDateTime time, Pageable pageable);
    List<BillEntity> findAllByUserBuyPetAndPaymentDate(UserEntity user, LocalDateTime time, Pageable pageable);
    List<BillEntity> findAllByMethodPayment(String methodPayment, Pageable pageable);
    Optional<BillEntity> findById(String id);
    Boolean existsByPetSale(PetEntity pet);
    void deleteById(String id);
    void deleteByPetSale(PetEntity pet);
}
