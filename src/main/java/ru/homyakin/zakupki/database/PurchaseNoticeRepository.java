package ru.homyakin.zakupki.database;

import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.homyakin.zakupki.models._223fz.purchase.PurchaseNotice;

@Component
public class PurchaseNoticeRepository extends BaseRepository<PurchaseNotice> {
    private static final Logger logger = LoggerFactory.getLogger(PurchaseNoticeRepository.class);
    private final JdbcTemplate jdbcTemplate;
    private final RepositoryService repositoryService;
    private final CustomerRepository customerRepository;
    private final ElectronicPlaceRepository electronicPlaceRepository;
    private final PlacingProcedureRepository placingProcedureRepository;
    private final PurchaseNoticeContactRepository purchaseNoticeContactRepository;
    private final PurchaseNoticeDocumentationDelivery purchaseNoticeDocumentationDelivery;
    private final PurchaseNoticeExtraRepository purchaseNoticeExtraRepository;
    private final PurchaseNoticeLotRepository purchaseNoticeLotRepository;

    public PurchaseNoticeRepository(
        DataSource dataSource,
        RepositoryService repositoryService,
        CustomerRepository customerRepository,
        ElectronicPlaceRepository electronicPlaceRepository,
        PlacingProcedureRepository placingProcedureRepository,
        PurchaseNoticeContactRepository purchaseNoticeContactRepository,
        PurchaseNoticeDocumentationDelivery purchaseNoticeDocumentationDelivery,
        PurchaseNoticeExtraRepository purchaseNoticeExtraRepository,
        PurchaseNoticeLotRepository purchaseNoticeLotRepository
    ) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.repositoryService = repositoryService;
        this.customerRepository = customerRepository;
        this.electronicPlaceRepository = electronicPlaceRepository;
        this.placingProcedureRepository = placingProcedureRepository;
        this.purchaseNoticeContactRepository = purchaseNoticeContactRepository;
        this.purchaseNoticeDocumentationDelivery = purchaseNoticeDocumentationDelivery;
        this.purchaseNoticeExtraRepository = purchaseNoticeExtraRepository;
        this.purchaseNoticeLotRepository = purchaseNoticeLotRepository;
    }

    @Override
    public void insert(PurchaseNotice purchaseNotice) {
        String sql = "INSERT INTO zakupki.purchase_notice_data (guid, create_date_time, url_eis, url_vsrz, " +
            "url_kis_rmis, registration_number, name, customer_inn, detached_org_inn, blocked_customer_inn," +
            "purchase_method_code, purchase_code_name, placer_inn, publication_date_time, purchase_notice_status_code," +
            "version, modification_description, not_dishonest, modification_date, save_user_id," +
            "delivery_place_indication, emergency, joint_purchase, for_small_or_middle, change_decision_date," +
            "antimonopoly_decision_taken, additional_info, appl_submision_place, appl_submision_start_date," +
            "appl_submision_order, envelope_opening_order, appl_examination_order, summingup_order, auction_order," +
            "consideration_second_part_place, consideration_second_part_order, is_upload_complete, electronic_place_id," +
            "submission_close_date_time, publication_planned_date)" +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " +
            "?, ?, ?, ?, ?, ?, ?, ?)";
        var data = purchaseNotice.getBody().getItem().getPurchaseNoticeData();
        logger.info("Inserting purchase notice with guid: {}", data.getGuid());
        try {
            customerRepository.insert(data.getCustomer().getMainInfo());
            customerRepository.insert(data.getPlacer().getMainInfo());
            String detachedOrgInn = null;
            if (data.getDetachedOrg() != null) {
                detachedOrgInn = data.getDetachedOrg().getMainInfo().getInn();
                customerRepository.insert(data.getDetachedOrg().getMainInfo());
            }
            String blockedCustomerInn = null;
            if (data.getBlockedCustomer() != null) {
                blockedCustomerInn = data.getBlockedCustomer().getMainInfo().getInn();
                customerRepository.insert(data.getBlockedCustomer().getMainInfo());
            }
            electronicPlaceRepository.insert(data.getElectronicPlaceInfo());
            jdbcTemplate.update(
                sql,
                data.getGuid(),
                repositoryService.convertFromXMLGregorianCalendarToLocalDateTime(data.getCreateDateTime()),
                data.getUrlEIS(),
                data.getUrlVSRZ(),
                data.getUrlKisRmis(),
                data.getRegistrationNumber(),
                data.getName(),
                data.getCustomer().getMainInfo().getInn(),
                detachedOrgInn,
                blockedCustomerInn,
                data.getPurchaseMethodCode(),
                data.getPurchaseCodeName(),
                data.getPlacer().getMainInfo().getInn(),
                repositoryService.convertFromXMLGregorianCalendarToLocalDateTime(data.getPublicationDateTime()),
                data.getStatus() != null ? data.getStatus().value() : null,
                data.getVersion(),
                repositoryService.removeExtraSpaces(data.getModificationDescription()),
                repositoryService.convertBoolean(data.isNotDishonest()),
                repositoryService.convertFromXMLGregorianCalendarToLocalDateTime(data.getModificationDate()),
                data.getSaveUserId(),
                data.getDeliveryPlaceIndication() != null ? data.getDeliveryPlaceIndication().value() : null,
                repositoryService.convertBoolean(data.isEmergency()),
                repositoryService.convertBoolean(data.isJointPurchase()),
                repositoryService.convertBoolean(data.isForSmallOrMiddle()),
                repositoryService.convertFromXMLGregorianCalendarToLocalDate(data.getChangeDecisionDate()),
                repositoryService.convertBoolean(data.isAntimonopolyDecisionTaken()),
                repositoryService.removeExtraSpaces(data.getAdditionalInfo()),
                repositoryService.removeExtraSpaces(data.getApplSubmisionPlace()),
                repositoryService.convertFromXMLGregorianCalendarToLocalDate(data.getApplSubmisionStartDate()),
                repositoryService.removeExtraSpaces(data.getApplSubmisionOrder()),
                repositoryService.removeExtraSpaces(data.getEnvelopeOpeningOrder()),
                repositoryService.removeExtraSpaces(data.getApplExaminationOrder()),
                repositoryService.removeExtraSpaces(data.getSummingupOrder()),
                repositoryService.removeExtraSpaces(data.getAuctionOrder()),
                repositoryService.removeExtraSpaces(data.getConsiderationSecondPartPlace()),
                repositoryService.removeExtraSpaces(data.getConsiderationSecondPartOrder()),
                repositoryService.convertBoolean(data.isIsUploadComplete()),
                data.getElectronicPlaceInfo() != null ? data.getElectronicPlaceInfo().getElectronicPlaceId() : null,
                repositoryService.convertFromXMLGregorianCalendarToLocalDateTime(data.getSubmissionCloseDateTime()),
                repositoryService.convertFromXMLGregorianCalendarToLocalDate(data.getPublicationPlannedDate())
            );

            placingProcedureRepository.insert(data.getPlacingProcedure(), data.getGuid());
            purchaseNoticeContactRepository.insert(data.getContact(), data.getGuid());
            purchaseNoticeDocumentationDelivery.insert(data.getDocumentationDelivery(), data.getGuid());
            if (data.getExtendFields() != null) {
                for (var noticeField : data.getExtendFields().getNoticeExtendField()) {
                    for (var field : noticeField.getExtendField()) {
                        purchaseNoticeExtraRepository.insert(field, data.getGuid());
                    }
                }
            }
            for (var lot : data.getLots().getLot()) {
                purchaseNoticeLotRepository.insert(lot, data.getGuid());
            }
        } catch (Exception e) {
            logger.error("Error during inserting purchase notice with guid {}", data.getGuid(), e);
        }
    }
}
