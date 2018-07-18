package io.github.jhipster.application.service;

import io.github.jhipster.application.service.dto.DevicesDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Devices.
 */
public interface DevicesService {

    /**
     * Save a devices.
     *
     * @param devicesDTO the entity to save
     * @return the persisted entity
     */
    DevicesDTO save(DevicesDTO devicesDTO);

    /**
     * Get all the devices.
     *
     * @return the list of entities
     */
    List<DevicesDTO> findAll();


    /**
     * Get the "id" devices.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<DevicesDTO> findOne(Long id);

    /**
     * Delete the "id" devices.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the devices corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @return the list of entities
     */
    List<DevicesDTO> search(String query);
}
