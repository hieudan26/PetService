package petservice.model.payload.request.ServiceResources;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigInteger;

@Getter
@Setter
//@NoArgsConstructor annotation sẽ sinh ra một constructor mặc định không chứa các tham số đầu vào. Mặc định constructor này là public.
@NoArgsConstructor
//Mặc định constructor được sinh ra là public access modifiers.
@AllArgsConstructor
@Data
public class InfoServiceRequest {
    @NotEmpty(message = "Thiếu Name")
    private String name;
    private String description;
    @NotNull(message = "Thiếu giá")
    private BigInteger price;
    @NotNull(message = "Thiếu slot")
    private BigInteger slot;
    @NotNull(message = "Thiếu status")
    private Boolean status;
}
