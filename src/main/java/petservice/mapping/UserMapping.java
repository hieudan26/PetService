package petservice.mapping;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import petservice.model.Entity.AddressEntity;
import petservice.model.Entity.UserEntity;
import petservice.model.payload.request.UserResources.InfoUserRequest;
import petservice.model.payload.request.UserResources.RegisterAdminRequest;
import petservice.model.payload.request.Authentication.RegisterRequest;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class UserMapping {
    public static UserEntity registerToEntity(RegisterRequest registerRequest) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        registerRequest.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        return new UserEntity(registerRequest.getUsername(),registerRequest.getEmail(),registerRequest.getPassword());
    }

    public static UserEntity registerToEntity(RegisterAdminRequest registerRequest) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        registerRequest.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        return new UserEntity(registerRequest.getUsername(),registerRequest.getEmail(),registerRequest.getPassword());
    }

    public static UserEntity UpdateUserInfoByUser(UserEntity user, InfoUserRequest userInfo) {
        user.setUpdate(LocalDateTime.now(ZoneId.of("GMT+07:00")));
        user.setFirstName(userInfo.getFirstName());
        user.setLastName(userInfo.getLastName());
        user.setAvatar(userInfo.getAvatar());
        user.setPhone(userInfo.getPhone());
        if(user.getAddress() == null)
        {
            user.setAddress(new AddressEntity());
        }

        return  user = UserMapping.UpdateAddressInfoByUser(user,userInfo);
    }

    public static UserEntity UpdateAddressInfoByUser(UserEntity user, InfoUserRequest userInfo) {

        if(user.getAddress() == null)
        {
            user.setAddress(new AddressEntity());
        }
        user.getAddress().setCountry(userInfo.getAddress().getCountry());
        user.getAddress().setCity(userInfo.getAddress().getCity());
        user.getAddress().setProvince(userInfo.getAddress().getProvince());
        user.getAddress().setStreetName(userInfo.getAddress().getStreetName());
        user.getAddress().setHouseNumber(userInfo.getAddress().getHouseNumber());
        return  user;
    }
    public static UserEntity UpdatePasswordByUser(UserEntity user, String password) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode(password));
        return  user;
    }

}
