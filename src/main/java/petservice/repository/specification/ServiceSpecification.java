package petservice.repository.specification;

import org.springframework.data.jpa.domain.Specification;
import petservice.model.Entity.ServiceEntity;

import java.math.BigInteger;
import java.util.Map;

import static org.springframework.data.jpa.domain.Specification.where;

public class ServiceSpecification {
    public static Specification<ServiceEntity> getFilterAllRows(String key) {
        return (root, query, cb) -> {
            query.distinct(true); //Important because of the join in the addressAttribute specifications
            return where(
                    where(nameContains(key))
                            .or(descriptionContains(key))
            ).toPredicate(root, query, cb);
        };
    }

    public static Specification<ServiceEntity> getFilter(Map<String,String> map) {
        return (root, query, cb) -> {
            query.distinct(true); //Important because of the join in the addressAttribute specifications
            return where(
                    where(nameExacts(map.get("name")))
                            .and(descriptionExacts(map.get("description")))
                            .and(statusBoolean(map.get("status")))
                            .and(priceInteger(map.get("price")))
                            .and(slotInteger(map.get("slot")))
            ).toPredicate(root, query, cb);
        };
    }

    //contain compare
    private static Specification<ServiceEntity> nameContains(String name) {
        return serviceAttributeContains("name", name);
    }

    private static Specification<ServiceEntity> descriptionContains(String description) {
        return serviceAttributeContains("description", description);
    }


    //Exact compare
    private static Specification<ServiceEntity> nameExacts(String name) {
        return serviceAttributeExacts("name", name);
    }

    private static Specification<ServiceEntity> descriptionExacts(String description) {
        return serviceAttributeExacts("description", description);
    }


    //Boolean
    private static Specification<ServiceEntity> statusBoolean(String status) {
        return serviceAttributeBoolean("status", status);
    }

    //BigInteger
    private static Specification<ServiceEntity> priceInteger(String price) {
        return serviceAttributeBoolean("price", price);
    }

    private static Specification<ServiceEntity> slotInteger(String slot) {
        return serviceAttributeBoolean("slot", slot);
    }

    private static Specification<ServiceEntity> serviceAttributeContains(String attribute, String value) {
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

    private static Specification<ServiceEntity> serviceAttributeExacts(String attribute, String value) {
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
    private static Specification<ServiceEntity> serviceAttributeBoolean(String attribute, String value) {
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

    private static Specification<ServiceEntity> serviceAttributeBigInteger(String attribute, BigInteger value) {
        return (root, query, cb) -> {
            if(value == null) {
                return null;
            }

            return cb.equal(
                    root.get(attribute),
                    String.valueOf(value)
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
