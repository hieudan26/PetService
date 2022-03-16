package petservice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import petservice.Service.UserService;
import petservice.model.Entity.RoleEntity;
import petservice.model.Entity.UserEntity;
import petservice.repository.RoleRepository;

@SpringBootTest
class PetServiceApplicationTests {
    @Autowired
    UserService userService;
    @Autowired
    RoleRepository roleRepository;
    @Test
    void contextLoads() {
//        RoleEntity role = new RoleEntity();
//        role.setName("ADMIN");
//        roleRepository.save(role);
//        UserEntity user = new UserEntity();
//        user.setPassword("123456789");
//        user.setUserName("hieudankf");
//        user.setEmail("hieudankazf@gmail.com");
//        userService.saveUser(user,"ADMIN");
//        userService.deleteUser("hieudankf");
    }

}
