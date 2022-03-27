package petservice.model.payload.request.ImagePetResources;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AddImagePetRequest {
    @NotBlank(message = "Không tồn tại url nào!")
    private String url;
}

