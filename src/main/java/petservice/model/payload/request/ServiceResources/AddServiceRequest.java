package petservice.model.payload.request.ServiceResources;


import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AddServiceRequest {
    @NotEmpty(message = "Thiếu Name")
    private String name;

    private String price;
    private String description;
    private String slot;
    private String status;
}
