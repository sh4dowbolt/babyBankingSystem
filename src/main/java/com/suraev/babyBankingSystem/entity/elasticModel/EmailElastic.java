package com.suraev.babyBankingSystem.entity.elasticModel;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import io.swagger.v3.oas.annotations.media.Schema;
@Getter
@Setter
@AllArgsConstructor
@Document(indexName = "emails")
@Builder
@Schema(description = "Email Elastic")
public class EmailElastic {
    @Id
    @Schema(description = "ID", example = "1")
    private Long id;
    @Field(type = FieldType.Keyword)
    @Schema(description = "Email", example = "test@test.com")
    private String email;
    @Field(type = FieldType.Keyword)
    @Schema(description = "User ID", example = "1")
    private Long userId;
}
