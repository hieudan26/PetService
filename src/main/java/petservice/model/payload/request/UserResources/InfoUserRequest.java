package petservice.model.payload.request.UserResources;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class InfoUserRequest {
    private String firstName;
    private String lastName;
    private String phone;
    private AddressRequest address;

}
