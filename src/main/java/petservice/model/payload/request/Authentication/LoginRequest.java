package petservice.model.payload.request.Authentication;

import lombok.*;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class LoginRequest {
    @NotEmpty
    String username;
    @NotEmpty
    String password;
}
