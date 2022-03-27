package petservice.model.payload.request.BillResources;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigInteger;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AddBillRequest {
    @NotEmpty(message = "Empty User")
    private String idUser;
    @NotEmpty(message = "Empty Pet")
    private String idPet;
    @NotNull(message = "Empty Price")
    private BigInteger price;
    private String methodPayment;
    private LocalDateTime paymentDate;
}
