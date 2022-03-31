package petservice.repository;

import org.springframework.data.domain.Page;
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
    Page<BookingServiceEntity> findAllByIdNotNull(Pageable pageable);
    Page<BookingServiceEntity>  findAllByService(ServiceEntity service, Pageable pageable);
    Page<BookingServiceEntity>  findAllByUserBookService_UserName(String name, Pageable pageable);
    Page<BookingServiceEntity>  findAllByStatus(String status, Pageable pageable);
    Page<BookingServiceEntity>  findAllByPayment(boolean status, Pageable pageable);
    Page<BookingServiceEntity>  findAllByDateBooking(LocalDateTime time, Pageable pageable);
    Page<BookingServiceEntity> findALLByUserBookServiceAndServiceAndDateBooking(UserEntity user, ServiceEntity service, LocalDateTime time, Pageable pageable);
    Optional<BookingServiceEntity> findById(String id);

    Page<BookingServiceEntity> findALLByService_NameContaining(String Name, Pageable pageable);
    public Long countAllByDateBookingAndService(LocalDateTime time, ServiceEntity service);
}
