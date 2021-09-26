package ru.homyakin.zakupki.database.purchase_protocol;

import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class ProtocolLotApplicationsToCriteriaRepository {
    private final static Logger logger = LoggerFactory.getLogger(ProtocolLotApplicationsToCriteriaRepository.class);
    private final static String INSERT =
        """
        insert into protocol_lot_applications_to_criteria (protocol_lot_applications_id, protocol_lot_criteria_guid) values (?, ?);
        """;
    private final JdbcTemplate jdbcTemplate;

    public ProtocolLotApplicationsToCriteriaRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void insert(int id, String criteriaGuid) {
        try {
            jdbcTemplate.update(
                INSERT,
                id,
                criteriaGuid
            );
        } catch (Exception e) {
            logger.error("Error during inserting into protocol_lot_applications_to_criteria", e);
        }
    }
}
