package petservice;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.junit4.SpringRunner;
import petservice.Service.PetService;
import petservice.Service.ServiceService;
import petservice.Service.UserService;
import petservice.model.Entity.*;
import petservice.repository.RoleRepository;
import petservice.repository.UserRepository;
import petservice.repository.specification.UserSpecification;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest(classes = PetServiceApplication.class)
class PetServiceApplicationTests {
    @Autowired
    UserService userService;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PetService petService;
    @Autowired
    ServiceService serviceService;
    @Autowired
    UserRepository userRepository;

    @Test
    void contextLoads() {

//        Pageable pageable = PageRequest.of(0, 3, Sort.by("CreateAt"));
//        Page<UserEntity> userEntityPage = userRepository.findAll(Specification.where(UserSpecification.getFilterAllRows("HCM")),pageable);


    }
//    @Test
//    void testPetService(){
//        PetEntity pet = new PetEntity();
////        pet.setName("daylaname2");
////        pet.setGender("male");
////        pet.setLocation("VN");
////        pet.setSize(new BigInteger("2"));
////        pet.setAge(new BigInteger("1"));
////        pet.setStatus(true);
////        pet.setDescription("des");
////        pet.setVaccinated(true);
////        pet.setPrice(new BigInteger("1000000"));
////        petService.savePet(pet);
//        pet = petService.findById("1d5472ec-9b21-489e-8327-6e5de2a85cb3");
////        petService.deletePet("a89a88fe-00a5-4ee4-8564-28de9dd132cf");
//        System.out.println(pet.getName());
////        imagePetService.deleteImagesByPet(pet);
////        ServiceEntity service = new ServiceEntity();
//    }
//
//    @Test
//    void TestImagePet(){
//
//    }

}
