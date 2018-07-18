package io.github.jhipster.application.repository.search;

import io.github.jhipster.application.domain.Transactions;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Transactions entity.
 */
public interface TransactionsSearchRepository extends ElasticsearchRepository<Transactions, Long> {
}
