package petservice.model.payload.request.BookingServiceResources;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class InfoBookingServiceRequest {
    @NotEmpty(message = "thieu ngay hen")
    private String dateBooking;
    @NotNull(message = "thieu tinh trang")
    private Boolean payment;
    private String status;
    @NotEmpty(message = "thieu nguoi dat")
    private String userBookService;
    @NotEmpty(message = "Thiếu service id")
    private String serviceId;
}
