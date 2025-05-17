package com.suraev.babyBankingSystem.util;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.stream.Collectors;
import com.suraev.babyBankingSystem.entity.User;
import com.suraev.babyBankingSystem.entity.elasticModel.UserElastic;
import com.suraev.babyBankingSystem.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import com.suraev.babyBankingSystem.entity.elasticModel.PhoneElastic;
import com.suraev.babyBankingSystem.entity.elasticModel.EmailElastic;
import lombok.extern.slf4j.Slf4j;
import com.suraev.babyBankingSystem.entity.Phone;
import com.suraev.babyBankingSystem.entity.Email;

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
        try {
       return UserElastic.builder()
                        .id(user.getId())
                        .name(user.getName())
                        .phones(user.getPhones().stream().map(this::convertToPhoneElastic).collect(Collectors.toList()))
                        .emails(user.getEmails().stream().map(this::convertToEmailElastic).collect(Collectors.toList()))
                        .dateOfBirth(user.getDateOfBirth())
                        .build();
    } catch (Exception e) {
        log.error("error converting user to elasticsearch : {}", user.getId(), e.getMessage());
        return null;
    }
    }

    private boolean isIndexExists(Class<?> indexType) {
        return elasticsearchOperations.indexOps(indexType).exists();
    }
    private PhoneElastic convertToPhoneElastic(Phone phone) {
        return new PhoneElastic(phone.getId(), phone.getNumber(), phone.getUser().getId());
    }
    private EmailElastic convertToEmailElastic(Email email) {
        return new EmailElastic(email.getId(), email.getEmail(), email.getUser().getId());
    }
}

