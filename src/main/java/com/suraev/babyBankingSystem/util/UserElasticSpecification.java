package com.suraev.babyBankingSystem.util;

import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import org.springframework.data.domain.Pageable;
import java.time.LocalDate;
import co.elastic.clients.json.JsonData;
import lombok.extern.slf4j.Slf4j;

@Slf4j
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
                .nested(n->n
                    .path("phones")
                    .query( q2->q2
                        .term(t->t
                            .field("phones.phone")
                            .value(phoneNumber)
                        )
                    )
                )
            ));
        }

        if(email != null && !email.isEmpty()) {
            boolQueryBuilder.must(Query.of(q->q
                .nested(n->n
                    .path("emails")
                    .query(q2->q2
                        .term(t->t
                            .field("emails.email")
                            .value(email)
                        )
                    )
                )
            ));
        }

        if (dateOfBirth != null) {
            boolQueryBuilder.must(Query.of(q -> q
                .range(r -> r
                    .field("dateOfBirth")
                    .gte(JsonData.of(dateOfBirth.toString()))
                )
            ));
        }
        Query query = boolQueryBuilder.build()._toQuery();

        log.info("debug message for boolQueryBuilder, query created: " + query);

        return NativeQuery.builder()
                        .withQuery(query)
                        .withPageable(pageable)
                        .build();

    }
}