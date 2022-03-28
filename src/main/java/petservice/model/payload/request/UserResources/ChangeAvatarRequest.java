package petservice.model.payload.request.UserResources;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ChangeAvatarRequest {
    @NotEmpty
    String url;
}
