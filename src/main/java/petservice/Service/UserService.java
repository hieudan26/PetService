package petservice.Service;


import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import petservice.model.Entity.RoleEntity;
import petservice.model.Entity.UserEntity;
import petservice.model.payload.request.UserResources.InfoUserRequest;


import java.util.List;

@Component
@Service
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
    UserEntity findById(String id);
    UserEntity findByEmail(String email);
    UserEntity updateUserInfo(UserEntity user, InfoUserRequest userInfo);
    UserEntity updateUserPassword(UserEntity user, String password);
    void deleteUserByUsername(String username);
    void deleteUserById(String id);
    void deleteUsersById(String[] ids);
    UserEntity updateActive(UserEntity user);
    UserEntity updateAvatar(UserEntity user,String url);
    UserEntity setStatus(UserEntity user,Boolean status);
}
