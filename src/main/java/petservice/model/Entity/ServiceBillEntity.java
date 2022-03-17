package petservice.model.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.rest.core.annotation.RestResource;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
@RestResource(exported=false)
@Entity
@Table(name = "\"ServiceBill\"", schema = "\"public\"")
public class ServiceBillEntity {
    @JsonIgnore
    private String id;
    private String paymentMethod;
    private LocalDateTime paymentDate;
    private long amount;
    private BookingServiceEntity bookingService;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "\"IdBookingService\"", referencedColumnName = "\"Id\"")
    public BookingServiceEntity getBookingService() {
        return bookingService;
    }

    public void setBookingService(BookingServiceEntity bookingService) {
        this.bookingService = bookingService;
    }

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "\"Id\"")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "\"PaymentMethod\"")
    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    @Basic
    @Column(name = "\"PaymentDate\"")
    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDateTime paymentDate) {
        this.paymentDate = paymentDate;
    }

    @Basic
    @Column(name = "\"Amount\"")
    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ServiceBillEntity that = (ServiceBillEntity) o;

        if (amount != that.amount) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (paymentMethod != null ? !paymentMethod.equals(that.paymentMethod) : that.paymentMethod != null)
            return false;
        if (paymentDate != null ? !paymentDate.equals(that.paymentDate) : that.paymentDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (paymentMethod != null ? paymentMethod.hashCode() : 0);
        result = 31 * result + (paymentDate != null ? paymentDate.hashCode() : 0);
        result = 31 * result + (int) (amount ^ (amount >>> 32));
        return result;
    }
}
