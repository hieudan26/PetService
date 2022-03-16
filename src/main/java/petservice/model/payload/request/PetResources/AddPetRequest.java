package petservice.model.payload.request.PetResources;

import lombok.*;
import petservice.model.Entity.ImagePetEntity;
import petservice.model.payload.request.ImagePetResources.AddImagePetRequest;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AddPetRequest {
    private String name;
    private String gender;
    private String location;
    private String age;
    private String size;
    private String description;
    private String vaccinated;
    private String status;
    private String price;
    private List<AddImagePetRequest> imagePetEntityList;
}