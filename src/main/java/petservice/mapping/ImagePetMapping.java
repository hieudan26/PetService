package petservice.mapping;

import petservice.model.Entity.ImagePetEntity;
import petservice.model.Entity.PetEntity;
import petservice.model.payload.request.ImagePetResources.AddImagePetRequest;
import petservice.model.payload.request.PetResources.AddPetRequest;

public class ImagePetMapping {
    public static ImagePetEntity ModelToEntity(AddImagePetRequest addImagePetRequest){
        ImagePetEntity newImagePet = new ImagePetEntity();
        newImagePet.setUrl(addImagePetRequest.getUrl());
        return newImagePet;
    }
}
