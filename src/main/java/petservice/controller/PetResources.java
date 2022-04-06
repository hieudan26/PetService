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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import petservice.Handler.HttpMessageNotReadableException;
import petservice.Handler.MethodArgumentNotValidException;
import petservice.Handler.RecordNotFoundException;
import petservice.Service.PetService;
import petservice.mapping.PetMapping;
import petservice.model.Entity.PetEntity;
import petservice.model.Entity.UserEntity;
import petservice.model.payload.request.PetResources.AddPetRequest;
import petservice.model.payload.request.PetResources.DeletePetsRequest;
import petservice.model.payload.request.PetResources.InfoPetRequest;
import petservice.model.payload.response.ErrorResponseMap;
import petservice.model.payload.response.SuccessResponse;
import petservice.model.payload.response.SuccessResponseWithPagination;
import petservice.security.JWT.JwtUtils;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/auth/pet")
@RequiredArgsConstructor
public class PetResources {
    private static final Logger LOGGER = LogManager.getLogger(PetResources.class);

    private final PetService petService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtils jwtUtils;

    @GetMapping("")
    @ResponseBody
    public ResponseEntity<SuccessResponseWithPagination> getPets(
            @RequestParam(required=false) String all,       @RequestParam(required=false) String name,
            @RequestParam(required=false) String gender,  @RequestParam(required=false) String location,
            @RequestParam(required=false) String breed,     @RequestParam(required=false) String description,
            @RequestParam(required=false) String category,
            @RequestParam(required=false) Boolean vaccinated,   @RequestParam(required=false) Boolean status,
                                                            @RequestParam(defaultValue = "0") int page,
                                                            @RequestParam(defaultValue = "3") int size,
                                                            @RequestParam(defaultValue = "Id") String sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        Map<String,String> ParamMap = new HashMap<>();
        if(name != null)
            ParamMap.put("name",name);
        if(gender != null)
            ParamMap.put("gender",gender);
        if(location != null)
            ParamMap.put("location",location);
        if(breed != null)
            ParamMap.put("breed",breed);
        if(description != null)
            ParamMap.put("description",description);
        if(category != null)
            ParamMap.put("category",category);
        if(status != null)
            ParamMap.put("status",status.toString());
        if(vaccinated != null)
            ParamMap.put("vaccinated",vaccinated.toString());
        if(all != null)
            ParamMap.put("all",all);

        Page<PetEntity> petPage = petService.getAllPet(ParamMap,pageable);
        if(petPage == null) {
            throw new RecordNotFoundException("No Pet existing " );
        }
        SuccessResponseWithPagination response = new SuccessResponseWithPagination(petPage);
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("List pets");
        response.setSuccess(true);
        response.getData().put("pets", petPage.getContent());
        return new ResponseEntity<SuccessResponseWithPagination>(response, HttpStatus.OK);
    }


    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<SuccessResponse> getInfoPet(HttpServletRequest request, @PathVariable("id") String id) throws Exception {

        PetEntity pet = petService.findById(id);

        if (pet == null){
            LOGGER.info("Inside getPet");
            throw new HttpMessageNotReadableException("Not exist");
        }

        SuccessResponse response = new SuccessResponse();
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("Info pet");
        response.setSuccess(true);
        response.getData().put("petInfo",pet);

        return new ResponseEntity<SuccessResponse>(response,HttpStatus.OK);

    }
    @GetMapping("/category/{category}")
    @ResponseBody
    public ResponseEntity<SuccessResponseWithPagination> getPetsByCategory(@RequestParam(defaultValue = "0") int page,
                                                             @RequestParam(defaultValue = "10") int size,
                                                             HttpServletRequest request, @PathVariable("category") String category) throws Exception {
        Pageable pageable = PageRequest.of(page, size, Sort.by("Name"));
        Page<PetEntity> petEntityPage = petService.getAllByCategor(pageable, category);
        if (petEntityPage == null) {
            throw new RecordNotFoundException("No PetEntity existing ");
        }
        SuccessResponseWithPagination response = new SuccessResponseWithPagination(petEntityPage);
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("List pets");
        response.setSuccess(true);
        response.getData().put("pets", petEntityPage.getContent());
        return new ResponseEntity<SuccessResponseWithPagination>(response, HttpStatus.OK);

    }



    @PostMapping("")
    @ResponseBody
    public ResponseEntity<SuccessResponse> addPet(@RequestBody @Valid AddPetRequest addPetRequest, BindingResult errors) throws Exception {
        System.out.println(addPetRequest.getImagePetEntityList());
        if (errors.hasErrors()) {
            throw new MethodArgumentNotValidException(errors);
        }
        if (addPetRequest == null) {
            LOGGER.info("Inside addIssuer, adding: " + addPetRequest.toString());
            throw new HttpMessageNotReadableException("Missing field");
        } else {
            LOGGER.info("Inside addIssuer...");
        }
        try{
            System.out.println(1);
            PetEntity newPet = PetMapping.ModelToEntity(addPetRequest);
            System.out.println(2);
            petService.savePet(newPet);

            SuccessResponse response = new SuccessResponse();
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Add new pet successful");
            response.setSuccess(true);

            response.getData().put("name",addPetRequest.getName());
            return new ResponseEntity<SuccessResponse>(response,HttpStatus.OK);

        }catch(Exception ex){
            throw new Exception(ex.getMessage() + "\nCan't create new pet");
        }
    }
    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity<SuccessResponse> deletePets(@PathVariable("id") String id, HttpServletRequest request) throws Exception {

        petService.deletePet(id);
        SuccessResponse response = new SuccessResponse();
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("delete successful");
        response.setSuccess(true);
        return new ResponseEntity<SuccessResponse>(response,HttpStatus.OK);
    }

    @DeleteMapping("")
    @ResponseBody
    public ResponseEntity<SuccessResponse> deletePet(@RequestBody @Valid DeletePetsRequest deleteRequest, BindingResult errors, HttpServletRequest request) throws Exception {
        if (errors.hasErrors()) {
            throw new MethodArgumentNotValidException(errors);
        }

        petService.deletePets(deleteRequest.getIds());
        SuccessResponse response = new SuccessResponse();
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("delete successful");
        response.setSuccess(true);
        return new ResponseEntity<SuccessResponse>(response,HttpStatus.OK);
    }
    @PutMapping("/{id}")
    @ResponseBody
    public ResponseEntity<SuccessResponse>  updateInfoPet(@RequestBody @Valid InfoPetRequest petInfo, @PathVariable("id") String id, BindingResult errors, HttpServletRequest request) throws Exception {

        if (errors.hasErrors()) {
            throw new MethodArgumentNotValidException(errors);
        }

        PetEntity pet = petService.findById(id);

        petService.deleteImagePet(id);
        pet = petService.updatePetInfo(pet,petInfo);

        SuccessResponse response = new SuccessResponse();
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("Update info successful");
        response.setSuccess(true);
        response.getData().put("petInfo",pet);

        return new ResponseEntity<SuccessResponse>(response,HttpStatus.OK);

    }



    private ResponseEntity SendErrorValid(String field, String message){
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
