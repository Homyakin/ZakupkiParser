package ru.homyakin.zakupki.database.purchase_protocol;

import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.homyakin.zakupki.models._223fz.purchase.ProtocolApplicationType;
import ru.homyakin.zakupki.utils.RepositoryUtils;

@Component
public class ApplicationConsiderationResultRepository {
    private final static Logger logger = LoggerFactory.getLogger(ApplicationConsiderationResultRepository.class);
    private final static String INSERT =
        """
        insert into application_consideration_result (application_guid, protocol_lot_applications_id, accepted,
         application_rejection_reason_code, rejection_reason) values (?, ?, ?, ?, ?);
        """;
    private final JdbcTemplate jdbcTemplate;

    public ApplicationConsiderationResultRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void insert(
        ProtocolApplicationType.ConsiderationResultInfo considerationResult,
        String applicationGuid,
        int protocolLotApplicationsId
    ) {
        if (considerationResult == null) return;
        try {
            jdbcTemplate.update(
                INSERT,
                applicationGuid,
                protocolLotApplicationsId,
                RepositoryUtils.mapAcceptedToString(considerationResult.getAccepted()),
                considerationResult.getRejectionReasonCode() != null ? considerationResult.getRejectionReasonCode().value() : null,
                considerationResult.getRejectionReason()
            );
        } catch (Exception e) {
            logger.error("Error during inserting into application_consideration_result", e);
        }
    }
}
