package io.github.jhipster.application.service.mapper;

import io.github.jhipster.application.domain.*;
import io.github.jhipster.application.service.dto.DevicesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Devices and its DTO DevicesDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DevicesMapper extends EntityMapper<DevicesDTO, Devices> {



    default Devices fromId(Long id) {
        if (id == null) {
            return null;
        }
        Devices devices = new Devices();
        devices.setId(id);
        return devices;
    }
}
