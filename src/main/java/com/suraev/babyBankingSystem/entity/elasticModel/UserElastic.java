package com.suraev.babyBankingSystem.entity.elasticModel;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.util.List;


@Document(indexName = "users")
@Getter
@Setter
@AllArgsConstructor
@Builder
public class UserElastic {
    @Id
    private Long id;
    @Field(type = FieldType.Text)
    private String name;
    @Field(type = FieldType.Date)
    private LocalDate dateOfBirth;
    @Field(type = FieldType.Nested)
    private List<PhoneElastic> phones;
    @Field(type = FieldType.Nested)
    private List<EmailElastic> emails;
    
}
