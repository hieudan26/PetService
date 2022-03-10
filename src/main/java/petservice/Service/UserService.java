package petservice.Service;


import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import petservice.model.Entity.RoleEntity;
import petservice.model.Entity.UserEntity;
import petservice.model.payload.request.UserResources.InfoUserRequest;


import java.util.List;

@Component
public interface UserService {
    List<UserEntity> getAllUser(Pageable pageable);
    UserEntity saveUser(UserEntity user, String roleName);
    RoleEntity saveRole(RoleEntity role);
    void addRoleToUser(String email, String roleName);
    UserEntity getUser(String email);
    List<UserEntity> getUsers();
    Boolean existsByEmail(String email);
    Boolean existsByUsername(String username);
    UserEntity findByUsername(String username);
    UserEntity findByEmail(String email);
    UserEntity updateUserInfo(UserEntity user, InfoUserRequest userInfo);
    UserEntity updateUserPassword(UserEntity user, String password);
    Integer deleteUser(String username);
    UserEntity updateActive(UserEntity user);
    UserEntity setStatus(UserEntity user,Boolean status);
}
