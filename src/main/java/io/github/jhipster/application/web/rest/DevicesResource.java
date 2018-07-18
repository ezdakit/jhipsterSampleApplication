package io.github.jhipster.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.application.service.DevicesService;
import io.github.jhipster.application.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.application.web.rest.util.HeaderUtil;
import io.github.jhipster.application.service.dto.DevicesDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Devices.
 */
@RestController
@RequestMapping("/api")
public class DevicesResource {

    private final Logger log = LoggerFactory.getLogger(DevicesResource.class);

    private static final String ENTITY_NAME = "devices";

    private final DevicesService devicesService;

    public DevicesResource(DevicesService devicesService) {
        this.devicesService = devicesService;
    }

    /**
     * POST  /devices : Create a new devices.
     *
     * @param devicesDTO the devicesDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new devicesDTO, or with status 400 (Bad Request) if the devices has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/devices")
    @Timed
    public ResponseEntity<DevicesDTO> createDevices(@RequestBody DevicesDTO devicesDTO) throws URISyntaxException {
        log.debug("REST request to save Devices : {}", devicesDTO);
        if (devicesDTO.getId() != null) {
            throw new BadRequestAlertException("A new devices cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DevicesDTO result = devicesService.save(devicesDTO);
        return ResponseEntity.created(new URI("/api/devices/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /devices : Updates an existing devices.
     *
     * @param devicesDTO the devicesDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated devicesDTO,
     * or with status 400 (Bad Request) if the devicesDTO is not valid,
     * or with status 500 (Internal Server Error) if the devicesDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/devices")
    @Timed
    public ResponseEntity<DevicesDTO> updateDevices(@RequestBody DevicesDTO devicesDTO) throws URISyntaxException {
        log.debug("REST request to update Devices : {}", devicesDTO);
        if (devicesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DevicesDTO result = devicesService.save(devicesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, devicesDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /devices : get all the devices.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of devices in body
     */
    @GetMapping("/devices")
    @Timed
    public List<DevicesDTO> getAllDevices() {
        log.debug("REST request to get all Devices");
        return devicesService.findAll();
    }

    /**
     * GET  /devices/:id : get the "id" devices.
     *
     * @param id the id of the devicesDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the devicesDTO, or with status 404 (Not Found)
     */
    @GetMapping("/devices/{id}")
    @Timed
    public ResponseEntity<DevicesDTO> getDevices(@PathVariable Long id) {
        log.debug("REST request to get Devices : {}", id);
        Optional<DevicesDTO> devicesDTO = devicesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(devicesDTO);
    }

    /**
     * DELETE  /devices/:id : delete the "id" devices.
     *
     * @param id the id of the devicesDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/devices/{id}")
    @Timed
    public ResponseEntity<Void> deleteDevices(@PathVariable Long id) {
        log.debug("REST request to delete Devices : {}", id);
        devicesService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/devices?query=:query : search for the devices corresponding
     * to the query.
     *
     * @param query the query of the devices search
     * @return the result of the search
     */
    @GetMapping("/_search/devices")
    @Timed
    public List<DevicesDTO> searchDevices(@RequestParam String query) {
        log.debug("REST request to search Devices for query {}", query);
        return devicesService.search(query);
    }

}
