package petservice.model.payload.request.BookingServiceResources;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AddListBookingServiceByCustomerRequest {
    @NotNull(message = "Thiếu ngày hẹn")
    private LocalDateTime dateBooking;
    @NotEmpty(message = "Thiếu service id")
    private List<String> serviceIds;
}
