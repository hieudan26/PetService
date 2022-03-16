package petservice.model.payload.request.UserResources;

import lombok.*;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class DeleteUsersRequest {
    @NotEmpty(message = "No item is chosen")
    private String[] ids;
}
