package io.github.jhipster.application.service.mapper;

import io.github.jhipster.application.domain.*;
import io.github.jhipster.application.service.dto.TransactionsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Transactions and its DTO TransactionsDTO.
 */
@Mapper(componentModel = "spring", uses = {DevicesMapper.class})
public interface TransactionsMapper extends EntityMapper<TransactionsDTO, Transactions> {

    @Mapping(source = "device.id", target = "deviceId")
    TransactionsDTO toDto(Transactions transactions);

    @Mapping(source = "deviceId", target = "device")
    Transactions toEntity(TransactionsDTO transactionsDTO);

    default Transactions fromId(Long id) {
        if (id == null) {
            return null;
        }
        Transactions transactions = new Transactions();
        transactions.setId(id);
        return transactions;
    }
}
