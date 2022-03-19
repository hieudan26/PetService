package petservice.Service;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import petservice.model.Entity.BookingServiceEntity;
import petservice.model.Entity.ServiceEntity;
import petservice.model.Entity.UserEntity;
import petservice.model.payload.request.BookingServiceResources.InfoBookingServiceRequest;

import java.time.LocalDateTime;
import java.util.List;


@Component
public interface BookingServiceService {
    List<BookingServiceEntity> getAllBookingService(Pageable pageable);
    List<BookingServiceEntity>  getAllByService(ServiceEntity service, Pageable pageable);
    List<BookingServiceEntity>  getAllByUserBookServiceName(String name, Pageable pageable);
    List<BookingServiceEntity>  getAllByStatus(String status, Pageable pageable);
    List<BookingServiceEntity>  getAllByPayment(boolean status, Pageable pageable);
    List<BookingServiceEntity>  getAllByDateBooking(LocalDateTime time, Pageable pageable);
    List<BookingServiceEntity> getAllByUserBookServiceAndServiceAndDateBooking(UserEntity user, ServiceEntity service, LocalDateTime time, Pageable pageable);
    List<BookingServiceEntity> getALLByService_NameContaining(String Name, Pageable pageable);

    BookingServiceEntity saveBookingService (BookingServiceEntity bookingService);
    BookingServiceEntity updateBookingServiceInfo(BookingServiceEntity bookingService, InfoBookingServiceRequest bookingServiceInfo) throws Exception;

    public Long countAllByDateBookingAndService(LocalDateTime time, ServiceEntity service);
    public Boolean isAvailableService(BookingServiceEntity newBooking) throws Exception;

    BookingServiceEntity findById(String id);
}
