package petservice.repository.specification;

import org.springframework.data.jpa.domain.Specification;
import petservice.model.Entity.PetEntity;
import petservice.model.Entity.PetEntity;

import java.util.Map;

import static org.springframework.data.jpa.domain.Specification.where;

public class PetSpecification {
    public static Specification<PetEntity> getFilterAllRows(String key) {
        System.out.println(key);
        return (root, query, cb) -> {
            query.distinct(true); //Important because of the join in the addressAttribute specifications
            return where(
                    where(nameContains(key))
                            .or(genderExacts(key))
                            .or(locationContains(key))
                            .or(breedContains(key))
                            .or(descriptionContains(key))
                            .or(categoryContains(key))
                            .or(vaccinatedBoolean(key))
                            .or(statusBoolean(key))
            ).toPredicate(root, query, cb);
        };
    }

    public static Specification<PetEntity> getFilter(Map<String,String> map) {
        System.out.println(map);
        return (root, query, cb) -> {
            query.distinct(true); //Important because of the join in the addressAttribute specifications
            return where(
                    where(nameContains(map.get("name")))
                            .and(genderExacts(map.get("gender")))
                            .and(locationContains(map.get("location")))
                            .and(breedContains(map.get("breed")))
                            .and(descriptionContains(map.get("description")))
                            .and(categoryContains(map.get("category")))
                            .and(vaccinatedBoolean(map.get("vaccinated")))
                            .and(statusBoolean(map.get("status")))
            ).toPredicate(root, query, cb);
        };
    }

    //contain compare
    private static Specification<PetEntity> nameContains(String firstName) {
        return petAttributeContains("name", firstName);
    }

    private static Specification<PetEntity> genderContains(String email) {
        return petAttributeContains("gender", email);
    }

    private static Specification<PetEntity> locationContains(String email) {
        return petAttributeContains("location", email);
    }

    private static Specification<PetEntity> ageContains(String petname) {
        return petAttributeContains("age", petname);
    }

    private static Specification<PetEntity> sizeContains(String petname) {
        return petAttributeContains("size", petname);
    }

    private static Specification<PetEntity> breedContains(String petname) {
        return petAttributeContains("breed", petname);
    }

    private static Specification<PetEntity> descriptionContains(String petname) {
        return petAttributeContains("description", petname);
    }

    private static Specification<PetEntity> categoryContains(String petname) {
        return petAttributeContains("category", petname);
    }


    //Exact compare
    private static Specification<PetEntity> nameExacts(String firstName) {
        return petAttributeExacts("name", firstName);
    }

    private static Specification<PetEntity> genderExacts(String email) {
        return petAttributeExacts("gender", email);
    }

    private static Specification<PetEntity> locationExacts(String email) {
        return petAttributeExacts("location", email);
    }

    private static Specification<PetEntity> ageExacts(String petname) {
        return petAttributeExacts("age", petname);
    }

    private static Specification<PetEntity> sizeExacts(String petname) {
        return petAttributeExacts("size", petname);
    }

    private static Specification<PetEntity> breedExacts(String petname) {
        return petAttributeExacts("breed", petname);
    }

    private static Specification<PetEntity> descriptionExacts(String petname) {
        return petAttributeExacts("description", petname);
    }

    private static Specification<PetEntity> categoryExacts(String petname) {
        return petAttributeExacts("category", petname);
    }


    //Boolean
    private static Specification<PetEntity> vaccinatedBoolean(String active) {
        return petAttributeBoolean("vaccinated", active);
    }

    private static Specification<PetEntity> statusBoolean(String status) {
        return petAttributeBoolean("status", status);
    }


    private static Specification<PetEntity> petAttributeContains(String attribute, String value) {
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

    private static Specification<PetEntity> petAttributeExacts(String attribute, String value) {
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
    private static Specification<PetEntity> petAttributeBoolean(String attribute, String value) {
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
    
    private static String containsLowerCase(String searchField) {
        return "%" + searchField.toLowerCase() + "%";
    }

    private static String exactLowerCase(String searchField) {
        return  searchField.toLowerCase();
    }
}
