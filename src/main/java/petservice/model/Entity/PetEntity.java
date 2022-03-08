package petservice.model.Entity;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.rest.core.annotation.RestResource;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.List;
@RestResource(exported=false)
@Entity
@Table(name = "\"Pet\"", schema = "\"public\"")
public class PetEntity {
    private String id;
    private String name;
    private String gender;
    private String location;
    private BigInteger age;
    private BigInteger size;
    private String description;
    private boolean vaccinated;
    private boolean status;
    private BigInteger price;
    private List<ImagePetEntity> imagePetEntityList;
    private List<BillEntity> billEntityList;
    @OneToMany(mappedBy = "pet", cascade = CascadeType.ALL)
    public List<ImagePetEntity> getImagePetEntityList() {
        return imagePetEntityList;
    }

    public void setImagePetEntityList(List<ImagePetEntity> imagePetEntityList) {
        this.imagePetEntityList = imagePetEntityList;
    }


    @OneToMany(mappedBy = "petSale", cascade = CascadeType.ALL)
    public List<BillEntity> getBillEntityList() {
        return billEntityList;
    }

    public void setBillEntityList(List<BillEntity> billEntityList) {
        this.billEntityList = billEntityList;
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
    @Column(name = "\"Name\"")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "\"Gender\"")
    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Basic
    @Column(name = "\"Location\"")
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Basic
    @Column(name = "\"Age\"")
    public BigInteger getAge() {
        return age;
    }

    public void setAge(BigInteger age) {
        this.age = age;
    }

    @Basic
    @Column(name = "\"Size\"")
    public BigInteger getSize() {
        return size;
    }

    public void setSize(BigInteger size) {
        this.size = size;
    }

    @Basic
    @Column(name = "\"Description\"")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "\"Vaccinated\"")
    public boolean isVaccinated() {
        return vaccinated;
    }

    public void setVaccinated(boolean vaccinated) {
        this.vaccinated = vaccinated;
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
    @Column(name = "\"Price\"")
    public BigInteger getPrice() {
        return price;
    }

    public void setPrice(BigInteger price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PetEntity petEntity = (PetEntity) o;

        if (vaccinated != petEntity.vaccinated) return false;
        if (status != petEntity.status) return false;
        if (id != null ? !id.equals(petEntity.id) : petEntity.id != null) return false;
        if (name != null ? !name.equals(petEntity.name) : petEntity.name != null) return false;
        if (gender != null ? !gender.equals(petEntity.gender) : petEntity.gender != null) return false;
        if (location != null ? !location.equals(petEntity.location) : petEntity.location != null) return false;
        if (age != null ? !age.equals(petEntity.age) : petEntity.age != null) return false;
        if (size != null ? !size.equals(petEntity.size) : petEntity.size != null) return false;
        if (description != null ? !description.equals(petEntity.description) : petEntity.description != null)
            return false;
        if (price != null ? !price.equals(petEntity.price) : petEntity.price != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (gender != null ? gender.hashCode() : 0);
        result = 31 * result + (location != null ? location.hashCode() : 0);
        result = 31 * result + (age != null ? age.hashCode() : 0);
        result = 31 * result + (size != null ? size.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (vaccinated ? 1 : 0);
        result = 31 * result + (status ? 1 : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        return result;
    }
}
