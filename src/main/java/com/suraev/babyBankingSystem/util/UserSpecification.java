package com.suraev.babyBankingSystem.util;

import org.springframework.data.jpa.domain.Specification;
import com.suraev.babyBankingSystem.entity.User;
import lombok.experimental.UtilityClass;
import java.util.Date;


@UtilityClass
public class UserSpecification {


    public static Specification<User> hasName(String name){
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("name"), "%" + name + "%");
    }

    public static Specification<User> hasPhoneNumber(String phoneNumber){
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("phoneNumber"),phoneNumber);
    }
    public static Specification<User> hasEmail(String email){
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("email"), email);
    }
    public static Specification<User> hasDateOfBirth(Date dateOfBirth){
        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThan(root.get("dateOfBirth"), dateOfBirth);
    }

}