package io.github.jhipster.application.service.impl;

import io.github.jhipster.application.service.DevicesService;
import io.github.jhipster.application.domain.Devices;
import io.github.jhipster.application.repository.DevicesRepository;
import io.github.jhipster.application.repository.search.DevicesSearchRepository;
import io.github.jhipster.application.service.dto.DevicesDTO;
import io.github.jhipster.application.service.mapper.DevicesMapper;
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
 * Service Implementation for managing Devices.
 */
@Service
@Transactional
public class DevicesServiceImpl implements DevicesService {

    private final Logger log = LoggerFactory.getLogger(DevicesServiceImpl.class);

    private final DevicesRepository devicesRepository;

    private final DevicesMapper devicesMapper;

    private final DevicesSearchRepository devicesSearchRepository;

    public DevicesServiceImpl(DevicesRepository devicesRepository, DevicesMapper devicesMapper, DevicesSearchRepository devicesSearchRepository) {
        this.devicesRepository = devicesRepository;
        this.devicesMapper = devicesMapper;
        this.devicesSearchRepository = devicesSearchRepository;
    }

    /**
     * Save a devices.
     *
     * @param devicesDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public DevicesDTO save(DevicesDTO devicesDTO) {
        log.debug("Request to save Devices : {}", devicesDTO);
        Devices devices = devicesMapper.toEntity(devicesDTO);
        devices = devicesRepository.save(devices);
        DevicesDTO result = devicesMapper.toDto(devices);
        devicesSearchRepository.save(devices);
        return result;
    }

    /**
     * Get all the devices.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<DevicesDTO> findAll() {
        log.debug("Request to get all Devices");
        return devicesRepository.findAll().stream()
            .map(devicesMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one devices by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<DevicesDTO> findOne(Long id) {
        log.debug("Request to get Devices : {}", id);
        return devicesRepository.findById(id)
            .map(devicesMapper::toDto);
    }

    /**
     * Delete the devices by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Devices : {}", id);
        devicesRepository.deleteById(id);
        devicesSearchRepository.deleteById(id);
    }

    /**
     * Search for the devices corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<DevicesDTO> search(String query) {
        log.debug("Request to search Devices for query {}", query);
        return StreamSupport
            .stream(devicesSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(devicesMapper::toDto)
            .collect(Collectors.toList());
    }
}
