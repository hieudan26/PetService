package petservice.model.payload.request.BookingServiceResources;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class InfoBookingServiceRequest {
    @NotEmpty(message = "thieu ngay hen")
    private LocalDateTime dateBooking;
    @NotEmpty(message = "thieu tinh trang")
    private Boolean payment;
    private String status;
    @NotEmpty(message = "thieu nguoi dat")
    private String userBookService;
    @NotEmpty(message = "thieu loai dich vu")
    private String serviceName;
}
