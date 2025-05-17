package com.suraev.babyBankingSystem.util;

import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import org.springframework.data.domain.Pageable;
import java.time.LocalDate;
import co.elastic.clients.json.JsonData;

public class UserElasticSpecification {

    public static NativeQuery buildUserQuery(
                                                String name,
                                                String phoneNumber,
                                                String email,
                                                LocalDate dateOfBirth,
                                                Pageable pageable
                                            ) {

        BoolQuery.Builder boolQueryBuilder = new BoolQuery.Builder();

        if(name != null && !name.isEmpty()) {
            boolQueryBuilder.must(Query.of(q->q
                .wildcard(w->w
                    .field("name")
                    .value(name+ "*")
                )
             ));
        }

        if(phoneNumber != null && !phoneNumber.isEmpty()) { 
            boolQueryBuilder.must(Query.of(q->q
                .term(t->t
                     .field("phones")
                .value(phoneNumber)
            )
        ));
        }

        if(email != null && !email.isEmpty()) {
            boolQueryBuilder.must(Query.of(q->q
                .term(t->t
                    .field("emails")
                    .value(email)
                )
            ));
        }

        if (dateOfBirth != null) {
            boolQueryBuilder.must(Query.of(q -> q
                .range(r -> r
                    .field("date_of_birth")
                    .gt(JsonData.of(dateOfBirth.toString()))
                )
            ));
        }

        return NativeQuery.builder()
        .withQuery(boolQueryBuilder.build()._toQuery())
        .withPageable(pageable)
        .build();

    }
}