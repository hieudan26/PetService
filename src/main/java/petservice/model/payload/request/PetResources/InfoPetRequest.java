package petservice.model.payload.request.PetResources;

import lombok.*;
import petservice.model.Entity.ImagePetEntity;
import petservice.model.payload.request.ImagePetResources.AddImagePetRequest;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigInteger;
import java.util.List;


@Getter
@Setter
//@NoArgsConstructor annotation sẽ sinh ra một constructor mặc định không chứa các tham số đầu vào. Mặc định constructor này là public.
@NoArgsConstructor
//Mặc định constructor được sinh ra là public access modifiers.
@AllArgsConstructor
@Data
public class InfoPetRequest {
    @NotBlank(message = "Thiếu tên")
    private String name;
    @NotBlank (message = "Thiếu giới tính")
    private String gender;
    @NotBlank (message = "Thiếu vị trí")
    private String location;
    @NotBlank (message = "Thiếu giống")
    private String breed;
    @NotNull (message = "Thiếu tuổi")
    private BigInteger age;
    @NotNull (message = "Thiếu size")
    private BigInteger size;
    private String description;
    @NotNull(message = "Thiếu vacxin")
    private Boolean vaccinated;
    private Boolean status;
    @NotNull(message = "Thiếu giá")
    private BigInteger price;
    @NotBlank(message = "Thiếu loại")
    private String category;
    private List<AddImagePetRequest> imagePetEntityList;
}
