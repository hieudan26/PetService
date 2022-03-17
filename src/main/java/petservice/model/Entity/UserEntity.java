package petservice.model.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.rest.core.annotation.RestResource;

import javax.annotation.Nullable;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.Set;
@RestResource(exported=false)
@Entity
@Table(name = "\"User\"", schema = "\"public\"")
public class UserEntity {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String userName;
    @JsonIgnore
    private String password;
    private String avatar;
    private boolean status;
    private boolean active;
    private LocalDateTime createAt;
    private LocalDateTime update;
    private AddressEntity address;
    private Set<RoleEntity> roles;
    @JsonIgnore
    private List<BillEntity> billEntityList;
    @JsonIgnore
    private List<BookingServiceEntity> bookingServiceEntityList;

    @OneToMany(mappedBy = "userBuyPet", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public List<BillEntity> getBillEntityList() {
        return billEntityList;
    }

    public void setBillEntityList(List<BillEntity> billEntityList) {
        this.billEntityList = billEntityList;
    }

    @OneToMany(mappedBy = "userBookService", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    public List<BookingServiceEntity> getBookingServiceEntityList() {
        return bookingServiceEntityList;
    }

    public void setBookingServiceEntityList(List<BookingServiceEntity> bookingServiceEntityList) {
        this.bookingServiceEntityList = bookingServiceEntityList;
    }

    public UserEntity() {
    }

    public UserEntity(String userName, String email, String password) {
        this.email = email;
        this.userName = userName;
        this.password = password;
        this.active = false;
        this.status = true;
        this.createAt = LocalDateTime.now(ZoneId.of("GMT+07:00"));
        this.update = LocalDateTime.now(ZoneId.of("GMT+07:00"));
    }

    @Nullable
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "\"IdAddress\"", referencedColumnName = "\"Id\"")
    public AddressEntity getAddress() {
        return address;
    }

    public void setAddress(AddressEntity address) {
        this.address = address;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="\"User_Role\"",joinColumns = @JoinColumn(name="\"UserId\""),inverseJoinColumns = @JoinColumn(name="\"RoleId\""))
    public Set<RoleEntity> getRoles() { return roles; }
    public void setRoles(Set<RoleEntity> roles) { this.roles = roles; }


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
    @Column(name = "\"FirstName\"")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Basic
    @Column(name = "\"LastName\"")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Basic
    @Column(name = "\"Email\"")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "\"Phone\"")
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Basic
    @Column(name = "\"UserName\"")
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Basic
    @Column(name = "\"Password\"")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "\"Avatar\"")
    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Basic
    @Column(name = "\"Status\"")
    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Basic
    @Column(name = "\"Active\"")
    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
    @Basic
    @Column(name = "\"CreateAt\"")
    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    @Basic
    @Column(name = "\"Update\"")
    public LocalDateTime getUpdate() {
        return update;
    }

    public void setUpdate(LocalDateTime update) {
        this.update = update;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserEntity user = (UserEntity) o;

        if (status != user.status) return false;
        if (id != null ? !id.equals(user.id) : user.id != null) return false;
        if (firstName != null ? !firstName.equals(user.firstName) : user.firstName != null) return false;
        if (lastName != null ? !lastName.equals(user.lastName) : user.lastName != null) return false;
        if (email != null ? !email.equals(user.email) : user.email != null) return false;
        if (phone != null ? !phone.equals(user.phone) : user.phone != null) return false;
        if (userName != null ? !userName.equals(user.userName) : user.userName != null) return false;
        if (password != null ? !password.equals(user.password) : user.password != null) return false;
        if (avatar != null ? !avatar.equals(user.avatar) : user.avatar != null) return false;
        if (createAt != null ? !createAt.equals(user.createAt) : user.createAt != null) return false;
        if (update != null ? !update.equals(user.update) : user.update != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (userName != null ? userName.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (avatar != null ? avatar.hashCode() : 0);
        result = 31 * result + (status ? 1 : 0);
        result = 31 * result + (createAt != null ? createAt.hashCode() : 0);
        result = 31 * result + (update != null ? update.hashCode() : 0);
        return result;
    }
}
