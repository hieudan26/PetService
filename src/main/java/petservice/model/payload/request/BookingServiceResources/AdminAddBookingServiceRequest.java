package petservice.model.payload.request.BookingServiceResources;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AdminAddBookingServiceRequest {
    @NotNull(message = "Thiếu username")
    private String usename;
    @NotNull(message = "Thiếu ngày hẹn")
    private LocalDateTime dateBooking;
    @NotEmpty(message = "Thiếu service id")
    private String serviceId;
}
