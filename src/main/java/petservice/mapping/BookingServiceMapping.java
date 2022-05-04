package petservice.mapping;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import petservice.Service.ServiceService;
import petservice.Service.UserService;
import petservice.common.StatusBookingService;
import petservice.model.Entity.BookingServiceEntity;
import petservice.model.Entity.ServiceEntity;
import petservice.model.Entity.UserEntity;
import petservice.model.payload.request.BookingServiceResources.AddBookingServiceRequest;
import petservice.model.payload.request.BookingServiceResources.AdminAddBookingServiceRequest;
import petservice.model.payload.request.BookingServiceResources.AdminInfoBookingServiceRequest;
import petservice.model.payload.request.BookingServiceResources.InfoBookingServiceRequest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class BookingServiceMapping {

    @Autowired
    UserService userService;

    @Autowired
    ServiceService serviceService;

    public  BookingServiceEntity updateBookingServiceByInfoAndCustomer (BookingServiceEntity bookingService, InfoBookingServiceRequest infoBookingServiceRequest, UserEntity user) throws Exception{

        if (StatusBookingService.handleUpperCaseString(infoBookingServiceRequest.getStatus()).equals("")){
            throw  new Exception("Status is not valid");
        }else{
            bookingService.setStatus(StatusBookingService.handleUpperCaseString(infoBookingServiceRequest.getStatus()));
        }
        bookingService.setPayment(infoBookingServiceRequest.getPayment().booleanValue());


        bookingService.setUserBookService(user);

        ServiceEntity service = serviceService.findById(infoBookingServiceRequest.getServiceId());

        if (service == null){
            throw new Exception("service is not exist");
        }
        else{
            bookingService.setService(service);
        }

        try{
            bookingService.setDateBooking(infoBookingServiceRequest.getDateBooking());
        }catch (Exception e) {
            throw new Exception("Datetime is wrong format");
        }

        return bookingService;
    }

    public  BookingServiceEntity adminUpdateBookingServiceByInfoAndCustomer (BookingServiceEntity bookingService, AdminInfoBookingServiceRequest infoBookingServiceRequest) throws Exception{

        if (StatusBookingService.handleUpperCaseString(infoBookingServiceRequest.getStatus()).equals("")){
            throw  new Exception("Status is not valid");
        }else{
            bookingService.setStatus(StatusBookingService.handleUpperCaseString(infoBookingServiceRequest.getStatus()));
        }
        bookingService.setPayment(infoBookingServiceRequest.getPayment().booleanValue());


        UserEntity user = userService.findByUsername(infoBookingServiceRequest.getUsername());
        if (user == null){
            throw new Exception("user is not exist");
        }
        else{
            bookingService.setUserBookService(user);
        }

        ServiceEntity service = serviceService.findById(infoBookingServiceRequest.getServiceId());

        if (service == null){
            throw new Exception("service is not exist");
        }
        else{
            bookingService.setService(service);
        }

        try{
            bookingService.setDateBooking(infoBookingServiceRequest.getDateBooking());
        }catch (Exception e) {
            throw new Exception("Datetime is wrong format");
        }

        return bookingService;
    }

    public   BookingServiceEntity modelToEntityAddByCustomer(AddBookingServiceRequest request, UserEntity user) throws Exception {
        BookingServiceEntity newBookingService = new BookingServiceEntity();

        newBookingService.setPayment(false);
        newBookingService.setStatus(StatusBookingService.StatusBooking.WAITING.toString());
        newBookingService.setUserBookService(user);

        ServiceEntity service = serviceService.findById(request.getServiceId());

        if (service == null){
            throw new Exception("service is not exist");
        }
        else{
            newBookingService.setService(service);
        }

        newBookingService.setDateBooking(request.getDateBooking());
        return newBookingService;
    }

    public   BookingServiceEntity modelToEntityAdminAddByCustomer(AdminAddBookingServiceRequest request) throws Exception {
        BookingServiceEntity newBookingService = new BookingServiceEntity();

        newBookingService.setPayment(false);
        newBookingService.setStatus(StatusBookingService.StatusBooking.WAITING.toString());

        UserEntity user = userService.findByUsername(request.getUsename());
        if (user == null){
            throw new Exception("user is not exist");
        }
        else{
            newBookingService.setUserBookService(user);
        }

        ServiceEntity service = serviceService.findById(request.getServiceId());

        if (service == null){
            throw new Exception("service is not exist");
        }
        else{
            newBookingService.setService(service);
        }


        newBookingService.setDateBooking(request.getDateBooking());
        return newBookingService;
    }


}
