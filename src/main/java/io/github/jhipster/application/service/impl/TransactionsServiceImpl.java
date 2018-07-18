package io.github.jhipster.application.service.impl;

import io.github.jhipster.application.service.TransactionsService;
import io.github.jhipster.application.domain.Transactions;
import io.github.jhipster.application.repository.TransactionsRepository;
import io.github.jhipster.application.repository.search.TransactionsSearchRepository;
import io.github.jhipster.application.service.dto.TransactionsDTO;
import io.github.jhipster.application.service.mapper.TransactionsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Transactions.
 */
@Service
@Transactional
public class TransactionsServiceImpl implements TransactionsService {

    private final Logger log = LoggerFactory.getLogger(TransactionsServiceImpl.class);

    private final TransactionsRepository transactionsRepository;

    private final TransactionsMapper transactionsMapper;

    private final TransactionsSearchRepository transactionsSearchRepository;

    public TransactionsServiceImpl(TransactionsRepository transactionsRepository, TransactionsMapper transactionsMapper, TransactionsSearchRepository transactionsSearchRepository) {
        this.transactionsRepository = transactionsRepository;
        this.transactionsMapper = transactionsMapper;
        this.transactionsSearchRepository = transactionsSearchRepository;
    }

    /**
     * Save a transactions.
     *
     * @param transactionsDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public TransactionsDTO save(TransactionsDTO transactionsDTO) {
        log.debug("Request to save Transactions : {}", transactionsDTO);
        Transactions transactions = transactionsMapper.toEntity(transactionsDTO);
        transactions = transactionsRepository.save(transactions);
        TransactionsDTO result = transactionsMapper.toDto(transactions);
        transactionsSearchRepository.save(transactions);
        return result;
    }

    /**
     * Get all the transactions.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<TransactionsDTO> findAll() {
        log.debug("Request to get all Transactions");
        return transactionsRepository.findAll().stream()
            .map(transactionsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one transactions by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TransactionsDTO> findOne(Long id) {
        log.debug("Request to get Transactions : {}", id);
        return transactionsRepository.findById(id)
            .map(transactionsMapper::toDto);
    }

    /**
     * Delete the transactions by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Transactions : {}", id);
        transactionsRepository.deleteById(id);
        transactionsSearchRepository.deleteById(id);
    }

    /**
     * Search for the transactions corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<TransactionsDTO> search(String query) {
        log.debug("Request to search Transactions for query {}", query);
        return StreamSupport
            .stream(transactionsSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(transactionsMapper::toDto)
            .collect(Collectors.toList());
    }
}
