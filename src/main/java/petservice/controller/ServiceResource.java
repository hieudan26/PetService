package petservice.controller;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import petservice.Handler.HttpMessageNotReadableException;
import petservice.Handler.MethodArgumentNotValidException;
import petservice.Handler.RecordNotFoundException;
import petservice.Service.ServiceService;
import petservice.mapping.ServiceMapping;
import petservice.model.Entity.ServiceEntity;
import petservice.model.payload.request.ServiceResources.AddServiceRequest;
import petservice.model.payload.request.ServiceResources.DeleteServiceRequest;
import petservice.model.payload.request.ServiceResources.InfoServiceRequest;
import petservice.model.payload.response.ErrorResponseMap;
import petservice.model.payload.response.SuccessResponse;
import petservice.security.JWT.JwtUtils;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/auth/service")
@RequiredArgsConstructor
public class ServiceResource {
    private static final Logger LOGGER = LogManager.getLogger(AdminResource.class);

    private final ServiceService serviceService;

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtUtils jwtUtils;


    @GetMapping("")
    @ResponseBody
    public ResponseEntity<SuccessResponse> getServices(@RequestParam(defaultValue = "0") int page,
                                                       @RequestParam(defaultValue = "3") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("Name"));

        List<ServiceEntity> serviceList = serviceService.getAllService(pageable);
        if(serviceList == null) {
            throw new RecordNotFoundException("No UserEntity existing " );
        }
        SuccessResponse response = new SuccessResponse();
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("list services");
        response.setSuccess(true);
        response.getData().put("services",serviceList   );
        return new ResponseEntity<SuccessResponse>(response,HttpStatus.OK);
    }


    @GetMapping("/info/{id}")
    @ResponseBody
    public ResponseEntity<SuccessResponse> getInfoService(HttpServletRequest request, @PathVariable("id") String id) throws Exception {

        ServiceEntity service = serviceService.findById(id);

        if (service == null){
            LOGGER.info("Inside getService");
            throw new HttpMessageNotReadableException("Not exist");
        }

        SuccessResponse response = new SuccessResponse();
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("Info service");
        response.setSuccess(true);
        response.getData().put("serviceInfo",service);

        return new ResponseEntity<SuccessResponse>(response,HttpStatus.OK);

    }

    @PostMapping("/add")
    @ResponseBody
    public ResponseEntity<SuccessResponse> addService(@RequestBody @Valid AddServiceRequest service, BindingResult errors) throws Exception {

        if (errors.hasErrors()) {
            throw new MethodArgumentNotValidException(errors);
        }
        if (service == null) {
            LOGGER.info("Inside addIssuer, adding: " + service.toString());
            throw new HttpMessageNotReadableException("Missing field");
        } else {
            LOGGER.info("Inside addIssuer...");
        }

        if(serviceService.existsByName(service.getName())){
            return SendErrorValid("name",service.getName()+"\" has already used\"","Field already taken");
        }

        try{

            ServiceEntity newService = ServiceMapping.ModelToEntity(service);
            serviceService.saveService(newService);

            SuccessResponse response = new SuccessResponse();
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Add new service successful");
            response.setSuccess(true);

            response.getData().put("name",service.getName());
            return new ResponseEntity<SuccessResponse>(response,HttpStatus.OK);

        }catch(Exception ex){
            throw new Exception("Can't create new service");
        }
    }



    @DeleteMapping("")
    @ResponseBody
    public ResponseEntity<SuccessResponse> deleteService(@RequestBody @Valid DeleteServiceRequest deleteRequest, BindingResult errors, HttpServletRequest request) throws Exception {
        if (errors.hasErrors()) {
            throw new MethodArgumentNotValidException(errors);
        }

        serviceService.deleteSevice(deleteRequest.getIds());
        SuccessResponse response = new SuccessResponse();
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("delete successful");
        response.setSuccess(true);
        return new ResponseEntity<SuccessResponse>(response,HttpStatus.OK);

    }


    @PutMapping("/info/{id}")
    @ResponseBody
    public ResponseEntity<SuccessResponse>  updateInfoService(@RequestBody @Valid InfoServiceRequest serviceInfo, @PathVariable("id") String id, BindingResult errors, HttpServletRequest request) throws Exception {

        if (errors.hasErrors()) {
            throw new MethodArgumentNotValidException(errors);
        }

        ServiceEntity service = serviceService.findById(id);

        if (!service.getName().equals(serviceInfo.getName()))
        {
            if (serviceService.existsByName(serviceInfo.getName())) {
                LOGGER.info("Inside addService, adding: " + serviceInfo.getName());
                throw new HttpMessageNotReadableException("Name of service has exist");
            }
        }
        service = serviceService.updateServiceInfo(service,serviceInfo);

        SuccessResponse response = new SuccessResponse();
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("Update info successful");
        response.setSuccess(true);
        response.getData().put("serviceInfo",service);

        return new ResponseEntity<SuccessResponse>(response,HttpStatus.OK);

    }
    private ResponseEntity SendErrorValid(String field, String message){
        ErrorResponseMap errorResponseMap = new ErrorResponseMap();
        Map<String,String> temp =new HashMap<>();
        errorResponseMap.setMessage("Field already taken");
        temp.put(field,message+" has already used");
        errorResponseMap.setStatus(HttpStatus.BAD_REQUEST.value());
        errorResponseMap.setDetails(temp);
        return ResponseEntity
                .badRequest()
                .body(errorResponseMap);
    }

    private ResponseEntity SendErrorValid(String field, String message, String field_already_taken){
        ErrorResponseMap errorResponseMap = new ErrorResponseMap();
        Map<String,String> temp =new HashMap<>();
        errorResponseMap.setMessage("Field already taken");
        temp.put(field,message+" has already used");
        errorResponseMap.setStatus(HttpStatus.BAD_REQUEST.value());
        errorResponseMap.setDetails(temp);
        return ResponseEntity
                .badRequest()
                .body(errorResponseMap);
    }
}
