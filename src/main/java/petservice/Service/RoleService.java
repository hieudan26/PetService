package petservice.Service;

import org.springframework.stereotype.Component;
import petservice.model.Entity.RoleEntity;

@Component
public interface RoleService {
    Boolean existsByRoleName(String RoleName);
    RoleEntity findByRoleName(String RoleName);
}
