package petservice.Service;


import org.springframework.stereotype.Component;
import petservice.model.Entity.RoleEntity;
import petservice.model.Entity.UserEntity;


import java.util.List;

@Component
public interface UserService {
    UserEntity saveUser(UserEntity user, String roleName);
    RoleEntity saveRole(RoleEntity role);
    void addRoleToUser(String email, String roleName);
    UserEntity getUser(String email);
    List<UserEntity> getUsers();
    Boolean existsByEmail(String email);
    Boolean existsByUsername(String username);
    UserEntity findByUsername(String username);
    //UserEntity updateUserInfo(UserEntity user, InfoUserRequest userInfo);
    UserEntity updateUserPassword(UserEntity user, String password);
    UserEntity deleteUser(String username);
    UserEntity updateActive(UserEntity user);
}
