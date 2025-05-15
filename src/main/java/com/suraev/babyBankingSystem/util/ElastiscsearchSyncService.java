package com.suraev.babyBankingSystem.util;

import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.stereotype.Service;
import com.suraev.babyBankingSystem.entity.User;
import com.suraev.babyBankingSystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.suraev.babyBankingSystem.exception.UserNotFoundException;
import com.suraev.babyBankingSystem.entity.elasticModel.UserElastic;
import com.suraev.babyBankingSystem.entity.elasticModel.PhoneElastic;
import com.suraev.babyBankingSystem.entity.elasticModel.EmailElastic;
import com.suraev.babyBankingSystem.entity.Phone;
import com.suraev.babyBankingSystem.entity.Email;
import java.util.stream.Collectors;


@Service    
@RequiredArgsConstructor
@Slf4j
public class ElastiscsearchSyncService {

    private final ElasticsearchOperations elasticsearchOperation;
    private final UserRepository userRepository;


    public void syncUser(Long userId) {
        try {
            User user = userRepository.findById(userId)
            .orElseThrow(() -> new UserNotFoundException("User not found"));

            UserElastic userElastic = convertToUserElastic(user);
            elasticsearchOperation.save(userElastic);
            log.info("user with id : {} synced to elasticsearch", userId);
        } catch (Exception e) {
            log.error("error syncing user with id : {}", userId, e.getMessage());
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



    private PhoneElastic convertToPhoneElastic(Phone phone) {
        return PhoneElastic.builder()
        .id(phone.getId())
        .phone(phone.getNumber())
        .userId(phone.getUser().getId())
        .build();
    }

    private EmailElastic convertToEmailElastic(Email email) {
        return EmailElastic.builder()
        .id(email.getId())
        .email(email.getEmail())
        .userId(email.getUser().getId())
        .build();
    }   

}
