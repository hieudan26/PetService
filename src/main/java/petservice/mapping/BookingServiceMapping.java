package petservice.mapping;

import org.springframework.beans.factory.annotation.Autowired;
import petservice.Service.ServiceService;
import petservice.Service.UserService;
import petservice.common.StatusBookingService;
import petservice.model.Entity.BookingServiceEntity;
import petservice.model.Entity.ServiceEntity;
import petservice.model.Entity.UserEntity;
import petservice.model.payload.request.BookingServiceResources.AddBookingServiceRequest;
import petservice.model.payload.request.BookingServiceResources.InfoBookingServiceRequest;



public class BookingServiceMapping {

    @Autowired
    static UserService userService;

    @Autowired
    static ServiceService serviceService;

    public static BookingServiceEntity UpdateBookingServiceByInfo (BookingServiceEntity bookingService, InfoBookingServiceRequest infoBookingServiceRequest) throws Exception{

        bookingService.setStatus(StatusBookingService.handleUpperCaseString(infoBookingServiceRequest.getStatus()));
        bookingService.setPayment(infoBookingServiceRequest.getPayment().booleanValue());
        bookingService.setDateBooking(infoBookingServiceRequest.getDateBooking());
        UserEntity user = userService.findByUsername(infoBookingServiceRequest.getUserBookService());
        if (user == null){
            throw new Exception("user booking is not exist");
        }
        else{
            bookingService.setUserBookService(user);
        }
        ServiceEntity service = serviceService.findByName(infoBookingServiceRequest.getServiceName());

        if (service == null){
            throw new Exception("service is not exist");
        }
        else{
            bookingService.setService(service);
        }

        return bookingService;
    }

    public static  BookingServiceEntity ModelToEntity(AddBookingServiceRequest request){
        BookingServiceEntity newBookingService = new BookingServiceEntity();
        newBookingService.setDateBooking(request.getDateBooking());
        newBookingService.setPayment(false);
        newBookingService.setStatus(StatusBookingService.StatusBooking.WAITING.toString());
        UserEntity user = userService.findByUsername(request.getUserBookService());
        newBookingService.setUserBookService(user);
        ServiceEntity service = serviceService.findByName(request.getServiceName());
        newBookingService.setService(service);
        return newBookingService;
    }


}
