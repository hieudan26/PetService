package petservice.model.payload.request.BookingServiceResources;

import lombok.*;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AddBookingServiceRequest {
    @NotEmpty(message = "Thiếu ngày hẹn")
    private String dateBooking;
    @NotEmpty(message = "Thiếu tên user")
    private String userBookService;
    @NotEmpty(message = "Thiếu service id")
    private String serviceId;
}
