package petservice.model.payload.request.PetResources;

import lombok.*;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class DeletePetRequest {
    @NotEmpty(message = "No item is chosen")
    private String[] ids;
}
