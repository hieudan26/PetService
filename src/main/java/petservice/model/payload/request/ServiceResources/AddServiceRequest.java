package petservice.model.payload.request.ServiceResources;


import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigInteger;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AddServiceRequest {
    @NotEmpty(message = "Thiếu Name")
    private String name;
    private String description;
    @NotNull(message = "Thiếu giá")
    private BigInteger price;
    @NotNull(message = "Thiếu slot")
    private BigInteger slot;
    private Boolean status;
    private String image;
}
