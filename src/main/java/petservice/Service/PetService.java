package petservice.Service;

import org.springframework.data.domain.Pageable;
import petservice.model.Entity.PetEntity;
import petservice.model.payload.request.PetResources.InfoPetRequest;

import java.util.List;

public interface PetService {
    List<PetEntity> getAllPet(Pageable pageable);
    PetEntity savePet(PetEntity pet);
    PetEntity updatePetInfo(PetEntity service, InfoPetRequest petInfo);
    PetEntity getPet(String id);
    List<PetEntity> getPets();
    PetEntity findById(String id);
    PetEntity setStatus(PetEntity pet,Boolean status);
    Integer deletePet(String id);
    void deletImagePet(String id);
    void deletePet(String[] ids);
}
