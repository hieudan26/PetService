package petservice.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import petservice.model.Entity.PetEntity;
import petservice.model.Entity.UserEntity;
import petservice.model.payload.request.PetResources.InfoPetRequest;

import java.util.List;
import java.util.Map;

public interface PetService {
    Page<PetEntity> getAllPet(Map<String,String> ParamMap, Pageable pageable);
    Page<PetEntity> getAllByCategor(Pageable pageable, String category);
    PetEntity savePet(PetEntity pet);
    PetEntity updatePetInfo(PetEntity service, InfoPetRequest petInfo);
    PetEntity getPet(String id);
    List<PetEntity> getPets();
    PetEntity findById(String id);
    PetEntity setStatus(PetEntity pet,Boolean status);
    void deleteImagePet(String id);
    void deletePet(String id);
    void deletePets(String[] ids);
}
