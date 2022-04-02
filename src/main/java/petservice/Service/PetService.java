package petservice.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import petservice.model.Entity.PetEntity;
import petservice.model.payload.request.PetResources.InfoPetRequest;

import java.util.List;

public interface PetService {
    Page<PetEntity> getAllPet(Pageable pageable) throws Exception;
    Page<PetEntity> getAllByCategor(Pageable pageable, String category) throws Exception;
    PetEntity savePet(PetEntity pet);
    PetEntity updatePetInfo(PetEntity service, InfoPetRequest petInfo);
    PetEntity getPet(String id) throws Exception;
    List<PetEntity> getPets();
    PetEntity findById(String id);
    PetEntity setStatus(PetEntity pet,Boolean status);
    void deletImagePet(String id);
    void deletePet(String id);
    void deletePets(String[] ids);
}
