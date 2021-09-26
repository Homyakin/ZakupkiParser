package ru.homyakin.zakupki.database.purchase_protocol;

import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.homyakin.zakupki.database.NonResidentInfoRepository;
import ru.homyakin.zakupki.database.SupplierInfoRepository;
import ru.homyakin.zakupki.models._223fz.purchase.ProtocolApplicationType;
import ru.homyakin.zakupki.models._223fz.types.WinnerIndication;
import ru.homyakin.zakupki.utils.CommonUtils;
import ru.homyakin.zakupki.utils.RepositoryUtils;

@Component
public class ApplicationRepository {
    private final static Logger logger = LoggerFactory.getLogger(ApplicationRepository.class);
    private final static String INSERT =
        """
        insert into application (guid, protocol_lot_applications_id, number, application_date, not_dishonest, provider, supplier_guid, non_resident_info_guid,
         price, currency_code, price_info, commodity_amount, contract_execution_term, conditions_proposals, accepted,
         application_rejection_reason_code, rejection_reason, winner_indication, commission_decision, contract_signed,
         additional_price, rating, assessment_result, commission_decision_place)
         values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);
        """;

    private final JdbcTemplate jdbcTemplate;
    private final SupplierInfoRepository supplierInfoRepository;
    private final NonResidentInfoRepository nonResidentInfoRepository;
    private final ProtocolLotCriteriaRepository protocolLotCriteriaRepository;
    private final ApplicationToCriteriaRepository applicationToCriteriaRepository;
    private final ApplicationQualifingCompetitionRepository applicationQualifingCompetitionRepository;
    private final ApplicationConsiderationResultRepository applicationConsiderationResultRepository;

    public ApplicationRepository(
        DataSource dataSource,
        SupplierInfoRepository supplierInfoRepository,
        NonResidentInfoRepository nonResidentInfoRepository,
        ProtocolLotCriteriaRepository protocolLotCriteriaRepository,
        ApplicationToCriteriaRepository applicationToCriteriaRepository,
        ApplicationQualifingCompetitionRepository applicationQualifingCompetitionRepository,
        ApplicationConsiderationResultRepository applicationConsiderationResultRepository
    ) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        this.supplierInfoRepository = supplierInfoRepository;
        this.nonResidentInfoRepository = nonResidentInfoRepository;
        this.protocolLotCriteriaRepository = protocolLotCriteriaRepository;
        this.applicationToCriteriaRepository = applicationToCriteriaRepository;
        this.applicationQualifingCompetitionRepository = applicationQualifingCompetitionRepository;
        this.applicationConsiderationResultRepository = applicationConsiderationResultRepository;
    }

    public void insert(ProtocolApplicationType application, int protocolLotApplicationsId) {
        try {
            final var guid = CommonUtils.generateGuid();
            final var supplierGuid = supplierInfoRepository.insert(application.getSupplierInfo());
            final var nonResidentGuid = nonResidentInfoRepository.insert(application.getNonResidentInfo());
            jdbcTemplate.update(
                INSERT,
                guid,
                protocolLotApplicationsId,
                application.getApplicationNumber(),
                RepositoryUtils.convertFromXMLGregorianCalendarToLocalDateTime(application.getApplicationDate()),
                RepositoryUtils.convertBoolean(application.isNotDishonest()),
                RepositoryUtils.convertBoolean(application.isProvider()),
                supplierGuid.orElse(null),
                nonResidentGuid.orElse(null),
                application.getPrice(),
                RepositoryUtils.getCurrencyCode(application.getCurrency()),
                application.getPriceInfo(),
                application.getCommodityAmount(),
                application.getContractExecutionTerm(),
                application.getConditionProposals(),
                RepositoryUtils.mapAcceptedToString(application.getAccepted()),
                application.getRejectionReasonCode() != null ? application.getRejectionReasonCode().value() : null,
                application.getRejectionReason(),
                mapWinnerIndicationToString(application.getWinnerIndication()),
                application.getCommissionDecision(),
                RepositoryUtils.convertBoolean(application.isContractSigned()),
                application.getAdditionalPrice(),
                application.getRating(),
                application.getAssessmentResult(),
                application.getCommissionDecisionPlace()
            );
            if (application.getCriteria() != null) {
                for (var criteria : application.getCriteria().getApplicationLotCriteria()) {
                    protocolLotCriteriaRepository.insert(criteria);
                    applicationToCriteriaRepository.insert(
                        guid,
                        protocolLotApplicationsId,
                        criteria.getLotApplcationsCriteria().getLotApplcationsCriteriaGuid()
                    );
                }
            }
            applicationQualifingCompetitionRepository.insert(
                application.getQualyfingCompetitionInfo(),
                guid,
                protocolLotApplicationsId
            );
            applicationConsiderationResultRepository.insert(
                application.getConsiderationResultInfo(),
                guid,
                protocolLotApplicationsId
            );
        } catch (Exception e) {
            logger.error("Error during inserting into application", e);
        }
    }

    private String mapWinnerIndicationToString(WinnerIndication winnerIndication) {
        if (winnerIndication == null) return null;
        return switch (winnerIndication) {
            case F -> "Победитель";
            case S -> "Второе место";
            case T -> "Третье место";
            case B -> "Ниже третьего";
            case N -> "Не указывается в данном протоколе";
        };
    }
}
