package ru.homyakin.zakupki.database.purchase_protocol;

import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.homyakin.zakupki.database.CustomerRepository;
import ru.homyakin.zakupki.models.Folder;
import ru.homyakin.zakupki.models._223fz.purchase.PurchaseProtocolDataType;
import ru.homyakin.zakupki.models._223fz.types.PurchaseProtocolStatusType;
import ru.homyakin.zakupki.utils.RepositoryUtils;

@Component
public class PurchaseProtocolRepository {
    private final static Logger logger = LoggerFactory.getLogger(PurchaseProtocolRepository.class);
    private final static String INSERT =
        """
        insert into purchase_protocol (guid, create_date_time, url_eis, url_vsrz, url_kis_rmis,
         protocol_purchase_info_guid, registration_number, placer_inn, customer_inn, additional_info, missed_contest,
         missed_reason, purchase_cancellation_reason, publication_date_time, status, version, modification_description,
         type, type_name, target_phase_code, procedure_date, procedure_place,
         protocol_signed_date, template_version, is_lot_oriented, commission_number, commission_name, commission_result,
         region_name, protocol_type)
         values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);
        """;

    private final JdbcTemplate jdbcTemplate;
    private final ProtocolPurchaseInfoRepository protocolPurchaseInfoRepository;
    private final CustomerRepository customerRepository;
    private final ProtocolLotApplicationsRepository protocolLotApplicationsRepository;

    public PurchaseProtocolRepository(
        DataSource dataSource,
        ProtocolPurchaseInfoRepository protocolPurchaseInfoRepository,
        CustomerRepository customerRepository,
        ProtocolLotApplicationsRepository protocolLotApplicationsRepository
    ) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        this.protocolPurchaseInfoRepository = protocolPurchaseInfoRepository;
        this.customerRepository = customerRepository;
        this.protocolLotApplicationsRepository = protocolLotApplicationsRepository;
    }

    public void insert(PurchaseProtocolDataType protocol, Folder folder, String region) {
        logger.info("Processing purchase protocol with guid {}", protocol.getGuid());
        var protocolPurchaseInfoGuid = protocolPurchaseInfoRepository.insert(protocol.getPurchaseInfo());
        customerRepository.insert(protocol.getPlacer().getMainInfo());
        String customerInn = null;
        if (protocol.getCustomer() != null) {
            customerRepository.insert(protocol.getCustomer().getMainInfo());
            customerInn = protocol.getCustomer().getMainInfo().getInn();
        }
        try {
            jdbcTemplate.update(
                INSERT,
                protocol.getGuid(),
                RepositoryUtils.convertFromXMLGregorianCalendarToLocalDateTime(protocol.getCreateDateTime()),
                protocol.getUrlEIS(),
                protocol.getUrlVSRZ(),
                protocol.getUrlKisRmis(),
                protocolPurchaseInfoGuid,
                protocol.getRegistrationNumber(),
                protocol.getPlacer().getMainInfo().getInn(),
                customerInn,
                protocol.getAdditionalInfo(),
                RepositoryUtils.convertBoolean(protocol.isMissedContest()),
                protocol.getMissedReason(),
                protocol.getPurchaseCancellationReason() != null ? protocol.getPurchaseCancellationReason().value() : null,
                RepositoryUtils.convertFromXMLGregorianCalendarToLocalDateTime(protocol.getPublicationDateTime()),
                mapStatusToString(protocol.getStatus()),
                protocol.getVersion(),
                protocol.getModificationDescription(),
                protocol.getType(),
                protocol.getTypeName(),
                protocol.getTargetPhaseCode(),
                RepositoryUtils.convertFromXMLGregorianCalendarToLocalDate(protocol.getProcedureDate()),
                protocol.getProcedurePlace(),
                RepositoryUtils.convertFromXMLGregorianCalendarToLocalDate(protocol.getProtocolSignDate()),
                protocol.getTemplateVersion(),
                RepositoryUtils.convertBoolean(protocol.isIsLotOriented()),
                protocol.getCommissionNumber(),
                protocol.getCommissionName(),
                protocol.getCommissionResult(),
                region,
                folder.getName()
            );
        } catch (DuplicateKeyException ignored) {
        } catch (Exception e) {
            logger.error("Error during inserting purchase protocol {}", protocol.getGuid(), e);
        }
        if (protocol.getLotApplicationsList() != null) {
            for (var protocolLotApplications : protocol.getLotApplicationsList().getProtocolLotApplications()) {
                protocolLotApplicationsRepository.insert(
                    protocolLotApplications,
                    protocol.getGuid()
                );
            }
        }
    }

    private String mapStatusToString(PurchaseProtocolStatusType status) {
        if (status == null) return null;
        return switch (status) {
            case F -> "Редактирование";
            case P -> "Размещено";
            case I -> "Недействительно";
            case M -> "Изменение";
        };
    }
}
