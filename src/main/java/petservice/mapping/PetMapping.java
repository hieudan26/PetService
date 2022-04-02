package petservice.mapping;

import petservice.model.Entity.ImagePetEntity;
import petservice.model.Entity.PetEntity;
import petservice.model.Entity.ServiceEntity;
import petservice.model.payload.request.ImagePetResources.AddImagePetRequest;
import petservice.model.payload.request.PetResources.AddPetRequest;
import petservice.model.payload.request.PetResources.InfoPetRequest;
import petservice.model.payload.request.ServiceResources.AddServiceRequest;
import petservice.model.payload.request.ServiceResources.InfoServiceRequest;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class PetMapping {
    public static PetEntity ModelToEntity(AddPetRequest addPetRequest) {
        PetEntity newPet = new PetEntity();
        newPet.setName(addPetRequest.getName());
        newPet.setGender(addPetRequest.getGender());
        newPet.setLocation(addPetRequest.getLocation());
        newPet.setDescription(addPetRequest.getDescription());
        newPet.setBreed(addPetRequest.getBreed());
        newPet.setCategory(addPetRequest.getCategory());


        if (addPetRequest.getAge().equals("")){
            newPet.setAge(new BigInteger("0"));
        }
        else{
            newPet.setAge(addPetRequest.getAge());
        }

        if (addPetRequest.getSize().equals("")){
            newPet.setSize(new BigInteger("0"));
        }
        else{
            newPet.setSize(addPetRequest.getSize());
        }

        if (addPetRequest.getPrice().equals("")){
            newPet.setPrice(new BigInteger("0"));
        }
        else{
            newPet.setPrice(addPetRequest.getPrice());
        }

        if (addPetRequest.getVaccinated().toString().trim().equals("")){
            newPet.setVaccinated(false);
        }
        else {
            newPet.setVaccinated(addPetRequest.getVaccinated());
        }

        if (addPetRequest.getStatus().toString().trim().equals("")){
            newPet.setStatus(true);
        }
        else {
            newPet.setStatus(addPetRequest.getStatus());
        }
        List<ImagePetEntity> ListImage = new ArrayList<ImagePetEntity>();
        for (AddImagePetRequest addImagePetRequest : addPetRequest.getImagePetEntityList()) {
            ImagePetEntity imagePetEntity = new ImagePetEntity();
            imagePetEntity = ImagePetMapping.ModelToEntity(addImagePetRequest);
            imagePetEntity.setPet(newPet);
            ListImage.add(imagePetEntity);
        }
        newPet.setImagePetEntityList(ListImage);

        return newPet;
    }


    public static PetEntity UpdatePetInfoByPet(PetEntity pet, InfoPetRequest petInfo) {
        pet.setName(petInfo.getName());
        pet.setGender(petInfo.getGender());
        pet.setLocation(petInfo.getLocation());
        pet.setBreed(petInfo.getBreed());
        pet.setDescription(petInfo.getDescription());
        pet.setCategory(petInfo.getCategory());


        if (petInfo.getAge().equals("")){
            pet.setAge(new BigInteger("0"));
        }
        else{
            pet.setAge(petInfo.getAge());
        }

        if (petInfo.getSize().equals("")){
            pet.setSize(new BigInteger("0"));
        }
        else{
            pet.setSize(petInfo.getSize());
        }

        if (petInfo.getPrice().equals("")){
            pet.setPrice(new BigInteger("0"));
        }
        else{
            pet.setPrice(petInfo.getPrice());
        }

        if (petInfo.getVaccinated().toString().trim().equals("")){
            pet.setVaccinated(false);
        }
        else {
            pet.setVaccinated(petInfo.getVaccinated());
        }

        if (petInfo.getStatus().toString().trim().equals("")){
            pet.setStatus(true);
        }
        else {
            pet.setStatus(petInfo.getStatus());
        }

        List<ImagePetEntity> ListImage = new ArrayList<ImagePetEntity>();
        for (AddImagePetRequest addImagePetRequest : petInfo.getImagePetEntityList()) {
            ImagePetEntity imagePetEntity = new ImagePetEntity();
            imagePetEntity = ImagePetMapping.ModelToEntity(addImagePetRequest);
            imagePetEntity.setPet(pet);
            ListImage.add(imagePetEntity);
        }
        pet.setImagePetEntityList(ListImage);


        return pet;
    }
}
