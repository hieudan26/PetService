package petservice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import petservice.Service.PetService;
import petservice.Service.ServiceService;
import petservice.Service.UserService;
import petservice.model.Entity.*;
import petservice.repository.RoleRepository;

import java.math.BigInteger;

@SpringBootTest
class PetServiceApplicationTests {
    @Autowired
    UserService userService;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PetService petService;
    @Autowired
    ServiceService serviceService;


    @Test
    void contextLoads() {
//        RoleEntity role = new RoleEntity();
//        role.setName("ADMIN");
//        roleRepository.save(role);
        UserEntity user = new UserEntity();
        user.setPassword("123456789");
        user.setUserName("hieudankf");
        user.setEmail("hieudankazf@gmail.com");
        userService.saveUser(user,"ADMIN");
        userService.deleteUser("hieudankf");
    }
    @Test
    void testPetService(){
        PetEntity pet = new PetEntity();
//        pet.setName("daylaname2");
//        pet.setGender("male");
//        pet.setLocation("VN");
//        pet.setSize(new BigInteger("2"));
//        pet.setAge(new BigInteger("1"));
//        pet.setStatus(true);
//        pet.setDescription("des");
//        pet.setVaccinated(true);
//        pet.setPrice(new BigInteger("1000000"));
//        petService.savePet(pet);
        pet = petService.findById("1d5472ec-9b21-489e-8327-6e5de2a85cb3");
//        petService.deletePet("a89a88fe-00a5-4ee4-8564-28de9dd132cf");
        System.out.println(pet.getName());
//        imagePetService.deleteImagesByPet(pet);
//        ServiceEntity service = new ServiceEntity();
    }

    @Test
    void TestImagePet(){

    }

}
