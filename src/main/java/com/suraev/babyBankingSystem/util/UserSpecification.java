package com.suraev.babyBankingSystem.util;

import org.springframework.data.jpa.domain.Specification;
import com.suraev.babyBankingSystem.entity.User;
import lombok.experimental.UtilityClass;
import java.time.LocalDate;

@Deprecated(forRemoval = true)
@UtilityClass
//TODO: remove this class after elasticsearch is implemented
public class UserSpecification {

    public static Specification<User> hasName(String name){
        return (root, query, criteriaBuilder) ->
        name == null ? criteriaBuilder.conjunction() : criteriaBuilder.like(root.get("name"), "%" + name + "%");
    }

    public static Specification<User> hasPhoneNumber(String phoneNumber){
        return (root, query, criteriaBuilder) ->
        phoneNumber == null ? criteriaBuilder.conjunction() : criteriaBuilder.equal(root.join("phones").get("number"),phoneNumber);
    }
    public static Specification<User> hasEmail(String email){
        return (root, query, criteriaBuilder) ->
        email == null ? criteriaBuilder.conjunction() : criteriaBuilder.equal(root.join("emails").get("email"), email);
    }
    public static Specification<User> hasDateOfBirth(LocalDate dateOfBirth){
        return (root, query, criteriaBuilder) ->
        dateOfBirth == null ? criteriaBuilder.conjunction() : criteriaBuilder.greaterThan(root.get("dateOfBirth"), dateOfBirth);
    }

}