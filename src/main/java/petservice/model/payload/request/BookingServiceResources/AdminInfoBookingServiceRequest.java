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
public class AdminInfoBookingServiceRequest {
    @NotNull(message = "thieu ngay hen")
    private LocalDateTime dateBooking;
    @NotNull(message = "thieu tinh trang")
    private Boolean payment;
    private String status;
    @NotEmpty(message = "Thiếu service id")
    private String serviceId;
    @NotEmpty(message = "Thiếu service id")
    private String  username;
}
