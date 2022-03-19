package petservice.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import petservice.model.Entity.BookingServiceEntity;
import petservice.model.Entity.ServiceEntity;
import petservice.model.Entity.UserEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@EnableJpaRepositories
public interface BookingServiceRepository extends JpaRepository<BookingServiceEntity, String> {
    List<BookingServiceEntity> findAllByIdNotNull(Pageable pageable);
    List<BookingServiceEntity>  findAllByService(ServiceEntity service, Pageable pageable);
    List<BookingServiceEntity>  findAllByUserBookService_UserName(String name, Pageable pageable);
    List<BookingServiceEntity>  findAllByStatus(String status, Pageable pageable);
    List<BookingServiceEntity>  findAllByPayment(boolean status, Pageable pageable);
    List<BookingServiceEntity>  findAllByDateBooking(LocalDateTime time, Pageable pageable);
    List<BookingServiceEntity> findALLByUserBookServiceAndServiceAndDateBooking(UserEntity user, ServiceEntity service, LocalDateTime time, Pageable pageable);
    Optional<BookingServiceEntity> findById(String id);

    List<BookingServiceEntity> findALLByService_NameContaining(String Name, Pageable pageable);
    public Long countAllByDateBookingAndService(LocalDateTime time, ServiceEntity service);
}
