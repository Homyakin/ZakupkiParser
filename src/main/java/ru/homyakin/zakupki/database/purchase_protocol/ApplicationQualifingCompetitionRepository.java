package ru.homyakin.zakupki.database.purchase_protocol;

import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.homyakin.zakupki.models._223fz.purchase.ProtocolApplicationType;
import ru.homyakin.zakupki.utils.RepositoryUtils;

@Component
public class ApplicationQualifingCompetitionRepository {
    private final static Logger logger = LoggerFactory.getLogger(ApplicationQualifingCompetitionRepository.class);
    private final static String INSERT =
        """
        insert into application_qualyfing_competition (application_guid, protocol_lot_applications_id, accepted,
         application_rejection_reason_code, rejection_reason) values (?, ?, ?, ?, ?);
        """;
    private final JdbcTemplate jdbcTemplate;

    public ApplicationQualifingCompetitionRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void insert(
        ProtocolApplicationType.QualyfingCompetitionInfo qualyfingCompetition,
        String applicationGuid,
        int protocolLotApplicationsId
    ) {
        if (qualyfingCompetition == null) return;
        try {
            jdbcTemplate.update(
                INSERT,
                applicationGuid,
                protocolLotApplicationsId,
                RepositoryUtils.mapAcceptedToString(qualyfingCompetition.getAccepted()),
                qualyfingCompetition.getRejectionReasonCode() != null ? qualyfingCompetition.getRejectionReasonCode().value() : null,
                qualyfingCompetition.getRejectionReason()
            );
        } catch (Exception e) {
            logger.error("Error during inserting into application_qualyfing_competition", e);
        }
    }
}
