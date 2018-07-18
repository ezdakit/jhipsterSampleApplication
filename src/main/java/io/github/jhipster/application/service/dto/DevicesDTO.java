package io.github.jhipster.application.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Devices entity.
 */
public class DevicesDTO implements Serializable {

    private Long id;

    private String deviceName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DevicesDTO devicesDTO = (DevicesDTO) o;
        if (devicesDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), devicesDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DevicesDTO{" +
            "id=" + getId() +
            ", deviceName='" + getDeviceName() + "'" +
            "}";
    }
}
