package petservice.mapping;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import petservice.model.Entity.UserEntity;
import petservice.model.payload.request.RegisterAdminRequest;
import petservice.model.payload.request.RegisterRequest;

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

//    public static UserEntity UpdateUserInfoByUser(UserEntity user, InfoUserRequest userInfo) {
//        user.setBirthdate(userInfo.getBirthdate());
//        user.setTenhienthi(userInfo.getTenhienthi());
//        user.setImage(userInfo.getImage());
//        return  user;
//    }

    public static UserEntity UpdatePasswordByUser(UserEntity user, String password) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode(password));
        return  user;
    }

}
