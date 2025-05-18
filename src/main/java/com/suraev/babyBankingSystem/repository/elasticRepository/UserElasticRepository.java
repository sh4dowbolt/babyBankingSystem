package com.suraev.babyBankingSystem.repository.elasticRepository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import com.suraev.babyBankingSystem.entity.elasticModel.UserElastic;
import org.springframework.stereotype.Repository;

@Repository
public interface UserElasticRepository extends ElasticsearchRepository<UserElastic, Long> {

}
