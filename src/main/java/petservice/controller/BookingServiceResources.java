package petservice.controller;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import petservice.Handler.HttpMessageNotReadableException;
import petservice.Handler.MethodArgumentNotValidException;
import petservice.Handler.RecordNotFoundException;
import petservice.Service.BookingServiceService;
import petservice.Service.UserService;
import petservice.common.StatusBookingService;
import petservice.mapping.BookingServiceMapping;
import petservice.model.Entity.BookingServiceEntity;
import petservice.model.Entity.UserEntity;
import petservice.model.payload.request.BookingServiceResources.AddBookingServiceRequest;
import petservice.model.payload.request.BookingServiceResources.AddListBookingServiceByCustomerRequest;
import petservice.model.payload.request.BookingServiceResources.AdminAddBookingServiceRequest;
import petservice.model.payload.request.BookingServiceResources.InfoBookingServiceRequest;
import petservice.model.payload.response.ErrorResponseMap;
import petservice.model.payload.response.SuccessResponse;
import petservice.model.payload.response.SuccessResponseWithPagination;
import petservice.security.JWT.JwtUtils;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.google.common.net.HttpHeaders.AUTHORIZATION;

@RestController
@RequestMapping("api/booking")
@RequiredArgsConstructor
public class BookingServiceResources {
    private static final Logger LOGGER = LogManager.getLogger(ServiceResource.class);

    private final BookingServiceService bookingService;

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    BookingServiceMapping bookingServiceMapping;

    @Autowired
    UserService userService;

//   Admin + USER
    @PostMapping("")
    @ResponseBody
    public ResponseEntity<SuccessResponse> addNewBookingService(@RequestBody @Valid AddBookingServiceRequest bookingInfo, BindingResult errors, HttpServletRequest request) throws Exception {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String accessToken = authorizationHeader.substring("Bearer ".length());

            if (jwtUtils.validateExpiredToken(accessToken) == true) {
                throw new BadCredentialsException("access token is  expired");
            }

            if (errors.hasErrors()) {
                throw new MethodArgumentNotValidException(errors);
            }

            UserEntity user = userService.findByUsername(jwtUtils.getUserNameFromJwtToken(accessToken));

            BookingServiceEntity newBooking = bookingServiceMapping.modelToEntityAddByCustomer(bookingInfo, user);


            if (newBooking.getService() == null){
                LOGGER.info("Service not exist: " + bookingInfo.getServiceId());
                throw new Exception("service is not exist");
            }

            if (!bookingService.isAvailableService(newBooking)){
                LOGGER.info("Slot of service if full: " + bookingInfo.getServiceId());
                throw new Exception("service not available");
            }

            bookingService.saveBookingService(newBooking);

            SuccessResponse response = new SuccessResponse();
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Adding new booking service is successful");
            response.setSuccess(true);
            response.getData().put("booking", newBooking);

            return new ResponseEntity<SuccessResponse>(response, HttpStatus.OK);
        } else {
            throw new BadCredentialsException("access token is missing");
        }
    }

    @PostMapping("admin")
    @ResponseBody
    public ResponseEntity<SuccessResponse> adminadddNewBookingService(@RequestBody @Valid AdminAddBookingServiceRequest bookingInfo, BindingResult errors, HttpServletRequest request) throws Exception {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String accessToken = authorizationHeader.substring("Bearer ".length());

            if (jwtUtils.validateExpiredToken(accessToken) == true) {
                throw new BadCredentialsException("access token is  expired");
            }

            if (errors.hasErrors()) {
                throw new MethodArgumentNotValidException(errors);
            }


            BookingServiceEntity newBooking = bookingServiceMapping.modelToEntityAdminAddByCustomer(bookingInfo);


            if (newBooking.getService() == null){
                LOGGER.info("Service not exist: " + bookingInfo.getServiceId());
                throw new Exception("service is not exist");
            }

            if (!bookingService.isAvailableService(newBooking)){
                LOGGER.info("Slot of service if full: " + bookingInfo.getServiceId());
                throw new Exception("service not available");
            }

            bookingService.saveBookingService(newBooking);

            SuccessResponse response = new SuccessResponse();
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Adding new booking service is successful");
            response.setSuccess(true);
            response.getData().put("booking", newBooking);

            return new ResponseEntity<SuccessResponse>(response, HttpStatus.OK);
        } else {
            throw new BadCredentialsException("access token is missing");
        }
    }



    @PostMapping("/list")
    @ResponseBody
    public ResponseEntity<SuccessResponse> addListNewBookingService(@RequestBody @Valid AddListBookingServiceByCustomerRequest bookingInfo, BindingResult errors, HttpServletRequest request) throws Exception {
        try {
            String authorizationHeader = request.getHeader(AUTHORIZATION);
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                String accessToken = authorizationHeader.substring("Bearer ".length());

                if (jwtUtils.validateExpiredToken(accessToken) == true) {
                    throw new BadCredentialsException("access token is  expired");
                }

                if (errors.hasErrors()) {
                    throw new MethodArgumentNotValidException(errors);
                }

                UserEntity user = userService.findByUsername(jwtUtils.getUserNameFromJwtToken(accessToken));

                List<BookingServiceEntity> bookingServiceEntities =  bookingService.saveListBookingService(bookingInfo, user);

                SuccessResponse response = new SuccessResponse();
                response.setStatus(HttpStatus.OK.value());
                response.setMessage("Adding new booking service is successful");
                response.setSuccess(true);
                response.getData().put("booking", bookingServiceEntities );

                return new ResponseEntity<SuccessResponse>(response, HttpStatus.OK);
            } else {
                throw new BadCredentialsException("access token is missing");
            }
        }catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.MULTI_STATUS);
        }
    }



//    Admin + USER
    @PutMapping("/{id}")
    @ResponseBody
    public ResponseEntity<SuccessResponse> updateBookingService(@RequestBody @Valid InfoBookingServiceRequest bookingInfo, @PathVariable("id") String id, BindingResult errors, HttpServletRequest request) throws Exception {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String accessToken = authorizationHeader.substring("Bearer ".length());

            if (jwtUtils.validateExpiredToken(accessToken) == true) {
                throw new BadCredentialsException("access token is  expired");
            }

            if (errors.hasErrors()) {
                throw new MethodArgumentNotValidException(errors);
            }

            UserEntity user = userService.findByUsername(jwtUtils.getUserNameFromJwtToken(accessToken));
            BookingServiceEntity updateBooking = bookingService.findByIdAndUserBookService(id, user);

            if (updateBooking == null){
                LOGGER.info("Not found booking: " + id);
                throw new Exception("Not found booking");
            }

            updateBooking = bookingService.updateBookingServiceInfoCustomer(updateBooking, bookingInfo, user);

            SuccessResponse response = new SuccessResponse();
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Update booking service is successful");
            response.setSuccess(true);
            response.getData().put("booking", updateBooking);
          
            return new ResponseEntity<SuccessResponse>(response, HttpStatus.OK);
        } else {
            throw new BadCredentialsException("access token is missing");
        }
    }


//    ADMIN
    @GetMapping("")
    @ResponseBody
    public ResponseEntity<SuccessResponseWithPagination> getAllBookingService(@RequestParam(defaultValue = "0") int page,
                                                                              @RequestParam(defaultValue = "3") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("dateBooking"));

        Page<BookingServiceEntity> bookingServices = bookingService.getAllBookingService(pageable);
        if(bookingServices == null) {
            throw new RecordNotFoundException("No booking service existing " );
        }
        SuccessResponseWithPagination response = new SuccessResponseWithPagination(bookingServices);
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("list booking services");
        response.setSuccess(true);
        response.getData().put("bookingServices",bookingServices.getContent());
        return new ResponseEntity<SuccessResponseWithPagination>(response,HttpStatus.OK);
    }

//    ADMIN + USER
    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<SuccessResponse> getInfoBookingService(HttpServletRequest request, @PathVariable("id") String id) throws Exception {

        BookingServiceEntity booking = bookingService.findById(id);

        if (booking == null){
            LOGGER.info("Inside getService");
            throw new HttpMessageNotReadableException("Not exist");
        }

        SuccessResponse response = new SuccessResponse();
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("Info booking");
        response.setSuccess(true);
        response.getData().put("BookingInfo",booking);

        return new ResponseEntity<SuccessResponse>(response,HttpStatus.OK);

    }


//    ADMIN
    @GetMapping("/find/service")
    @ResponseBody
    public ResponseEntity<SuccessResponseWithPagination> getAllBookingServiceByServiceName(@RequestParam(defaultValue = "0") int page,
                                                                @RequestParam(defaultValue = "3") int size, @RequestParam String name) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("dateBooking"));

        Page<BookingServiceEntity> bookingServices = bookingService.getALLByService_NameContaining(name, pageable);

        if(bookingServices == null) {
            throw new RecordNotFoundException("No booking service existing " );
        }
        SuccessResponseWithPagination response = new SuccessResponseWithPagination(bookingServices);
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("list booking services");
        response.setSuccess(true);
        response.getData().put("bookingServices",bookingServices.getContent());
        return new ResponseEntity<SuccessResponseWithPagination>(response,HttpStatus.OK);
    }

//    ADMIN
    @GetMapping("/find/user")
    @ResponseBody
    public ResponseEntity<SuccessResponseWithPagination> getAllBookingServiceByUsername(@RequestParam(defaultValue = "0") int page,
                                                                             @RequestParam(defaultValue = "3") int size, @RequestParam String name) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("dateBooking"));

        Page<BookingServiceEntity> bookingServices = bookingService.getAllByUserBookServiceName(name, pageable);

        if(bookingServices == null) {
            throw new RecordNotFoundException("No booking service existing " );
        }
        SuccessResponseWithPagination response = new SuccessResponseWithPagination(bookingServices);
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("list booking services");
        response.setSuccess(true);
        response.getData().put("bookingServices",bookingServices.getContent());
        return new ResponseEntity<SuccessResponseWithPagination>(response,HttpStatus.OK);
    }

//  ADMIN
    @GetMapping("/find/status")
    @ResponseBody
    public ResponseEntity<SuccessResponseWithPagination> getAllBookingServiceByStatus(@RequestParam(defaultValue = "0") int page,
                                                                          @RequestParam(defaultValue = "3") int size, @RequestParam String status) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("dateBooking"));

        try{
            status = StatusBookingService.handleUpperCaseString(status);
        }
        catch (Exception e) {
            status = "";
        }
        finally {
            Page<BookingServiceEntity> bookingServices = bookingService.getAllByStatus(status, pageable);

            if(bookingServices == null) {
                throw new RecordNotFoundException("No booking service existing " );
            }
            SuccessResponseWithPagination response = new SuccessResponseWithPagination(bookingServices);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("list booking services");
            response.setSuccess(true);
            response.getData().put("bookingServices",bookingServices.getContent());
            return new ResponseEntity<SuccessResponseWithPagination>(response,HttpStatus.OK);
        }
    }


//    ADMIN
    @GetMapping("/find/date")
    @ResponseBody
    public ResponseEntity<SuccessResponseWithPagination> getAllBookingServiceBydate(@RequestParam(defaultValue = "0") int page,
                                                                        @RequestParam(defaultValue = "3") int size, @RequestParam String date) throws Exception {
        Pageable pageable = PageRequest.of(page, size, Sort.by("dateBooking"));

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime dateTime = LocalDateTime.parse(date, formatter);

            Page<BookingServiceEntity> bookingServices = bookingService.getAllByDateBooking(dateTime, pageable);

            if(bookingServices == null) {
                throw new RecordNotFoundException("No booking service existing " );
            }
            SuccessResponseWithPagination response = new SuccessResponseWithPagination(bookingServices);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("list booking services");
            response.setSuccess(true);
            response.getData().put("bookingServices",bookingServices.getContent());
            return new ResponseEntity<SuccessResponseWithPagination>(response,HttpStatus.OK);
        }
        catch (Exception e) {
            throw new Exception("Datetime is wrong format");
        }


    }

//    ADMin
    @GetMapping("/find/paystatus")
    @ResponseBody
    public ResponseEntity<SuccessResponseWithPagination> getAllBookingServiceByPayment(@RequestParam(defaultValue = "0") int page,
                                                                      @RequestParam(defaultValue = "3") int size, @RequestParam String pay) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("dateBooking"));

        Boolean payStatus = Boolean.valueOf(pay);

        Page<BookingServiceEntity> bookingServices = bookingService.getAllByPayment(payStatus, pageable);

        if(bookingServices == null) {
            throw new RecordNotFoundException("No booking service existing " );
        }
        SuccessResponseWithPagination response = new SuccessResponseWithPagination(bookingServices);
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("list booking services");
        response.setSuccess(true);
        response.getData().put("bookingServices",bookingServices.getContent());
        return new ResponseEntity<SuccessResponseWithPagination>(response,HttpStatus.OK);

    }


    private ResponseEntity SendErrorValid(String field, String message, String field_already_taken){
        ErrorResponseMap errorResponseMap = new ErrorResponseMap();
        Map<String,String> temp =new HashMap<>();
        errorResponseMap.setMessage(field+" already taken");
        temp.put(field,message+" has already used");
        errorResponseMap.setStatus(HttpStatus.BAD_REQUEST.value());
        errorResponseMap.setDetails(temp);
        return ResponseEntity
                .badRequest()
                .body(errorResponseMap);
    }
}
