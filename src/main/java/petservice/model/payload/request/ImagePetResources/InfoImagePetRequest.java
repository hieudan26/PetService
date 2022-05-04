package petservice.model.payload.request.ImagePetResources;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
//@NoArgsConstructor annotation sẽ sinh ra một constructor mặc định không chứa các tham số đầu vào. Mặc định constructor này là public.
@NoArgsConstructor
//Mặc định constructor được sinh ra là public access modifiers.
@AllArgsConstructor
@Data
public class InfoImagePetRequest {
    @NotBlank(message = "Không tồn tại url nào!")
    private String url;
}
