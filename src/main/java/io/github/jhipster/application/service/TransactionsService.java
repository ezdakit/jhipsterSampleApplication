package io.github.jhipster.application.service;

import io.github.jhipster.application.service.dto.TransactionsDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Transactions.
 */
public interface TransactionsService {

    /**
     * Save a transactions.
     *
     * @param transactionsDTO the entity to save
     * @return the persisted entity
     */
    TransactionsDTO save(TransactionsDTO transactionsDTO);

    /**
     * Get all the transactions.
     *
     * @return the list of entities
     */
    List<TransactionsDTO> findAll();


    /**
     * Get the "id" transactions.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<TransactionsDTO> findOne(Long id);

    /**
     * Delete the "id" transactions.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the transactions corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @return the list of entities
     */
    List<TransactionsDTO> search(String query);
}
