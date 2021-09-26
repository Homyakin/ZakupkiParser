package ru.homyakin.zakupki.database.purchase_protocol;

import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import ru.homyakin.zakupki.models._223fz.purchase.ProtocolLotApplications;

@Component
public class ProtocolLotApplicationsRepository {
    private final static Logger logger = LoggerFactory.getLogger(ProtocolLotApplicationsRepository.class);

    private final SimpleJdbcInsert simpleJdbcInsert;
    private final ProtocolLotCriteriaRepository protocolLotCriteriaRepository;
    private final ProtocolLotApplicationsToCriteriaRepository protocolLotApplicationsToCriteriaRepository;
    private final ProtocolLotRepository protocolLotRepository;
    private final ApplicationRepository applicationRepository;

    public ProtocolLotApplicationsRepository(
        DataSource dataSource,
        ProtocolLotCriteriaRepository protocolLotCriteriaRepository,
        ProtocolLotApplicationsToCriteriaRepository protocolLotApplicationsToCriteriaRepository,
        ProtocolLotRepository protocolLotRepository,
        ApplicationRepository applicationRepository
    ) {
        simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
            .withTableName("protocol_lot_applications")
            .usingGeneratedKeyColumns("id");
        this.protocolLotCriteriaRepository = protocolLotCriteriaRepository;
        this.protocolLotApplicationsToCriteriaRepository = protocolLotApplicationsToCriteriaRepository;
        this.protocolLotRepository = protocolLotRepository;
        this.applicationRepository = applicationRepository;
    }

    public void insert(ProtocolLotApplications lotApplications, String purchaseProtocolGuid) {
        try {
            protocolLotRepository.insert(lotApplications.getLot(), lotApplications.getLotParameters());
            Map<String, Object> parameters = new HashMap<>(19);
            parameters.put("purchase_protocol_guid", purchaseProtocolGuid);
            parameters.put("protocol_lot_guid", lotApplications.getLot().getGuid());
            final var id = simpleJdbcInsert.executeAndReturnKey(parameters).intValue();
            if (lotApplications.getCriteria() != null) {
                for (var criteria : lotApplications.getCriteria().getLotApplicationsCriteria()) {
                    protocolLotCriteriaRepository.insert(criteria);
                    protocolLotApplicationsToCriteriaRepository.insert(id, criteria.getGuid());
                }
            }
            for (var application: lotApplications.getApplication()) {
                applicationRepository.insert(application, id);
            }
        } catch (Exception e) {
            logger.error("Error during inserting into protocol_lot_applications", e);
        }
    }
}
