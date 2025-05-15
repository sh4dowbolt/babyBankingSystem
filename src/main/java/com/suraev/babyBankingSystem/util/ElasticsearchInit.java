package com.suraev.babyBankingSystem.util;

import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

import lombok.RequiredArgsConstructor;
import lombok.experimental.UtilityClass;
import java.util.List;
import java.util.stream.Collectors;

import com.suraev.babyBankingSystem.entity.User;
import com.suraev.babyBankingSystem.entity.elasticModel.UserElastic;
import com.suraev.babyBankingSystem.repository.UserRepository;

import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import com.suraev.babyBankingSystem.entity.elasticModel.PhoneElastic;
import com.suraev.babyBankingSystem.entity.elasticModel.EmailElastic;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ElasticsearchInit {    

    private final UserRepository userRepository;
    private final ElasticsearchOperations elasticsearchOperations;

    @PostConstruct
    public void initElasticsearchData() {

        try {
        if (!isIndexExists(UserElastic.class)) {
            log.info("intializing elasticsearch index for users");
        
        List<User> users = userRepository.findAll();
        List<UserElastic> userElastic = users.stream()
        .map(this::convertToUserElastic)
        .collect(Collectors.toList());  

        elasticsearchOperations.save(userElastic);

        log.info("succesfully initialized elasticsearch index for users");
        } else {
            log.info("elasticsearch index for users already exists, skipping initialization");
        }
    } catch (Exception e) {
        log.error("error initializing elasticsearch index for users", e);
    }
    }

    private UserElastic convertToUserElastic(User user) {
       return UserElastic.builder()
       .id(user.getId())
       .name(user.getName())
       .phones(user.getPhones().stream().map(phone -> new PhoneElastic(phone.getId(), phone.getNumber(), user.getId())).collect(Collectors.toList()))
       .emails(user.getEmails().stream().map(email -> new EmailElastic(email.getId(), email.getEmail(), user.getId())).collect(Collectors.toList()))
       .dateOfBirth(user.getDateOfBirth())
       .build();
    }

    private boolean isIndexExists(Class<?> indexType) {
        return elasticsearchOperations.indexOps(indexType).exists();
    }
}

