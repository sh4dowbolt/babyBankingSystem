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
import io.swagger.v3.oas.annotations.media.Schema;  

@Document(indexName = "users")
@Getter
@Setter
@AllArgsConstructor
@Builder
@Schema(description = "User Elastic")
public class UserElastic {
    @Id
    @Schema(description = "ID", example = "1")
    private Long id;
    @Field(type = FieldType.Text)
    @Schema(description = "Name", example = "John Doe")
    private String name;
    @Field(type = FieldType.Date)
    @Schema(description = "Date of birth", example = "12.12.2020")
    private LocalDate dateOfBirth;
    @Field(type = FieldType.Nested)
    @Schema(description = "Phones")
    private List<PhoneElastic> phones;
    @Field(type = FieldType.Nested)
    @Schema(description = "Emails")
    private List<EmailElastic> emails;
    
}
