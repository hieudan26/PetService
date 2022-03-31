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
public class InfoBookingServiceRequest {
    @NotNull(message = "thieu ngay hen")
    private LocalDateTime dateBooking;
    @NotNull(message = "thieu tinh trang")
    private Boolean payment;
    private String status;
    @NotEmpty(message = "thieu nguoi dat")
    private String userBookService;
    @NotEmpty(message = "Thiếu service id")
    private String serviceId;
}
