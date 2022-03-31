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
import petservice.Service.RoleService;
import petservice.Service.UserService;
import petservice.mapping.UserMapping;
import petservice.model.Entity.UserEntity;
import petservice.model.payload.request.PetResources.DeletePetsRequest;
import petservice.model.payload.request.UserResources.RegisterAdminRequest;
import petservice.model.payload.request.UserResources.RoleToUserRequest;
import petservice.model.payload.request.UserResources.StatusRequest;
import petservice.model.payload.response.ErrorResponseMap;
import petservice.model.payload.response.SuccessResponse;
import petservice.model.payload.response.SuccessResponseWithPagination;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/user")
@RequiredArgsConstructor
public class UserResource {
    private static final Logger LOGGER = LogManager.getLogger(UserResource.class);

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    AuthenticationManager authenticationManager;


    @GetMapping("")
    @ResponseBody
    public ResponseEntity<SuccessResponseWithPagination> getUsers(
            @RequestParam(required=false) String id,        @RequestParam(required=false) String firstname,
            @RequestParam(required=false) String lastname,  @RequestParam(required=false) String email,
            @RequestParam(required=false) String phone,     @RequestParam(required=false) String username,
            @RequestParam(required=false) Boolean status,   @RequestParam(required=false) Boolean active,
            @RequestParam(required=false) Boolean address,  @RequestParam(required=false) Boolean all,

                                                                @RequestParam(defaultValue = "0") int page,
                                                                @RequestParam(defaultValue = "3") int size,
                                                                @RequestParam(defaultValue = "CreateAt") String sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));

        Page<UserEntity> userPage = userService.getAllUser(pageable);
        if(userPage == null) {
            throw new RecordNotFoundException("No UserEntity existing " );
        }


        SuccessResponseWithPagination response = new SuccessResponseWithPagination(userPage);
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("list users");
        response.setSuccess(true);
        response.getData().put("users",userPage.getContent());
        return new ResponseEntity<SuccessResponseWithPagination>(response,HttpStatus.OK);
    }

    @GetMapping("/search")
    @ResponseBody
    public ResponseEntity<SuccessResponseWithPagination> findUsers(
            @RequestParam(required=false) String id,        @RequestParam(required=false) String firstname,
            @RequestParam(required=false) String lastname,  @RequestParam(required=false) String email,
            @RequestParam(required=false) String phone,     @RequestParam(required=false) String username,
            @RequestParam(required=false) Boolean status,   @RequestParam(required=false) Boolean active,
            @RequestParam(required=false) Boolean address,  @RequestParam(required=false) Boolean all,

                                                            @RequestParam(defaultValue = "0") int page,
                                                            @RequestParam(defaultValue = "3") int size,
                                                            @RequestParam(defaultValue = "CreateAt") String sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        Page<UserEntity> userPage = userService.getAllUser(pageable);
        if(userPage == null) {
            throw new RecordNotFoundException("No UserEntity existing " );
        }


        SuccessResponseWithPagination response = new SuccessResponseWithPagination(userPage);
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("list users");
        response.setSuccess(true);
        response.getData().put("users",userPage.getContent());
        return new ResponseEntity<SuccessResponseWithPagination>(response,HttpStatus.OK);
    }

    @GetMapping("{id}")
    @ResponseBody
    public ResponseEntity<SuccessResponse> getUser( @PathVariable("id") String id) {

        UserEntity user = userService.findById(id);
        if(user == null) {
            throw new RecordNotFoundException("No UserEntity existing " );
        }
        SuccessResponse response = new SuccessResponse();
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("list users");
        response.setSuccess(true);
        response.getData().put("user",user);
        return new ResponseEntity<SuccessResponse>(response,HttpStatus.OK);
    }

    @PostMapping("")
    @ResponseBody
    public ResponseEntity<SuccessResponse> saveUser(@RequestBody @Valid RegisterAdminRequest user, BindingResult errors) throws  Exception {
        if (errors.hasErrors()) {
            throw new MethodArgumentNotValidException(errors);
        }
        if (user == null) {
            LOGGER.info("Inside addIssuer, adding: " + user.toString());
            throw new HttpMessageNotReadableException("Missing field");
        } else {
            LOGGER.info("Inside addIssuer...");
        }

        if(userService.existsByEmail(user.getEmail())){
            return SendErrorValid("email",user.getEmail());
        }

        if(userService.existsByUsername(user.getUsername())){
            return SendErrorValid("username",user.getUsername());
        }

        try{

            UserEntity newUser = UserMapping.registerToEntity(user);
            newUser.setActive(true);
            userService.saveUser(newUser,user.getRoles());

            SuccessResponse response = new SuccessResponse();
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("add user successful");
            response.setSuccess(true);
            response.getData().put("email",user.getEmail());
            return new ResponseEntity<SuccessResponse>(response,HttpStatus.OK);

        }catch(Exception ex){
            throw new Exception("Can't create your account");
        }
    }

    @PutMapping("/status")
    @ResponseBody
    public ResponseEntity<SuccessResponse> setStatusUser(@RequestBody @Valid StatusRequest request, BindingResult errors) throws  Exception {
        if (errors.hasErrors()) {
            throw new MethodArgumentNotValidException(errors);
        }
        if (request == null) {
            throw new HttpMessageNotReadableException("Missing field");
        } else {
            LOGGER.info("Inside addIssuer, adding: " + request);
            LOGGER.info("Inside addIssuer...");
        }

        if(!userService.existsByEmail(request.getEmail())){
            return SendErrorValid("email",request.getEmail());
        }

        try{

            UserEntity user = userService.findByEmail(request.getEmail());
            userService.setStatus(user,request.getStatus());

            SuccessResponse response = new SuccessResponse();
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Change status successful");
            response.setSuccess(true);
            response.getData().put("user",user);
            return new ResponseEntity<SuccessResponse>(response,HttpStatus.OK);

        }catch(Exception ex){
            throw new Exception("Can't create your account");
        }
    }

    @PostMapping("/role")
    @ResponseBody
    public ResponseEntity<SuccessResponse> addRoleToUser(@RequestBody @Valid RoleToUserRequest roleForm, BindingResult errors) throws  Exception  {
        if (errors.hasErrors()) {
            throw new MethodArgumentNotValidException(errors);
        }

        if (roleForm == null) {
            LOGGER.info("Inside addIssuer, adding: " + roleForm.toString());
            throw new HttpMessageNotReadableException("Missing field");
        } else {
            LOGGER.info("Inside addIssuer...");
        }

        if(!userService.existsByEmail(roleForm.getEmail())){
            throw new HttpMessageNotReadableException("UserEntity is not exist");
        }

        if(!roleService.existsByRoleName(roleForm.getRoleName())){
            throw new HttpMessageNotReadableException("Role is not exist");
        }
        try{
        userService.addRoleToUser(roleForm.getEmail(),roleForm.getRoleName());

        SuccessResponse response = new SuccessResponse();
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("add user successful");
        response.setSuccess(true);
        response.getData().put("email",roleForm.getEmail());
        response.getData().put("role",roleForm.getRoleName());
        return new ResponseEntity<SuccessResponse>(response,HttpStatus.OK);

        }catch(Exception ex){
            throw new Exception("Can't add role to account");
        }
    }


    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity<SuccessResponse> deleteUsers(@PathVariable("id") String id, HttpServletRequest request) throws Exception {

        userService.deleteUserById(id);
        SuccessResponse response = new SuccessResponse();
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("delete successful");
        response.setSuccess(true);
        return new ResponseEntity<SuccessResponse>(response,HttpStatus.OK);
    }

    @DeleteMapping("")
    @ResponseBody
    public ResponseEntity<SuccessResponse> deleteUser(@RequestBody @Valid DeletePetsRequest deleteRequest, BindingResult errors) throws Exception {
        if (errors.hasErrors()) {
            throw new MethodArgumentNotValidException(errors);
        }

        userService.deleteUsersById(deleteRequest.getIds());
        SuccessResponse response = new SuccessResponse();
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("delete successful");
        response.setSuccess(true);
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
}