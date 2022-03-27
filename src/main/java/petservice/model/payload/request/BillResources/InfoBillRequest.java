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
public class InfoBillRequest {
    @NotEmpty(message = "Empty User")
    private String idUser;
    @NotEmpty(message = "Empty Pet")
    private String idPet;
    @NotNull(message = "")
    private BigInteger price;
    private String methodPayment;
    private LocalDateTime paymentDate;
}
