package petservice.Service.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import petservice.Service.BookingServiceService;
import petservice.mapping.BookingServiceMapping;
import petservice.model.Entity.BookingServiceEntity;
import petservice.model.Entity.ServiceEntity;
import petservice.model.Entity.UserEntity;
import petservice.model.payload.request.BookingServiceResources.InfoBookingServiceRequest;
import petservice.repository.BookingServiceRepository;

import javax.transaction.Transactional;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class BookingSerivceSerivceImpl implements BookingServiceService {

   final BookingServiceRepository bookingServiceRepository;

   @Autowired
    BookingServiceMapping bookingServiceMapping;

    @Override
    public Page<BookingServiceEntity> getAllBookingService(Pageable pageable) {
        if (bookingServiceRepository.findAllByIdNotNull(pageable).isEmpty()){
            return null;
        }
        return bookingServiceRepository.findAllByIdNotNull(pageable);
    }

    @Override
    public Page<BookingServiceEntity> getAllByService(ServiceEntity service, Pageable pageable) {
        if (bookingServiceRepository.findAllByService(service, pageable).isEmpty()){
            return null;
        }
        return bookingServiceRepository.findAllByService(service, pageable);
    }

    @Override
    public Page<BookingServiceEntity> getAllByUserBookServiceName(String name, Pageable pageable) {
        if (bookingServiceRepository.findAllByUserBookService_UserName(name, pageable).isEmpty()){
            return null;
        }
        return bookingServiceRepository.findAllByUserBookService_UserName(name, pageable);
    }

    @Override
    public Page<BookingServiceEntity> getAllByStatus(String status, Pageable pageable) {
        if (bookingServiceRepository.findAllByStatus(status, pageable).isEmpty()){
            return null;
        }
        return bookingServiceRepository.findAllByStatus(status, pageable);
    }

    @Override
    public Page<BookingServiceEntity> getAllByPayment(boolean status, Pageable pageable) {
        if (bookingServiceRepository.findAllByPayment(status, pageable).isEmpty()){
            return null;
        }
        return bookingServiceRepository.findAllByPayment(status, pageable);
    }

    @Override
    public Page<BookingServiceEntity> getAllByDateBooking(LocalDateTime time, Pageable pageable) {
        if (bookingServiceRepository.findAllByDateBooking(time, pageable).isEmpty()){
            return null;
        }
        return bookingServiceRepository.findAllByDateBooking(time, pageable);
    }

    @Override
    public Page<BookingServiceEntity> getAllByUserBookServiceAndServiceAndDateBooking(UserEntity user, ServiceEntity service, LocalDateTime time, Pageable pageable) {
        if (bookingServiceRepository.findALLByUserBookServiceAndServiceAndDateBooking(user, service, time, pageable).isEmpty()){
            return null;
        }
        return bookingServiceRepository.findALLByUserBookServiceAndServiceAndDateBooking(user, service, time, pageable);
    }

    @Override
    public Page<BookingServiceEntity> getALLByService_NameContaining(String Name, Pageable pageable) {
        if (bookingServiceRepository.findALLByService_NameContaining(Name, pageable ).isEmpty()){
            return null;
        }
        return bookingServiceRepository.findALLByService_NameContaining(Name, pageable );
    }

    @Override
    public BookingServiceEntity saveBookingService(BookingServiceEntity bookingService) {
        return bookingServiceRepository.save(bookingService);
    }

    @Override
    public BookingServiceEntity updateBookingServiceInfo(BookingServiceEntity bookingService, InfoBookingServiceRequest bookingServiceInfo) throws Exception {
        bookingService =  bookingServiceMapping.UpdateBookingServiceByInfo(bookingService, bookingServiceInfo);
        if (!this.isAvailableService(bookingService)){
            throw new Exception("service not available");
        }else{
            return bookingServiceRepository.save(bookingService);
        }
    }

    @Override
    public Long countAllByDateBookingAndService(LocalDateTime time, ServiceEntity service) {
        return bookingServiceRepository.countAllByDateBookingAndService(time,service);
    }

    @Override
    public Boolean isAvailableService(BookingServiceEntity newBooking) throws Exception {
        if (newBooking.getDateBooking().isBefore(LocalDateTime.now(ZoneId.of("GMT+07:00")))) {
            throw new Exception("Datatime is not valid");
        }

        BigInteger maxSlot = newBooking.getService().getSlot();
        Long slot =  this.countAllByDateBookingAndService(newBooking.getDateBooking(), newBooking.getService());
        if (slot.intValue() < maxSlot.intValue()){
            return true;
        }
        return false;
    }

    @Override
    public BookingServiceEntity findById(String id) {
        Optional<BookingServiceEntity> booking = bookingServiceRepository.findById(id);
        if (booking.isEmpty()){
            return null;
        }
        return booking.get();
    }
}
