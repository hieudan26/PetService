package petservice.controller;


import lombok.RequiredArgsConstructor;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import petservice.Handler.HttpMessageNotReadableException;
import petservice.Handler.MethodArgumentNotValidException;
import petservice.Handler.RecordNotFoundException;
import petservice.Service.EmailService;
import petservice.Service.UserService;
import petservice.mapping.UserMapping;
import petservice.model.Entity.UserEntity;
import petservice.model.payload.request.Authentication.LoginRequest;
import petservice.model.payload.request.Authentication.ReActiveRequest;
import petservice.model.payload.request.Authentication.RefreshTokenRequest;
import petservice.model.payload.request.Authentication.RegisterRequest;
import petservice.model.payload.response.ErrorResponseMap;
import petservice.model.payload.response.SuccessResponse;
import petservice.security.DTO.AppUserDetail;
import petservice.security.JWT.JwtUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import static com.google.common.net.HttpHeaders.AUTHORIZATION;

@ComponentScan
@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class AuthentiactionController {
    private static final Logger LOGGER = LogManager.getLogger(AuthentiactionController.class);

    private final UserService userService;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/register")
    @ResponseBody
    public ResponseEntity<SuccessResponse>  addUser(@RequestBody @Valid RegisterRequest user, BindingResult errors) throws Exception {

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
            return SendErrorValid("email",user.getEmail()+"\" has already used\"","email "+user.getEmail()+"\" has already used\"");
        }

        if(userService.existsByUsername(user.getUsername())){
            return SendErrorValid("username",user.getUsername()+"\" has already used\"","username "+user.getUsername()+"\" has already used\"");
        }

        try{

            UserEntity newUser = UserMapping.registerToEntity(user);
            String roleName = "USER";
            userService.saveUser(newUser,roleName);

            emailService.sendActiveMessage(newUser);

            SuccessResponse response = new SuccessResponse();
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Register successful");
            response.setSuccess(true);

            response.getData().put("email",user.getEmail());
            return new ResponseEntity<SuccessResponse>(response,HttpStatus.OK);

        }catch(Exception ex){
            throw new Exception("Can't create your account");
        }
    }

    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<SuccessResponse>  Sigin(@RequestBody @Valid LoginRequest user, BindingResult errors, HttpServletResponse resp) throws Exception {

        if (errors.hasErrors()) {
            throw new MethodArgumentNotValidException(errors);
        }

        if(!userService.existsByUsername(user.getUsername())){
            return SendErrorValid("username",user.getUsername()+" not found","No account found");
        }

        UserEntity loginUser = userService.findByUsername(user.getUsername());

        if(!(passwordEncoder.matches(user.getPassword(),loginUser.getPassword()))){
            return SendErrorValid("password","password is not matched","password is not matched");
        }

        if(!loginUser.isActive()){
            return SendErrorValid("active","Your account haven't activated","Unactivated account");
        }

        if(!loginUser.isStatus()){
            return SendErrorValid("status","Your account is banned","Banned account");
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        AppUserDetail userDetails = (AppUserDetail) authentication.getPrincipal();


        String accessToken = jwtUtils.generateJwtToken(userDetails);
        String refreshToken = jwtUtils.generateRefreshJwtToken(userDetails);


        SuccessResponse response = new SuccessResponse();
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("Login successful");
        response.setSuccess(true);

        Cookie cookieAccessToken = new Cookie("accessToken", accessToken);
        Cookie cookieRefreshToken = new Cookie("refreshToken", refreshToken);

        resp.setHeader("Set-Cookie", "test=value; Path=/");
        resp.addCookie(cookieAccessToken);
        resp.addCookie(cookieRefreshToken);

        response.getData().put("accessToken",accessToken);
        response.getData().put("refreshToken",refreshToken);

//        response.getData().put("name",loginUser.getTenhienthi());
//        response.getData().put("image",loginUser.getImage());
//        response.getData().put("roles",userDetails.getRoles());

        return new ResponseEntity<SuccessResponse>(response,HttpStatus.OK);
    }
    @PostMapping("/refreshtoken")
    public ResponseEntity<SuccessResponse> refreshToken(@RequestBody RefreshTokenRequest refreshToken, HttpServletRequest request, HttpServletResponse resp) {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            String accessToken = authorizationHeader.substring("Bearer ".length());

            if(jwtUtils.validateExpiredToken(accessToken) == false){
                throw new BadCredentialsException("access token is not expired");
            }

            if(jwtUtils.validateExpiredToken(refreshToken.getRefreshToken()) == true){
                throw new BadCredentialsException("refresh token is expired");
            }

            if(refreshToken == null){
                throw new BadCredentialsException("refresh token is missing");
            }

            if(!jwtUtils.getUserNameFromJwtToken(refreshToken.getRefreshToken()).equals(jwtUtils.getUserNameFromJwtToken(refreshToken.getRefreshToken()))){
                throw new BadCredentialsException("two token are not a pair");
            }


            AppUserDetail userDetails =  AppUserDetail.build(userService.findByUsername(jwtUtils.getUserNameFromJwtToken(refreshToken.getRefreshToken())));

            accessToken = jwtUtils.generateJwtToken(userDetails);

            SuccessResponse response = new SuccessResponse();
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Login successful");
            response.setSuccess(true);

            Cookie cookieAccessToken = new Cookie("accessToken", accessToken);

            resp.setHeader("Set-Cookie", "test=value; Path=/");
            resp.addCookie(cookieAccessToken);

            response.getData().put("accessToken",accessToken);
            response.getData().put("refreshToken",refreshToken);

            return new ResponseEntity<SuccessResponse>(response,HttpStatus.OK);
        }
        else
        {
            throw new BadCredentialsException("access token is missing");
        }
    }

    @PostMapping("/refreshtokencookie")
    public ResponseEntity<SuccessResponse> refreshTokenCookie(@CookieValue("refreshToken") String refreshToken, HttpServletRequest request) {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            String accessToken = authorizationHeader.substring("Bearer ".length());

            if(jwtUtils.validateExpiredToken(accessToken) == false){
                throw new BadCredentialsException("access token is not expired");
            }

            if(jwtUtils.validateExpiredToken(refreshToken) == true){
                throw new BadCredentialsException("refresh token is expired");
            }

            if(refreshToken == null){
                throw new BadCredentialsException("refresh token is missing");
            }

            if(!jwtUtils.getUserNameFromJwtToken(refreshToken).equals(jwtUtils.getUserNameFromJwtToken(refreshToken))){
                throw new BadCredentialsException("two token are not a pair");
            }


            AppUserDetail userDetails =  AppUserDetail.build(userService.findByUsername(jwtUtils.getUserNameFromJwtToken(refreshToken)));

            accessToken = jwtUtils.generateJwtToken(userDetails);

            SuccessResponse response = new SuccessResponse();
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Login successful");
            response.setSuccess(true);

            response.getData().put("accessToken",accessToken);
            response.getData().put("refreshToken",refreshToken);

            return new ResponseEntity<SuccessResponse>(response,HttpStatus.OK);
        }
        else
        {
            throw new BadCredentialsException("access token is missing");
        }
    }

    private ResponseEntity SendErrorValid(String field, String message,String title){
        ErrorResponseMap errorResponseMap = new ErrorResponseMap();
        Map<String,String> temp =new HashMap<>();
        errorResponseMap.setMessage(title);
        temp.put(field,message);
        errorResponseMap.setStatus(HttpStatus.BAD_REQUEST.value());
        errorResponseMap.setDetails(temp);
        return ResponseEntity
                .badRequest()
                .body(errorResponseMap);
    }

    @GetMapping("/active")
    public ResponseEntity<SuccessResponse> activeToken( @RequestParam(defaultValue = "") String key
    ) {
            if(key == null || key ==""){
                throw new BadCredentialsException("key active is not valid");
            }

            String username = jwtUtils.getUserNameFromJwtToken(key);
            UserEntity user = userService.findByUsername(username);

            if(user == null){
                throw new RecordNotFoundException("Not found, please register again");
            }

            if(user.isActive() == true){
                throw new RecordNotFoundException("user already has been actived!");
            }

            userService.updateActive(user);



            SuccessResponse response = new SuccessResponse();
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Active successful");
            response.setSuccess(true);

            response.getData().put("email",user.getEmail());

            return new ResponseEntity<SuccessResponse>(response,HttpStatus.OK);
    }

    @PostMapping("/reactive")
    public ResponseEntity<SuccessResponse> reActiveToken(@RequestBody @Valid ReActiveRequest reActiveRequest  , BindingResult errors) throws Exception {

        if (errors.hasErrors()) {
            throw new MethodArgumentNotValidException(errors);
        }
        if (reActiveRequest == null) {
            throw new HttpMessageNotReadableException("Missing field");
        }

        if(!userService.existsByEmail(reActiveRequest.getEmail())){
            throw new HttpMessageNotReadableException("Email is not Registed");
        }

        UserEntity user = userService.getUser(reActiveRequest.getEmail());

        if(user.isStatus() == true){
            throw new HttpMessageNotReadableException("user already has been actived!");
        }


        try{

            emailService.sendActiveMessage(user);


            SuccessResponse response = new SuccessResponse();
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Resend email successful");
            response.setSuccess(true);

            response.getData().put("email",user.getEmail());

            return new ResponseEntity<SuccessResponse>(response,HttpStatus.OK);
        }catch (Exception ex){
            throw  new Exception("Some error when send active email");
        }
    }

    @PostMapping("/forgetpassword")
    public ResponseEntity<SuccessResponse> forgetPassword(@RequestBody @Valid ReActiveRequest reActiveRequest  , BindingResult errors) throws Exception {

        if (errors.hasErrors()) {
            throw new MethodArgumentNotValidException(errors);
        }
        if (reActiveRequest == null) {
            throw new HttpMessageNotReadableException("Missing field");
        }

        if(!userService.existsByEmail(reActiveRequest.getEmail())){
            throw new HttpMessageNotReadableException("Email is not Registed");
        }

        UserEntity user = userService.getUser(reActiveRequest.getEmail());

        try{

            RandomString gen = new RandomString(8, ThreadLocalRandom.current());
            String newpass = gen.nextString();

            user = userService.updateUserPassword(user,newpass);
            emailService.sendForgetPasswordMessage(user,newpass);


            SuccessResponse response = new SuccessResponse();
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Send email with new password successful");
            response.setSuccess(true);
            response.getData().put("email",user.getEmail());

            return new ResponseEntity<SuccessResponse>(response,HttpStatus.OK);
        }catch (Exception ex){
            throw  new Exception("Some error when send active email");
        }
    }
}
