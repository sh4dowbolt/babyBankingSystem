package com.suraev.babyBankingSystem.service;

import org.springframework.stereotype.Service;
import com.suraev.babyBankingSystem.entity.User;
import com.suraev.babyBankingSystem.entity.elasticModel.UserElastic;
import com.suraev.babyBankingSystem.repository.UserRepository;
import com.suraev.babyBankingSystem.util.UserElasticSpecification;
import lombok.RequiredArgsConstructor;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.suraev.babyBankingSystem.dto.UserDTO;
import java.time.LocalDate;
import java.util.stream.Collectors;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import java.util.List;
import org.springframework.data.domain.PageImpl;
import com.suraev.babyBankingSystem.entity.elasticModel.PhoneElastic;
import com.suraev.babyBankingSystem.entity.elasticModel.EmailElastic;
import lombok.extern.slf4j.Slf4j;
import com.suraev.babyBankingSystem.dto.PhoneDTO;
import com.suraev.babyBankingSystem.dto.EmailDTO;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ElasticsearchOperations elasticsearchOperations;

    @Override
    @Cacheable(value = "users", key = "#id")
    public Optional<User> getUser(Long id) {    
        return userRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true) 
    public Page<UserDTO> searchForUsers(
                                        String name, 
                                        String phoneNumber,
                                        String email,
                                        LocalDate dateOfBirth,
                                        Pageable pageable
                                        ) {

        NativeQuery query = UserElasticSpecification.buildUserQuery(name, phoneNumber, email, dateOfBirth, pageable);

        List<UserElastic> users = elasticsearchOperations
                                .search(query,UserElastic.class)
                                .map(result -> result.getContent())
                                .stream()
                                .collect(Collectors.toList());
        
        List<UserDTO> userDTOs = users.stream()
                                .map(this::userToUserDtos)
                                .collect(Collectors.toList());

        log.info("searching for users: " + userDTOs);

        return new PageImpl<>(userDTOs, pageable, elasticsearchOperations.count(query, UserElastic.class));
    }

    private UserDTO userToUserDtos(UserElastic userElastic) {
        return new UserDTO(
            userElastic.getId(),
            userElastic.getName(),
            userElastic.getDateOfBirth(),
            userElastic.getPhones().stream().map(this::phoneToPhoneDTO).collect(Collectors.toList()),
            userElastic.getEmails().stream().map(this::emailToEmailDTO).collect(Collectors.toList())
        );
    }
    private PhoneDTO phoneToPhoneDTO(PhoneElastic phoneElastic) {
        return new PhoneDTO(phoneElastic.getId(), phoneElastic.getPhone(), phoneElastic.getUserId());
    }
    private EmailDTO emailToEmailDTO(EmailElastic emailElastic) {
        return new EmailDTO(emailElastic.getId(), emailElastic.getEmail(), emailElastic.getUserId());
    }
    
}