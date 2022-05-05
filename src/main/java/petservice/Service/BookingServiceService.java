package petservice.Service;

import org.apache.catalina.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import petservice.model.Entity.BookingServiceEntity;
import petservice.model.Entity.ServiceEntity;
import petservice.model.Entity.UserEntity;
import petservice.model.payload.request.BookingServiceResources.AddListBookingServiceByCustomerRequest;
import petservice.model.payload.request.BookingServiceResources.AdminInfoBookingServiceRequest;
import petservice.model.payload.request.BookingServiceResources.InfoBookingServiceRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Component
public interface BookingServiceService {
    Page<BookingServiceEntity> getAllBookingService(Pageable pageable);
    Page<BookingServiceEntity>  getAllByService(ServiceEntity service, Pageable pageable);
    Page<BookingServiceEntity>  getAllByUserBookServiceName(String name, Pageable pageable);
    Page<BookingServiceEntity>  getAllByStatus(String status, Pageable pageable);
    Page<BookingServiceEntity>  getAllByPayment(boolean status, Pageable pageable);
    Page<BookingServiceEntity>  getAllByDateBooking(LocalDateTime time, Pageable pageable);
    Page<BookingServiceEntity> getAllByUserBookServiceAndServiceAndDateBooking(UserEntity user, ServiceEntity service, LocalDateTime time, Pageable pageable);
    Page<BookingServiceEntity> getALLByService_NameContaining(String Name, Pageable pageable);

    BookingServiceEntity saveBookingService (BookingServiceEntity bookingService);
    BookingServiceEntity updateBookingServiceInfoCustomer(BookingServiceEntity bookingService, InfoBookingServiceRequest bookingServiceInfo, UserEntity user) throws Exception;

    BookingServiceEntity adminUpdateBookingServiceInfoCustomer(BookingServiceEntity bookingServiceEntity,  AdminInfoBookingServiceRequest bookingServiceInfo) throws Exception;

    public Long countAllByDateBookingAndService(LocalDateTime time, ServiceEntity service);
    public Boolean isAvailableService(BookingServiceEntity newBooking) throws Exception;
    public Boolean isUpdateAvailableService(LocalDateTime time, ServiceEntity service) throws Exception;
    BookingServiceEntity  findByIdAndUserBookService(String id, UserEntity user);

    List<BookingServiceEntity>  saveListBookingService (AddListBookingServiceByCustomerRequest bookingInfo, UserEntity user) throws Exception;
    BookingServiceEntity findById(String id);

    Boolean isFullSlotService(LocalDateTime date, ServiceEntity service);
}
