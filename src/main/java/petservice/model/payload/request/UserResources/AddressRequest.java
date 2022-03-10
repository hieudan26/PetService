package petservice.model.payload.request.UserResources;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AddressRequest {
    private String houseNumber;
    private String streetName;
    private String city;
    private String province;
    private String country;
}
