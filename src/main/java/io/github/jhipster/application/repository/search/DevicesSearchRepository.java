package io.github.jhipster.application.repository.search;

import io.github.jhipster.application.domain.Devices;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Devices entity.
 */
public interface DevicesSearchRepository extends ElasticsearchRepository<Devices, Long> {
}
