package petservice.model.payload.request.ServiceResources;

import lombok.*;

@Getter
@Setter
//@NoArgsConstructor annotation sẽ sinh ra một constructor mặc định không chứa các tham số đầu vào. Mặc định constructor này là public.
@NoArgsConstructor
//Mặc định constructor được sinh ra là public access modifiers.
@AllArgsConstructor
@Data
public class InfoServiceRequest {
    private String name;
    private String description;
    private String  price;
    private String slot;
    private String status;
}
