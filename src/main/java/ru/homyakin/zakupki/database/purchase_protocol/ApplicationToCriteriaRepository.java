package ru.homyakin.zakupki.database.purchase_protocol;

import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class ApplicationToCriteriaRepository {
    private final static Logger logger = LoggerFactory.getLogger(ApplicationToCriteriaRepository.class);
    private final static String INSERT =
        """
        insert into application_to_criteria (application_guid, application_protocol_lot_applications_id, protocol_lot_criteria_guid) values (?, ?, ?);
        """;
    private final JdbcTemplate jdbcTemplate;

    public ApplicationToCriteriaRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void insert(String applicationGuid, int protocolLotApplicationsId, String criteriaGuid) {
        try {
            jdbcTemplate.update(
                INSERT,
                applicationGuid,
                protocolLotApplicationsId,
                criteriaGuid
            );
        } catch (Exception e) {
            logger.error("Error during inserting into protocol_lot_applications_to_criteria", e);
        }
    }
}
