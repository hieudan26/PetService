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
import petservice.Service.BillService;
import petservice.Service.PetService;
import petservice.Service.UserService;
import petservice.mapping.BillMapping;
import petservice.model.Entity.BillEntity;
import petservice.model.Entity.BookingServiceEntity;
import petservice.model.Entity.PetEntity;
import petservice.model.Entity.UserEntity;
import petservice.model.payload.request.BillResources.AddBillRequest;
import petservice.model.payload.request.BillResources.DeleteBillByIdPetRequest;
import petservice.model.payload.request.BillResources.DeleteBillsRequest;
import petservice.model.payload.response.SuccessResponse;
import petservice.security.JWT.JwtUtils;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import java.util.List;


@RestController
@RequestMapping("api/auth/bill")
@RequiredArgsConstructor
public class BillResource {
    private static final Logger LOGGER = LogManager.getLogger(BillResource.class);
    private final BillService billService;
    private final UserService userService;
    private final PetService petService;

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    BillMapping billMapping;



    @GetMapping("")
    @ResponseBody
    public ResponseEntity<SuccessResponse> getBills(@RequestParam(defaultValue = "0") int page,
                                                   @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("PaymentDate"));

        List<BillEntity> billEntityList = billService.getAllBills(pageable);
        if (billEntityList == null) {
            throw new RecordNotFoundException("No Bill Entity existing ");
        }
        SuccessResponse response = new SuccessResponse();
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("List bills");
        response.setSuccess(true);
        response.getData().put("bills", billEntityList);
        return new ResponseEntity<SuccessResponse>(response, HttpStatus.OK);
    }



    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<SuccessResponse> getInfoBill(HttpServletRequest request, @PathVariable("id") String id) throws Exception {

        BillEntity bill = billService.getById(id);

        if (bill == null){
            LOGGER.info("Inside get bill");
            throw new HttpMessageNotReadableException("Not exist");
        }

        SuccessResponse response = new SuccessResponse();
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("Info bill");
        response.setSuccess(true);
        response.getData().put("billInfo",bill);

        return new ResponseEntity<SuccessResponse>(response,HttpStatus.OK);

    }

    @GetMapping("/find/user")
    @ResponseBody
    public ResponseEntity<SuccessResponse> getAllBillsByUsername(@RequestParam(defaultValue = "0") int page,
                                                                          @RequestParam(defaultValue = "3") int size, @RequestParam String name) throws Exception {
        Pageable pageable = PageRequest.of(page, size, Sort.by("PaymentDate"));

        UserEntity user = userService.findByUsername(name);
        if (user == null) {
            throw new Exception("User not exist");
        }
        List<BillEntity> billEntityList = billService.getAllByUser(user, pageable);

        if(billEntityList == null) {
            throw new RecordNotFoundException("No bill existing " );
        }
        SuccessResponse response = new SuccessResponse();
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("list bills");
        response.setSuccess(true);
        response.getData().put("bills",billEntityList);
        return new ResponseEntity<SuccessResponse>(response,HttpStatus.OK);
    }

    @GetMapping("/find/pet")
    @ResponseBody
    public ResponseEntity<SuccessResponse> getAllBillsByIdPet(@RequestParam String idPet) throws Exception {

        PetEntity pet = petService.findById(idPet);
        if (pet == null) {
            throw new Exception("Pet not exist");
        }
        BillEntity billEntity = billService.getByPet(pet);

        if(billEntity == null) {
            throw new RecordNotFoundException("No bill existing " );
        }
        SuccessResponse response = new SuccessResponse();
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("bill");
        response.setSuccess(true);
        response.getData().put("bill",billEntity);
        return new ResponseEntity<SuccessResponse>(response,HttpStatus.OK);
    }




    @PostMapping("")
    @ResponseBody
    public ResponseEntity<SuccessResponse> addNewBill(@RequestBody @Valid AddBillRequest addBillRequest,
                                                      BindingResult errors) throws Exception {
        if (errors.hasErrors()) {
            throw new MethodArgumentNotValidException(errors);
        }
        BillEntity bill = billMapping.ModelToEntity(addBillRequest);

        if (bill == null) {
            LOGGER.info("Can't convert Model to Entity");
            throw new Exception("Can't convert Model to Entity");
        }
        if (!billService.existsByPetSale(bill.getPetSale())) {
            billService.saveBill(bill);
        } else {
            LOGGER.info("Bill is existing");
            throw new Exception("Bill is existing");
        }

        SuccessResponse response = new SuccessResponse();
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("Adding new bill is successful");
        response.setSuccess(true);
        response.getData().put("bill", bill);

        return new ResponseEntity<SuccessResponse>(response, HttpStatus.OK);
    }

    @DeleteMapping("")
    @ResponseBody
    public ResponseEntity<SuccessResponse> deleteBills(@RequestBody @Valid DeleteBillsRequest deleteBillsRequest, BindingResult errors, HttpServletRequest request) throws Exception {
        if (errors.hasErrors()) {
            throw new MethodArgumentNotValidException(errors);
        }

        billService.deleteBills(deleteBillsRequest.getIds());
        SuccessResponse response = new SuccessResponse();
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("delete successful");
        response.setSuccess(true);
        return new ResponseEntity<SuccessResponse>(response,HttpStatus.OK);
    }

    @DeleteMapping("/pet")
    @ResponseBody
    public ResponseEntity<SuccessResponse> deleteBillByIdPet(@RequestBody @Valid DeleteBillByIdPetRequest deleteBillByIdPetRequest, BindingResult errors, HttpServletRequest request) throws Exception {
        if (errors.hasErrors()) {
            throw new MethodArgumentNotValidException(errors);
        }
        billService.deleteByIdPet(deleteBillByIdPetRequest.getIdPet());
        SuccessResponse response = new SuccessResponse();
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("delete successful");
        response.setSuccess(true);
        return new ResponseEntity<SuccessResponse>(response,HttpStatus.OK);
    }


}
