package com.suraev.babyBankingSystem.entity.elasticModel;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Document(indexName = "emails")
@Builder
public class EmailElastic {
    @Id
    private Long id;
    @Field(type = FieldType.Keyword)
    private String email;
    @Field(type = FieldType.Keyword)
    private Long userId;
}
