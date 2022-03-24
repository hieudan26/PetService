package petservice.model.payload.request.BillResources;

import lombok.*;

import javax.validation.constraints.NotEmpty;

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
    @NotEmpty(message = "Empty Price")
    private String price;
    private String methodPayment;
    private String paymentDate;
}
