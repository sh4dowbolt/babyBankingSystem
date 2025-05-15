package com.suraev.babyBankingSystem.entity.elasticModel;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;   
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Document;

@Getter
@Setter
@AllArgsConstructor
@Document(indexName = "phones")
public class PhoneElastic {
    @Id
    private Long id;
    @Field(type = FieldType.Keyword)
    private String phone;
    @Field(type = FieldType.Keyword)
    private Long userId;
}