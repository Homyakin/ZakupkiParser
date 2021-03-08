package ru.homyakin.zakupki.database;

import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class PositionToContractRepository {
    private static final Logger logger = LoggerFactory.getLogger(ContractPositionRepository.class);
    private final JdbcTemplate jdbcTemplate;

    public PositionToContractRepository(
        DataSource dataSource
    ) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void insert(String contractGuid, String positionGuid) {
        var sql = "INSERT INTO zakupki.position_to_contract (contract_guid, position_guid) VALUES (?, ?)";
        try {
            jdbcTemplate.update(
                sql,
                contractGuid,
                positionGuid
            );
        } catch (Exception e) {
            logger.error("Error during insert in position_to_contract", e);
        }
    }
}
