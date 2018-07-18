package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.JhipsterSampleApplicationApp;

import io.github.jhipster.application.domain.Transactions;
import io.github.jhipster.application.repository.TransactionsRepository;
import io.github.jhipster.application.repository.search.TransactionsSearchRepository;
import io.github.jhipster.application.service.TransactionsService;
import io.github.jhipster.application.service.dto.TransactionsDTO;
import io.github.jhipster.application.service.mapper.TransactionsMapper;
import io.github.jhipster.application.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;


import static io.github.jhipster.application.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the TransactionsResource REST controller.
 *
 * @see TransactionsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
public class TransactionsResourceIntTest {

    private static final String DEFAULT_TRX_ID = "AAAAAAAAAA";
    private static final String UPDATED_TRX_ID = "BBBBBBBBBB";

    private static final Integer DEFAULT_TRX_TYPE = 1;
    private static final Integer UPDATED_TRX_TYPE = 2;

    @Autowired
    private TransactionsRepository transactionsRepository;


    @Autowired
    private TransactionsMapper transactionsMapper;
    

    @Autowired
    private TransactionsService transactionsService;

    /**
     * This repository is mocked in the io.github.jhipster.application.repository.search test package.
     *
     * @see io.github.jhipster.application.repository.search.TransactionsSearchRepositoryMockConfiguration
     */
    @Autowired
    private TransactionsSearchRepository mockTransactionsSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTransactionsMockMvc;

    private Transactions transactions;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TransactionsResource transactionsResource = new TransactionsResource(transactionsService);
        this.restTransactionsMockMvc = MockMvcBuilders.standaloneSetup(transactionsResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Transactions createEntity(EntityManager em) {
        Transactions transactions = new Transactions()
            .trxId(DEFAULT_TRX_ID)
            .trxType(DEFAULT_TRX_TYPE);
        return transactions;
    }

    @Before
    public void initTest() {
        transactions = createEntity(em);
    }

    @Test
    @Transactional
    public void createTransactions() throws Exception {
        int databaseSizeBeforeCreate = transactionsRepository.findAll().size();

        // Create the Transactions
        TransactionsDTO transactionsDTO = transactionsMapper.toDto(transactions);
        restTransactionsMockMvc.perform(post("/api/transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transactionsDTO)))
            .andExpect(status().isCreated());

        // Validate the Transactions in the database
        List<Transactions> transactionsList = transactionsRepository.findAll();
        assertThat(transactionsList).hasSize(databaseSizeBeforeCreate + 1);
        Transactions testTransactions = transactionsList.get(transactionsList.size() - 1);
        assertThat(testTransactions.getTrxId()).isEqualTo(DEFAULT_TRX_ID);
        assertThat(testTransactions.getTrxType()).isEqualTo(DEFAULT_TRX_TYPE);

        // Validate the Transactions in Elasticsearch
        verify(mockTransactionsSearchRepository, times(1)).save(testTransactions);
    }

    @Test
    @Transactional
    public void createTransactionsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = transactionsRepository.findAll().size();

        // Create the Transactions with an existing ID
        transactions.setId(1L);
        TransactionsDTO transactionsDTO = transactionsMapper.toDto(transactions);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTransactionsMockMvc.perform(post("/api/transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transactionsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Transactions in the database
        List<Transactions> transactionsList = transactionsRepository.findAll();
        assertThat(transactionsList).hasSize(databaseSizeBeforeCreate);

        // Validate the Transactions in Elasticsearch
        verify(mockTransactionsSearchRepository, times(0)).save(transactions);
    }

    @Test
    @Transactional
    public void checkTrxTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = transactionsRepository.findAll().size();
        // set the field null
        transactions.setTrxType(null);

        // Create the Transactions, which fails.
        TransactionsDTO transactionsDTO = transactionsMapper.toDto(transactions);

        restTransactionsMockMvc.perform(post("/api/transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transactionsDTO)))
            .andExpect(status().isBadRequest());

        List<Transactions> transactionsList = transactionsRepository.findAll();
        assertThat(transactionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTransactions() throws Exception {
        // Initialize the database
        transactionsRepository.saveAndFlush(transactions);

        // Get all the transactionsList
        restTransactionsMockMvc.perform(get("/api/transactions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(transactions.getId().intValue())))
            .andExpect(jsonPath("$.[*].trxId").value(hasItem(DEFAULT_TRX_ID.toString())))
            .andExpect(jsonPath("$.[*].trxType").value(hasItem(DEFAULT_TRX_TYPE)));
    }
    

    @Test
    @Transactional
    public void getTransactions() throws Exception {
        // Initialize the database
        transactionsRepository.saveAndFlush(transactions);

        // Get the transactions
        restTransactionsMockMvc.perform(get("/api/transactions/{id}", transactions.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(transactions.getId().intValue()))
            .andExpect(jsonPath("$.trxId").value(DEFAULT_TRX_ID.toString()))
            .andExpect(jsonPath("$.trxType").value(DEFAULT_TRX_TYPE));
    }
    @Test
    @Transactional
    public void getNonExistingTransactions() throws Exception {
        // Get the transactions
        restTransactionsMockMvc.perform(get("/api/transactions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTransactions() throws Exception {
        // Initialize the database
        transactionsRepository.saveAndFlush(transactions);

        int databaseSizeBeforeUpdate = transactionsRepository.findAll().size();

        // Update the transactions
        Transactions updatedTransactions = transactionsRepository.findById(transactions.getId()).get();
        // Disconnect from session so that the updates on updatedTransactions are not directly saved in db
        em.detach(updatedTransactions);
        updatedTransactions
            .trxId(UPDATED_TRX_ID)
            .trxType(UPDATED_TRX_TYPE);
        TransactionsDTO transactionsDTO = transactionsMapper.toDto(updatedTransactions);

        restTransactionsMockMvc.perform(put("/api/transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transactionsDTO)))
            .andExpect(status().isOk());

        // Validate the Transactions in the database
        List<Transactions> transactionsList = transactionsRepository.findAll();
        assertThat(transactionsList).hasSize(databaseSizeBeforeUpdate);
        Transactions testTransactions = transactionsList.get(transactionsList.size() - 1);
        assertThat(testTransactions.getTrxId()).isEqualTo(UPDATED_TRX_ID);
        assertThat(testTransactions.getTrxType()).isEqualTo(UPDATED_TRX_TYPE);

        // Validate the Transactions in Elasticsearch
        verify(mockTransactionsSearchRepository, times(1)).save(testTransactions);
    }

    @Test
    @Transactional
    public void updateNonExistingTransactions() throws Exception {
        int databaseSizeBeforeUpdate = transactionsRepository.findAll().size();

        // Create the Transactions
        TransactionsDTO transactionsDTO = transactionsMapper.toDto(transactions);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTransactionsMockMvc.perform(put("/api/transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transactionsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Transactions in the database
        List<Transactions> transactionsList = transactionsRepository.findAll();
        assertThat(transactionsList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Transactions in Elasticsearch
        verify(mockTransactionsSearchRepository, times(0)).save(transactions);
    }

    @Test
    @Transactional
    public void deleteTransactions() throws Exception {
        // Initialize the database
        transactionsRepository.saveAndFlush(transactions);

        int databaseSizeBeforeDelete = transactionsRepository.findAll().size();

        // Get the transactions
        restTransactionsMockMvc.perform(delete("/api/transactions/{id}", transactions.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Transactions> transactionsList = transactionsRepository.findAll();
        assertThat(transactionsList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Transactions in Elasticsearch
        verify(mockTransactionsSearchRepository, times(1)).deleteById(transactions.getId());
    }

    @Test
    @Transactional
    public void searchTransactions() throws Exception {
        // Initialize the database
        transactionsRepository.saveAndFlush(transactions);
        when(mockTransactionsSearchRepository.search(queryStringQuery("id:" + transactions.getId())))
            .thenReturn(Collections.singletonList(transactions));
        // Search the transactions
        restTransactionsMockMvc.perform(get("/api/_search/transactions?query=id:" + transactions.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(transactions.getId().intValue())))
            .andExpect(jsonPath("$.[*].trxId").value(hasItem(DEFAULT_TRX_ID.toString())))
            .andExpect(jsonPath("$.[*].trxType").value(hasItem(DEFAULT_TRX_TYPE)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Transactions.class);
        Transactions transactions1 = new Transactions();
        transactions1.setId(1L);
        Transactions transactions2 = new Transactions();
        transactions2.setId(transactions1.getId());
        assertThat(transactions1).isEqualTo(transactions2);
        transactions2.setId(2L);
        assertThat(transactions1).isNotEqualTo(transactions2);
        transactions1.setId(null);
        assertThat(transactions1).isNotEqualTo(transactions2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TransactionsDTO.class);
        TransactionsDTO transactionsDTO1 = new TransactionsDTO();
        transactionsDTO1.setId(1L);
        TransactionsDTO transactionsDTO2 = new TransactionsDTO();
        assertThat(transactionsDTO1).isNotEqualTo(transactionsDTO2);
        transactionsDTO2.setId(transactionsDTO1.getId());
        assertThat(transactionsDTO1).isEqualTo(transactionsDTO2);
        transactionsDTO2.setId(2L);
        assertThat(transactionsDTO1).isNotEqualTo(transactionsDTO2);
        transactionsDTO1.setId(null);
        assertThat(transactionsDTO1).isNotEqualTo(transactionsDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(transactionsMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(transactionsMapper.fromId(null)).isNull();
    }
}
