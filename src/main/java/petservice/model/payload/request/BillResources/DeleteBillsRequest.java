package petservice.model.payload.request.BillResources;

import lombok.*;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class DeleteBillsRequest {
    @NotEmpty(message = "No item is chosen")
    private String[] ids;
}
