package petservice.Service.Impl;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import petservice.Service.RoleService;
import petservice.model.Entity.RoleEntity;
import petservice.repository.RoleRepository;

import javax.transaction.Transactional;

@Service @RequiredArgsConstructor @Transactional @Slf4j
public class RoleServiceImpl implements RoleService {

    final RoleRepository roleRepository;

    @Override
    public Boolean existsByRoleName(String RoleName) {
        return roleRepository.existsByName(RoleName);
    }

    @Override
    public RoleEntity findByRoleName(String RoleName) {
        return roleRepository.findByName(RoleName).get();
    }
}
