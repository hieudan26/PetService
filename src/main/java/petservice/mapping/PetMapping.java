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


        if (addPetRequest.getAge().trim().equals("")){
            newPet.setAge(new BigInteger("0"));
        }
        else{
            newPet.setAge(new BigInteger(addPetRequest.getAge()));
        }

        if (addPetRequest.getSize().trim().equals("")){
            newPet.setSize(new BigInteger("0"));
        }
        else{
            newPet.setSize(new BigInteger(addPetRequest.getSize()));
        }

        if (addPetRequest.getPrice().trim().equals("")){
            newPet.setPrice(new BigInteger("0"));
        }
        else{
            newPet.setPrice(new BigInteger(addPetRequest.getPrice()));
        }

        if (addPetRequest.getVaccinated().toString().trim().equals("")){
            newPet.setVaccinated(false);
        }
        else {
            newPet.setVaccinated(Boolean.parseBoolean(addPetRequest.getVaccinated()));
        }

        if (addPetRequest.getStatus().toString().trim().equals("")){
            newPet.setStatus(true);
        }
        else {
            newPet.setStatus(Boolean.parseBoolean(addPetRequest.getStatus()));
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
        pet.setDescription(petInfo.getDescription());

        if (petInfo.getAge().trim().equals("")){
            pet.setAge(new BigInteger("0"));
        }
        else{
            pet.setAge(new BigInteger(petInfo.getAge()));
        }

        if (petInfo.getSize().trim().equals("")){
            pet.setSize(new BigInteger("0"));
        }
        else{
            pet.setSize(new BigInteger(petInfo.getSize()));
        }

        if (petInfo.getPrice().trim().equals("")){
            pet.setPrice(new BigInteger("0"));
        }
        else{
            pet.setPrice(new BigInteger(petInfo.getPrice()));
        }

        if (petInfo.getVaccinated().toString().trim().equals("")){
            pet.setVaccinated(false);
        }
        else {
            pet.setVaccinated(Boolean.parseBoolean(petInfo.getVaccinated()));
        }

        if (petInfo.getStatus().toString().trim().equals("")){
            pet.setStatus(true);
        }
        else {
            pet.setStatus(Boolean.parseBoolean(petInfo.getStatus()));
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
