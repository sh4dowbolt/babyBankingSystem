package com.suraev.babyBankingSystem.entity.elasticModel;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;   
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Document;
import io.swagger.v3.oas.annotations.media.Schema;
@Getter
@Setter
@AllArgsConstructor
@Document(indexName = "phones")
@Builder
@Schema(description = "Phone Elastic")
public class PhoneElastic {
    @Id
    @Schema(description = "ID", example = "1")
    private Long id;
    @Field(type = FieldType.Keyword)
    @Schema(description = "Phone", example = "79999999999")
    private String phone;
    @Field(type = FieldType.Keyword)
    @Schema(description = "User ID", example = "1")
    private Long userId;
}