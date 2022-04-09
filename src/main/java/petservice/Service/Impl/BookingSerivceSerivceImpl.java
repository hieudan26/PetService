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
import petservice.model.payload.request.BookingServiceResources.AddBookingServiceRequest;
import petservice.model.payload.request.BookingServiceResources.AddListBookingServiceByCustomerRequest;
import petservice.model.payload.request.BookingServiceResources.InfoBookingServiceRequest;
import petservice.repository.BookingServiceRepository;
import petservice.repository.ServiceRepository;

import javax.transaction.Transactional;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class BookingSerivceSerivceImpl implements BookingServiceService {

   final BookingServiceRepository bookingServiceRepository;

   final ServiceRepository serviceRepository;

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
        bookingServiceRepository.save(bookingService);
        if (isFullSlotService(bookingService.getDateBooking(), bookingService.getService())){
            ServiceEntity service = bookingService.getService();
            service.setStatus(false);
            serviceRepository.save(service);
        }
        return bookingService;
    }

    @Override
    public BookingServiceEntity updateBookingServiceInfoCustomer(BookingServiceEntity bookingService, InfoBookingServiceRequest bookingServiceInfo, UserEntity user) throws Exception {
        bookingService =  bookingServiceMapping.updateBookingServiceByInfoAndCustomer(bookingService, bookingServiceInfo, user);
        if (!this.isAvailableService(bookingService)){
            throw new Exception("service not available");
        }else{
            return bookingServiceRepository.save(bookingService);
        }
    }

    @Override
    public Long countAllByDateBookingAndService(LocalDateTime time, ServiceEntity service) {
        long count = 0;
        List<BookingServiceEntity> bookingServiceEntities = bookingServiceRepository.findAllByService(service);
        Date date = Date.from(time.atZone(ZoneId.systemDefault()).toInstant());
        for(BookingServiceEntity item : bookingServiceEntities){
            Date dateBooking = Date.from(item.getDateBooking().atZone(ZoneId.systemDefault()).toInstant());
            if (date.equals(dateBooking)){
                count ++;
            }
        }
        return Long.valueOf(count);
    }

    @Override
    public Boolean isAvailableService(BookingServiceEntity newBooking) throws Exception {
        if (newBooking.getDateBooking().isBefore(LocalDateTime.now(ZoneId.of("GMT+07:00")))) {
            throw new Exception("Datatime is not valid");
        }

        if (isFullSlotService(newBooking.getDateBooking(), newBooking.getService())){
            return false;
        }
        return true;
    }

    @Override
    public BookingServiceEntity findByIdAndUserBookService(String id, UserEntity user) {
        Optional<BookingServiceEntity> booking = bookingServiceRepository.findByIdAndUserBookService(id, user);
        if (booking.isEmpty()){
            return null;
        }
        return booking.get();
    }

    @Override
    public List<BookingServiceEntity> saveListBookingService(AddListBookingServiceByCustomerRequest bookingInfo, UserEntity user) throws Exception {
        try {

            List<BookingServiceEntity> bookingServiceEntities = new ArrayList<>();

            for(String id : bookingInfo.getServiceIds()){
                AddBookingServiceRequest request = new AddBookingServiceRequest(bookingInfo.getDateBooking(), id);
                BookingServiceEntity newBookingService = bookingServiceMapping.modelToEntityAddByCustomer(request, user);
                if (!isAvailableService(newBookingService)){
                    throw new Exception("service not available");
                }
                BookingServiceEntity bookingServiceEntity =  saveBookingService(newBookingService);
                bookingServiceEntities.add(bookingServiceEntity);
            }
            return bookingServiceEntities;

        }
        catch (Exception e){
            return null;
        }

    }

    @Override
    public BookingServiceEntity findById(String id) {
        Optional<BookingServiceEntity> booking = bookingServiceRepository.findById(id);
        if (booking.isEmpty()){
            return null;
        }
        return booking.get();
    }

    @Override
    public Boolean isFullSlotService(LocalDateTime date, ServiceEntity service) {
        BigInteger maxSlot = service.getSlot();
        Long slot = countAllByDateBookingAndService(date, service);
        if (slot.intValue() < maxSlot.intValue()) {
            return false;
        }
        return true;
    }
}
