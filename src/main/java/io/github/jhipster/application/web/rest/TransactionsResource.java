package io.github.jhipster.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.application.service.TransactionsService;
import io.github.jhipster.application.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.application.web.rest.util.HeaderUtil;
import io.github.jhipster.application.service.dto.TransactionsDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Transactions.
 */
@RestController
@RequestMapping("/api")
public class TransactionsResource {

    private final Logger log = LoggerFactory.getLogger(TransactionsResource.class);

    private static final String ENTITY_NAME = "transactions";

    private final TransactionsService transactionsService;

    public TransactionsResource(TransactionsService transactionsService) {
        this.transactionsService = transactionsService;
    }

    /**
     * POST  /transactions : Create a new transactions.
     *
     * @param transactionsDTO the transactionsDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new transactionsDTO, or with status 400 (Bad Request) if the transactions has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/transactions")
    @Timed
    public ResponseEntity<TransactionsDTO> createTransactions(@Valid @RequestBody TransactionsDTO transactionsDTO) throws URISyntaxException {
        log.debug("REST request to save Transactions : {}", transactionsDTO);
        if (transactionsDTO.getId() != null) {
            throw new BadRequestAlertException("A new transactions cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TransactionsDTO result = transactionsService.save(transactionsDTO);
        return ResponseEntity.created(new URI("/api/transactions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /transactions : Updates an existing transactions.
     *
     * @param transactionsDTO the transactionsDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated transactionsDTO,
     * or with status 400 (Bad Request) if the transactionsDTO is not valid,
     * or with status 500 (Internal Server Error) if the transactionsDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/transactions")
    @Timed
    public ResponseEntity<TransactionsDTO> updateTransactions(@Valid @RequestBody TransactionsDTO transactionsDTO) throws URISyntaxException {
        log.debug("REST request to update Transactions : {}", transactionsDTO);
        if (transactionsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TransactionsDTO result = transactionsService.save(transactionsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, transactionsDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /transactions : get all the transactions.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of transactions in body
     */
    @GetMapping("/transactions")
    @Timed
    public List<TransactionsDTO> getAllTransactions() {
        log.debug("REST request to get all Transactions");
        return transactionsService.findAll();
    }

    /**
     * GET  /transactions/:id : get the "id" transactions.
     *
     * @param id the id of the transactionsDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the transactionsDTO, or with status 404 (Not Found)
     */
    @GetMapping("/transactions/{id}")
    @Timed
    public ResponseEntity<TransactionsDTO> getTransactions(@PathVariable Long id) {
        log.debug("REST request to get Transactions : {}", id);
        Optional<TransactionsDTO> transactionsDTO = transactionsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(transactionsDTO);
    }

    /**
     * DELETE  /transactions/:id : delete the "id" transactions.
     *
     * @param id the id of the transactionsDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/transactions/{id}")
    @Timed
    public ResponseEntity<Void> deleteTransactions(@PathVariable Long id) {
        log.debug("REST request to delete Transactions : {}", id);
        transactionsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/transactions?query=:query : search for the transactions corresponding
     * to the query.
     *
     * @param query the query of the transactions search
     * @return the result of the search
     */
    @GetMapping("/_search/transactions")
    @Timed
    public List<TransactionsDTO> searchTransactions(@RequestParam String query) {
        log.debug("REST request to search Transactions for query {}", query);
        return transactionsService.search(query);
    }

}
