package petservice.model.payload.request.PetResources;

import lombok.*;
import petservice.model.Entity.ImagePetEntity;
import petservice.model.payload.request.ImagePetResources.AddImagePetRequest;

import java.util.List;


@Getter
@Setter
//@NoArgsConstructor annotation sẽ sinh ra một constructor mặc định không chứa các tham số đầu vào. Mặc định constructor này là public.
@NoArgsConstructor
//Mặc định constructor được sinh ra là public access modifiers.
@AllArgsConstructor
@Data
public class InfoPetRequest {
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
