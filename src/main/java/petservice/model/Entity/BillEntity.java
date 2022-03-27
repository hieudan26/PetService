package petservice.model.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.rest.core.annotation.RestResource;

import javax.persistence.*;
import java.math.BigInteger;
import java.time.LocalDateTime;

@RestResource(exported=false)
@Entity
@Table(name ="\"Bill\"", schema ="\"public\"")
public class BillEntity {

    private String id;
    private String methodPayment;
    private BigInteger price;
    private UserEntity userBuyPet;
    private PetEntity petSale;
    private LocalDateTime paymentDate;
    @ManyToOne
    @JoinColumn(name = "\"IdUser\"")
    public UserEntity getUserBuyPet() {
        return userBuyPet;
    }

    public void setUserBuyPet(UserEntity userBuyPet) {
        this.userBuyPet = userBuyPet;
    }

    @OneToOne
    @JoinColumn(name = "\"IdPet\"")
    public PetEntity getPetSale() {
        return petSale;
    }
    public void setPetSale(PetEntity petSale) {
        this.petSale = petSale;
    }

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name ="\"Id\"")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name ="\"MethodPayment\"")
    public String getMethodPayment() {
        return methodPayment;
    }

    public void setMethodPayment(String methodPayment) {
        this.methodPayment = methodPayment;
    }

    @Basic
    @Column(name ="\"Price\"")
    public BigInteger getPrice() {
        return price;
    }

    public void setPrice(BigInteger price) {
        this.price = price;
    }

    @Basic
    @Column(name ="\"PaymentDate\"")
    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }
    public void setPaymentDate(LocalDateTime paymentDate) {
        this.paymentDate = paymentDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BillEntity that = (BillEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (methodPayment != null ? !methodPayment.equals(that.methodPayment) : that.methodPayment != null)
            return false;
        if (price != null ? !price.equals(that.price) : that.price != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (methodPayment != null ? methodPayment.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        return result;
    }
}
