package petservice.model.payload.request.BookingServiceResources;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AdminAddBookingServiceRequest {
    @NotEmpty(message = "Thiếu ngày hẹn")
    private LocalDateTime dateBooking;
    @NotEmpty(message = "Thiếu tên user")
    private String userBookService;
    @NotEmpty(message = "Thiếu service id")
    private String serviceId;
}
