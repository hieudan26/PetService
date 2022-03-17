package petservice.model.payload.request.ImagePetResources;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AddImagePetRequest {
    private String url;
}

