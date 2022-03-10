package petservice.model.payload.request.UserResources;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class StatusRequest {
    @NotEmpty
    @Email
    String email;

    Boolean status =false;
}
