package io.github.jhipster.application.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Transactions entity.
 */
public class TransactionsDTO implements Serializable {

    private Long id;

    private String trxId;

    @NotNull
    private Integer trxType;

    private Long deviceId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTrxId() {
        return trxId;
    }

    public void setTrxId(String trxId) {
        this.trxId = trxId;
    }

    public Integer getTrxType() {
        return trxType;
    }

    public void setTrxType(Integer trxType) {
        this.trxType = trxType;
    }

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long devicesId) {
        this.deviceId = devicesId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TransactionsDTO transactionsDTO = (TransactionsDTO) o;
        if (transactionsDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), transactionsDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TransactionsDTO{" +
            "id=" + getId() +
            ", trxId='" + getTrxId() + "'" +
            ", trxType=" + getTrxType() +
            ", device=" + getDeviceId() +
            "}";
    }
}
