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
        UserEntity user = new UserEntity("hieudankt ","hieudankt@gmail.com","123456789");

        user = userService.saveUser(user,"USER");

    }

}
