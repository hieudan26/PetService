package petservice.model.payload.request.ServiceResources;

import lombok.*;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class DeleteServiceRequest {
    @NotEmpty(message = "No item is chosen")
    private String[] ids;
}
