package petservice.Service.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import petservice.Service.PetService;
import petservice.mapping.PetMapping;
import petservice.model.Entity.PetEntity;
import petservice.model.payload.request.PetResources.InfoPetRequest;
import petservice.repository.ImagePetRepository;
import petservice.repository.PetRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
//@RequiredArgsConstructor sẽ sinh ra một constructor với các tham số bắt buộc phải có giá trị. Các thuộc tính final và các thuộc tính được đánh dấu @NonNull sẽ bị bắt buộc phải chứa giá trị trong constructor.
@RequiredArgsConstructor
//Quy dinh la 1 transactional
@Transactional
//đang sử dụng Simple Logging Facade for Java
@Slf4j
public class PetServiceImpl implements PetService {
    final PetRepository petRepository;
    final ImagePetRepository imagePetRepository;

    @Override
    public Page<PetEntity> getAllPet(Pageable pageable) throws Exception {
        if (petRepository.findAllByIdNotNull(pageable).isEmpty()) {
            return null;
        }
        return petRepository.findAllByIdNotNull(pageable);
    }

    @Override
    public Page<PetEntity> getAllByCategor(Pageable pageable, String category) throws Exception {
        if (petRepository.findAllByCategory(pageable,category).isEmpty()) {
            return null;
        }
        return petRepository.findAllByCategory(pageable,category);
    }

    @Override
    public PetEntity savePet(PetEntity pet) {
        return petRepository.save(pet);
    }

    @Override
    public PetEntity updatePetInfo(PetEntity pet, InfoPetRequest petInfo) {
        pet = PetMapping.UpdatePetInfoByPet(pet, petInfo);
        return petRepository.save(pet);
    }

    @Override
    public PetEntity getPet(String id) throws Exception {

        log.info("Fetching pet {}", id);
        if (petRepository.findById(id).isEmpty()){
            return null;
        }
        return petRepository.findById(id).get();
    }

    @Override
    public List<PetEntity> getPets() {
        log.info("Fetching all pets ");
        return petRepository.findAll();
    }

    @Override
    public PetEntity findById(String id) {
        return petRepository.findById(id).get();
    }

    @Override
    public PetEntity setStatus(PetEntity pet, Boolean status) {
        pet.setStatus(status);
        return petRepository.save(pet);
    }

    @Override
    public void deletImagePet(String id) {
        imagePetRepository.deleteByPet(findById(id));
    }

    @Override
    public void deletePet(String id) {
            petRepository.deleteById(id);
    }

    @Override
    public void deletePets(String[] ids) {
        for (String id : ids) {
            deletePet(id);
        }
    }
}
