package io.github.jhipster.application.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Transactions.
 */
@Entity
@Table(name = "transactions")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "transactions")
public class Transactions implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "trx_id")
    private String trxId;

    @NotNull
    @Column(name = "trx_type", nullable = false)
    private Integer trxType;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Devices device;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTrxId() {
        return trxId;
    }

    public Transactions trxId(String trxId) {
        this.trxId = trxId;
        return this;
    }

    public void setTrxId(String trxId) {
        this.trxId = trxId;
    }

    public Integer getTrxType() {
        return trxType;
    }

    public Transactions trxType(Integer trxType) {
        this.trxType = trxType;
        return this;
    }

    public void setTrxType(Integer trxType) {
        this.trxType = trxType;
    }

    public Devices getDevice() {
        return device;
    }

    public Transactions device(Devices devices) {
        this.device = devices;
        return this;
    }

    public void setDevice(Devices devices) {
        this.device = devices;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Transactions transactions = (Transactions) o;
        if (transactions.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), transactions.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Transactions{" +
            "id=" + getId() +
            ", trxId='" + getTrxId() + "'" +
            ", trxType=" + getTrxType() +
            "}";
    }
}
