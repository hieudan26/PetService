package petservice.model.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.rest.core.annotation.RestResource;

import javax.persistence.*;
import java.util.Date;
@RestResource(exported=false)
@Entity
@Table(name = "\"BookingService\"", schema = "public")
public class BookingServiceEntity {
    @JsonIgnore
    private String id;
    private Date dateBooking;
    private boolean payment;
    private String status;
    private UserEntity userBookService;
    private ServiceEntity service;



    @ManyToOne
    @JoinColumn(name = "\"IdUser\"")
    public UserEntity getUserBookService() {
        return userBookService;
    }

    public void setUserBookService(UserEntity userBookService) {
        this.userBookService = userBookService;
    }

    @ManyToOne
    @JoinColumn(name = "\"IdService\"")
    public ServiceEntity getService() {
        return service;
    }

    public void setService(ServiceEntity service) {
        this.service = service;
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
    @Column(name = "\"DateBooking\"")
    public Date getDateBooking() {
        return dateBooking;
    }

    public void setDateBooking(Date dateBooking) {
        this.dateBooking = dateBooking;
    }

    @Basic
    @Column(name = "\"Payment\"")
    public boolean isPayment() {
        return payment;
    }

    public void setPayment(boolean payment) {
        this.payment = payment;
    }

    @Basic
    @Column(name = "\"Status\"")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BookingServiceEntity that = (BookingServiceEntity) o;

        if (status != that.status) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (dateBooking != null ? !dateBooking.equals(that.dateBooking) : that.dateBooking != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (dateBooking != null ? dateBooking.hashCode() : 0);
        result = 31 * result + (payment ? 1 : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }
}
