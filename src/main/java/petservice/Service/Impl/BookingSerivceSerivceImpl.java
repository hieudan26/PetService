package petservice.Service.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class BookingSerivceSerivceImpl implements BookingServiceService {

   final BookingServiceRepository bookingServiceRepository;

    @Override
    public List<BookingServiceEntity> getAllBookingService(Pageable pageable) {
        return bookingServiceRepository.findAllByIdNotNull(pageable);
    }

    @Override
    public List<BookingServiceEntity> getAllByService(ServiceEntity service, Pageable pageable) {
        return bookingServiceRepository.findAllByService(service, pageable);
    }

    @Override
    public List<BookingServiceEntity> getAllByUserBookServiceName(String name, Pageable pageable) {
        return bookingServiceRepository.findAllByUserBookService_UserName(name, pageable);
    }

    @Override
    public List<BookingServiceEntity> getAllByStatus(String status, Pageable pageable) {
        return bookingServiceRepository.findAllByStatus(status, pageable);
    }

    @Override
    public List<BookingServiceEntity> getAllByPayment(boolean status, Pageable pageable) {
        return bookingServiceRepository.findAllByPayment(status, pageable);
    }

    @Override
    public List<BookingServiceEntity> getAllByDateBooking(LocalDateTime time, Pageable pageable) {
        return bookingServiceRepository.findAllByDateBooking(time, pageable);
    }

    @Override
    public List<BookingServiceEntity> getAllByUserBookServiceAndServiceAndDateBooking(UserEntity user, ServiceEntity service, LocalDateTime time, Pageable pageable) {
        return bookingServiceRepository.findALLByUserBookServiceAndServiceAndDateBooking(user, service, time, pageable);
    }

    @Override
    public List<BookingServiceEntity> getALLByService_NameContaining(String Name, Pageable pageable) {
        return bookingServiceRepository.findALLByService_NameContaining(Name, pageable );
    }

    @Override
    public BookingServiceEntity saveBookingService(BookingServiceEntity bookingService) {
        return bookingServiceRepository.save(bookingService);
    }

    @Override
    public BookingServiceEntity updateBookingServiceInfo(BookingServiceEntity bookingService, InfoBookingServiceRequest bookingServiceInfo) throws Exception {
        bookingService =  BookingServiceMapping.UpdateBookingServiceByInfo(bookingService, bookingServiceInfo);
        return bookingServiceRepository.save(bookingService);
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
        return bookingServiceRepository.findById(id).get();
    }
}
