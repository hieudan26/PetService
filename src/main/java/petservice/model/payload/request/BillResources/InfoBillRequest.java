package petservice.model.payload.request.BillResources;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigInteger;
import java.time.LocalDateTime;

@Getter
@Setter
//@NoArgsConstructor annotation sẽ sinh ra một constructor mặc định không chứa các tham số đầu vào. Mặc định constructor này là public.
@NoArgsConstructor
//Mặc định constructor được sinh ra là public access modifiers.
@AllArgsConstructor
@Data
public class InfoBillRequest {
    @NotBlank(message = "Empty User")
    private String idUser;
    @NotBlank(message = "Empty Pet")
    private String idPet;
    @NotNull(message = "Empty Price")
    private BigInteger price;
    private String methodPayment;
    private LocalDateTime paymentDate;
}
