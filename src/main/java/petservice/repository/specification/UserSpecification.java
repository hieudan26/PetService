package petservice.repository.specification;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.jpa.domain.Specification;
import petservice.model.Entity.AddressEntity;
import petservice.model.Entity.UserEntity;

import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.ListJoin;
import java.util.List;
import java.util.Map;

import static org.springframework.data.jpa.domain.Specification.where;

public class UserSpecification {
    public static Specification<UserEntity> getFilterAllRows(String key) {
        return (root, query, cb) -> {
            query.distinct(true); //Important because of the join in the addressAttribute specifications
            return where(
                    where(firstNameContains(key))
                            .or(lastNameContains(key))
                            .or(emailContains(key))
                            .or(phoneContains(key)
                            .or(usernameContains(key))
            )
                    .or(streetContains(key))
                    .or(cityContains(key))
                    .or(houseNumberContains(key))
                    .or(provinceContains(key))
                    .or(countryContains(key))
            ).toPredicate(root, query, cb);
        };
    }

    public static Specification<UserEntity> getFilter(Map<String,String> map) {
        return (root, query, cb) -> {
            query.distinct(true); //Important because of the join in the addressAttribute specifications
            return where(
                    where(firstNameExacts(map.get("firstName")))
                            .and(lastNameExacts(map.get("lastName")))
                            .and(emailExacts(map.get("email")))
                            .and(phoneExacts(map.get("phone")))
                            .and(usernameExacts(map.get("username")))
                            .and(activeBoolean(map.get("active")))
                            .and(statusBoolean(map.get("status")))
            ).toPredicate(root, query, cb);
        };
    }

    //contain compare
    private static Specification<UserEntity> firstNameContains(String firstName) {
        return userAttributeContains("firstName", firstName);
    }

    private static Specification<UserEntity> lastNameContains(String lastName) {
        return userAttributeContains("lastName", lastName);
    }

    private static Specification<UserEntity> emailContains(String email) {
        return userAttributeContains("email", email);
    }

    private static Specification<UserEntity> phoneContains(String email) {
        return userAttributeContains("phone", email);
    }

    private static Specification<UserEntity> usernameContains(String username) {
        return userAttributeExacts("userName", username);
    }

    //Exact compare
    private static Specification<UserEntity> firstNameExacts(String firstName) {
        return userAttributeExacts("firstName", firstName);
    }

    private static Specification<UserEntity> lastNameExacts(String lastName) {
        return userAttributeExacts("lastName", lastName);
    }

    private static Specification<UserEntity> emailExacts(String email) {
        return userAttributeExacts("email", email);
    }

    private static Specification<UserEntity> phoneExacts(String phone) {
        return userAttributeExacts("phone", phone);
    }

    private static Specification<UserEntity> usernameExacts(String username) {
        return userAttributeExacts("userName", username);
    }


    //Boolean
    private static Specification<UserEntity> activeBoolean(String active) {
        return userAttributeBoolean("active", active);
    }

    private static Specification<UserEntity> statusBoolean(String status) {
        return userAttributeBoolean("status", status);
    }


    private static Specification<UserEntity> userAttributeContains(String attribute, String value) {
        return (root, query, cb) -> {
            if(value == null) {
                return null;
            }

            return cb.like(
                    cb.lower(root.get(attribute)),
                    containsLowerCase(value)
            );
        };
    }

    private static Specification<UserEntity> userAttributeExacts(String attribute, String value) {
        return (root, query, cb) -> {
            if(value == null) {
                return null;
            }

            return cb.equal(
                    cb.lower(root.get(attribute)),
                    exactLowerCase(value)
            );
        };
    }
    private static Specification<UserEntity> userAttributeBoolean(String attribute, String value) {
        return (root, query, cb) -> {
            if(value == null) {
                return null;
            }

            return cb.equal(
                    root.get(attribute),
                    Boolean.parseBoolean(value)
            );
        };
    }

    private static Specification<UserEntity> houseNumberContains(String city) {
        return addressAttributeContains("houseNumber", city);
    }

    private static Specification<UserEntity> streetContains(String street) {
        return addressAttributeContains("streetName", street);
    }

    private static Specification<UserEntity> cityContains(String city) {
        return addressAttributeContains("city", city);
    }

    private static  Specification<UserEntity> provinceContains(String city) {
        return addressAttributeContains("province", city);
    }

    private static Specification<UserEntity> countryContains(String city) {
        return addressAttributeContains("country", city);
    }

    private static Specification<UserEntity> addressAttributeContains(String attribute, String value) {
        return (root, query, cb) -> {
            if(value == null) {
                return null;
            }

            return cb.like(
                    cb.lower(root.get("address").get(attribute)),
                    containsLowerCase(value)
            );
        };
    }

    private static String containsLowerCase(String searchField) {
        return "%" + searchField.toLowerCase() + "%";
    }

    private static String exactLowerCase(String searchField) {
        return  searchField.toLowerCase();
    }
}
