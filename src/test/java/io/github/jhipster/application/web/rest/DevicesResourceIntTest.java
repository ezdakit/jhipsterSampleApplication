package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.JhipsterSampleApplicationApp;

import io.github.jhipster.application.domain.Devices;
import io.github.jhipster.application.repository.DevicesRepository;
import io.github.jhipster.application.repository.search.DevicesSearchRepository;
import io.github.jhipster.application.service.DevicesService;
import io.github.jhipster.application.service.dto.DevicesDTO;
import io.github.jhipster.application.service.mapper.DevicesMapper;
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
 * Test class for the DevicesResource REST controller.
 *
 * @see DevicesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
public class DevicesResourceIntTest {

    private static final String DEFAULT_DEVICE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_DEVICE_NAME = "BBBBBBBBBB";

    @Autowired
    private DevicesRepository devicesRepository;


    @Autowired
    private DevicesMapper devicesMapper;
    

    @Autowired
    private DevicesService devicesService;

    /**
     * This repository is mocked in the io.github.jhipster.application.repository.search test package.
     *
     * @see io.github.jhipster.application.repository.search.DevicesSearchRepositoryMockConfiguration
     */
    @Autowired
    private DevicesSearchRepository mockDevicesSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDevicesMockMvc;

    private Devices devices;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DevicesResource devicesResource = new DevicesResource(devicesService);
        this.restDevicesMockMvc = MockMvcBuilders.standaloneSetup(devicesResource)
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
    public static Devices createEntity(EntityManager em) {
        Devices devices = new Devices()
            .deviceName(DEFAULT_DEVICE_NAME);
        return devices;
    }

    @Before
    public void initTest() {
        devices = createEntity(em);
    }

    @Test
    @Transactional
    public void createDevices() throws Exception {
        int databaseSizeBeforeCreate = devicesRepository.findAll().size();

        // Create the Devices
        DevicesDTO devicesDTO = devicesMapper.toDto(devices);
        restDevicesMockMvc.perform(post("/api/devices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(devicesDTO)))
            .andExpect(status().isCreated());

        // Validate the Devices in the database
        List<Devices> devicesList = devicesRepository.findAll();
        assertThat(devicesList).hasSize(databaseSizeBeforeCreate + 1);
        Devices testDevices = devicesList.get(devicesList.size() - 1);
        assertThat(testDevices.getDeviceName()).isEqualTo(DEFAULT_DEVICE_NAME);

        // Validate the Devices in Elasticsearch
        verify(mockDevicesSearchRepository, times(1)).save(testDevices);
    }

    @Test
    @Transactional
    public void createDevicesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = devicesRepository.findAll().size();

        // Create the Devices with an existing ID
        devices.setId(1L);
        DevicesDTO devicesDTO = devicesMapper.toDto(devices);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDevicesMockMvc.perform(post("/api/devices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(devicesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Devices in the database
        List<Devices> devicesList = devicesRepository.findAll();
        assertThat(devicesList).hasSize(databaseSizeBeforeCreate);

        // Validate the Devices in Elasticsearch
        verify(mockDevicesSearchRepository, times(0)).save(devices);
    }

    @Test
    @Transactional
    public void getAllDevices() throws Exception {
        // Initialize the database
        devicesRepository.saveAndFlush(devices);

        // Get all the devicesList
        restDevicesMockMvc.perform(get("/api/devices?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(devices.getId().intValue())))
            .andExpect(jsonPath("$.[*].deviceName").value(hasItem(DEFAULT_DEVICE_NAME.toString())));
    }
    

    @Test
    @Transactional
    public void getDevices() throws Exception {
        // Initialize the database
        devicesRepository.saveAndFlush(devices);

        // Get the devices
        restDevicesMockMvc.perform(get("/api/devices/{id}", devices.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(devices.getId().intValue()))
            .andExpect(jsonPath("$.deviceName").value(DEFAULT_DEVICE_NAME.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingDevices() throws Exception {
        // Get the devices
        restDevicesMockMvc.perform(get("/api/devices/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDevices() throws Exception {
        // Initialize the database
        devicesRepository.saveAndFlush(devices);

        int databaseSizeBeforeUpdate = devicesRepository.findAll().size();

        // Update the devices
        Devices updatedDevices = devicesRepository.findById(devices.getId()).get();
        // Disconnect from session so that the updates on updatedDevices are not directly saved in db
        em.detach(updatedDevices);
        updatedDevices
            .deviceName(UPDATED_DEVICE_NAME);
        DevicesDTO devicesDTO = devicesMapper.toDto(updatedDevices);

        restDevicesMockMvc.perform(put("/api/devices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(devicesDTO)))
            .andExpect(status().isOk());

        // Validate the Devices in the database
        List<Devices> devicesList = devicesRepository.findAll();
        assertThat(devicesList).hasSize(databaseSizeBeforeUpdate);
        Devices testDevices = devicesList.get(devicesList.size() - 1);
        assertThat(testDevices.getDeviceName()).isEqualTo(UPDATED_DEVICE_NAME);

        // Validate the Devices in Elasticsearch
        verify(mockDevicesSearchRepository, times(1)).save(testDevices);
    }

    @Test
    @Transactional
    public void updateNonExistingDevices() throws Exception {
        int databaseSizeBeforeUpdate = devicesRepository.findAll().size();

        // Create the Devices
        DevicesDTO devicesDTO = devicesMapper.toDto(devices);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDevicesMockMvc.perform(put("/api/devices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(devicesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Devices in the database
        List<Devices> devicesList = devicesRepository.findAll();
        assertThat(devicesList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Devices in Elasticsearch
        verify(mockDevicesSearchRepository, times(0)).save(devices);
    }

    @Test
    @Transactional
    public void deleteDevices() throws Exception {
        // Initialize the database
        devicesRepository.saveAndFlush(devices);

        int databaseSizeBeforeDelete = devicesRepository.findAll().size();

        // Get the devices
        restDevicesMockMvc.perform(delete("/api/devices/{id}", devices.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Devices> devicesList = devicesRepository.findAll();
        assertThat(devicesList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Devices in Elasticsearch
        verify(mockDevicesSearchRepository, times(1)).deleteById(devices.getId());
    }

    @Test
    @Transactional
    public void searchDevices() throws Exception {
        // Initialize the database
        devicesRepository.saveAndFlush(devices);
        when(mockDevicesSearchRepository.search(queryStringQuery("id:" + devices.getId())))
            .thenReturn(Collections.singletonList(devices));
        // Search the devices
        restDevicesMockMvc.perform(get("/api/_search/devices?query=id:" + devices.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(devices.getId().intValue())))
            .andExpect(jsonPath("$.[*].deviceName").value(hasItem(DEFAULT_DEVICE_NAME.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Devices.class);
        Devices devices1 = new Devices();
        devices1.setId(1L);
        Devices devices2 = new Devices();
        devices2.setId(devices1.getId());
        assertThat(devices1).isEqualTo(devices2);
        devices2.setId(2L);
        assertThat(devices1).isNotEqualTo(devices2);
        devices1.setId(null);
        assertThat(devices1).isNotEqualTo(devices2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DevicesDTO.class);
        DevicesDTO devicesDTO1 = new DevicesDTO();
        devicesDTO1.setId(1L);
        DevicesDTO devicesDTO2 = new DevicesDTO();
        assertThat(devicesDTO1).isNotEqualTo(devicesDTO2);
        devicesDTO2.setId(devicesDTO1.getId());
        assertThat(devicesDTO1).isEqualTo(devicesDTO2);
        devicesDTO2.setId(2L);
        assertThat(devicesDTO1).isNotEqualTo(devicesDTO2);
        devicesDTO1.setId(null);
        assertThat(devicesDTO1).isNotEqualTo(devicesDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(devicesMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(devicesMapper.fromId(null)).isNull();
    }
}
