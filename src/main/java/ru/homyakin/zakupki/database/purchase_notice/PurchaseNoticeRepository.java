package ru.homyakin.zakupki.database.purchase_notice;

import javax.sql.DataSource;
import javax.xml.datatype.XMLGregorianCalendar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.homyakin.zakupki.database.CustomerRepository;
import ru.homyakin.zakupki.database.purchase_plan.ElectronicPlaceRepository;
import ru.homyakin.zakupki.models.Folder;
import ru.homyakin.zakupki.models._223fz.purchase.PurchaseNoticeAE94FZDataType;
import ru.homyakin.zakupki.models._223fz.purchase.PurchaseNoticeAEDataType;
import ru.homyakin.zakupki.models._223fz.purchase.PurchaseNoticeDataBaseType;
import ru.homyakin.zakupki.models._223fz.purchase.PurchaseNoticeDataType;
import ru.homyakin.zakupki.models._223fz.purchase.PurchaseNoticeNonISBaseType;
import ru.homyakin.zakupki.models._223fz.purchase.PurchaseNoticeOADataType;
import ru.homyakin.zakupki.models._223fz.purchase.PurchaseNoticeOKDataType;
import ru.homyakin.zakupki.models._223fz.purchase.PurchaseNoticeZKDataType;
import ru.homyakin.zakupki.models._223fz.types.ElectronicPlaceInfoType;
import ru.homyakin.zakupki.utils.RepositoryUtils;

@Component
public class PurchaseNoticeRepository {
    private static final Logger logger = LoggerFactory.getLogger(PurchaseNoticeRepository.class);
    private final JdbcTemplate jdbcTemplate;
    private final CustomerRepository customerRepository;
    private final ElectronicPlaceRepository electronicPlaceRepository;
    private final PlacingProcedureRepository placingProcedureRepository;
    private final PurchaseNoticeContactRepository purchaseNoticeContactRepository;
    private final PurchaseNoticeDocumentationDelivery purchaseNoticeDocumentationDelivery;
    private final PurchaseNoticeExtraRepository purchaseNoticeExtraRepository;
    private final PurchaseNoticeLotRepository purchaseNoticeLotRepository;
    private final PurchaseNoticeToLotRepository purchaseNoticeToLotRepository;

    public PurchaseNoticeRepository(
        DataSource dataSource,
        CustomerRepository customerRepository,
        ElectronicPlaceRepository electronicPlaceRepository,
        PlacingProcedureRepository placingProcedureRepository,
        PurchaseNoticeContactRepository purchaseNoticeContactRepository,
        PurchaseNoticeDocumentationDelivery purchaseNoticeDocumentationDelivery,
        PurchaseNoticeExtraRepository purchaseNoticeExtraRepository,
        PurchaseNoticeLotRepository purchaseNoticeLotRepository,
        PurchaseNoticeToLotRepository purchaseNoticeToLotRepository
    ) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.customerRepository = customerRepository;
        this.electronicPlaceRepository = electronicPlaceRepository;
        this.placingProcedureRepository = placingProcedureRepository;
        this.purchaseNoticeContactRepository = purchaseNoticeContactRepository;
        this.purchaseNoticeDocumentationDelivery = purchaseNoticeDocumentationDelivery;
        this.purchaseNoticeExtraRepository = purchaseNoticeExtraRepository;
        this.purchaseNoticeLotRepository = purchaseNoticeLotRepository;
        this.purchaseNoticeToLotRepository = purchaseNoticeToLotRepository;
    }

    public void insert(PurchaseNoticeDataBaseType data, Folder folder, String region) {
        String sql = "INSERT INTO zakupki.purchase_notice (guid, purchase_notice_type_code, create_date_time, url_eis, url_vsrz, " +
            "url_kis_rmis, registration_number, name, customer_inn, detached_org_inn, blocked_customer_inn," +
            "purchase_method_code, purchase_code_name, placer_inn, publication_date_time, purchase_notice_status_code," +
            "version, modification_description, not_dishonest, modification_date, save_user_id," +
            "delivery_place_indication, emergency, joint_purchase, for_small_or_middle, change_decision_date," +
            "antimonopoly_decision_taken, additional_info, appl_submision_place, appl_submision_start_date," +
            "appl_submision_order, envelope_opening_order, appl_examination_order, summingup_order, auction_order," +
            "consideration_second_part_place, consideration_second_part_order, is_upload_complete, electronic_place_id," +
            "submission_close_date_time, publication_planned_date, region_name)" +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " +
            "?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
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

            ElectronicPlaceInfoType electronicPlaceInfo = null;
            XMLGregorianCalendar submissionCloseDateTime = null;
            XMLGregorianCalendar publicationPlannedDate = null;

            if (data instanceof PurchaseNoticeDataType notice) {
                electronicPlaceInfo = notice.getElectronicPlaceInfo();
                submissionCloseDateTime = notice.getSubmissionCloseDateTime();
                publicationPlannedDate = notice.getPublicationPlannedDate();
            } else if (data instanceof PurchaseNoticeAE94FZDataType noticeAE94) {
                electronicPlaceInfo = noticeAE94.getElectronicPlaceInfo();
                submissionCloseDateTime = noticeAE94.getSubmissionCloseDateTime();
                publicationPlannedDate = noticeAE94.getPublicationPlannedDate();
            } else if (data instanceof PurchaseNoticeAEDataType noticeAE) {
                electronicPlaceInfo = noticeAE.getElectronicPlaceInfo();
                submissionCloseDateTime = noticeAE.getSubmissionCloseDateTime();
                publicationPlannedDate = noticeAE.getPublicationPlannedDate();
            } else if (data instanceof PurchaseNoticeOADataType noticeOA) {
                submissionCloseDateTime = noticeOA.getSubmissionCloseDateTime();
                publicationPlannedDate = noticeOA.getPublicationPlannedDate();
            } else if (data instanceof PurchaseNoticeOKDataType noticeOK) {
                submissionCloseDateTime = noticeOK.getSubmissionCloseDateTime();
                publicationPlannedDate = noticeOK.getPublicationPlannedDate();
            } else if (data instanceof PurchaseNoticeZKDataType noticeZK) {
                submissionCloseDateTime = noticeZK.getSubmissionCloseDateTime();
                publicationPlannedDate = noticeZK.getPublicationPlannedDate();
            }
            electronicPlaceRepository.insert(electronicPlaceInfo);
            jdbcTemplate.update(
                sql,
                data.getGuid(),
                folder.getName(),
                RepositoryUtils.convertFromXMLGregorianCalendarToLocalDateTime(data.getCreateDateTime()),
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
                RepositoryUtils.convertFromXMLGregorianCalendarToLocalDateTime(data.getPublicationDateTime()),
                data.getStatus() != null ? data.getStatus().value() : null,
                data.getVersion(),
                RepositoryUtils.removeExtraSpaces(data.getModificationDescription()),
                RepositoryUtils.convertBoolean(data.isNotDishonest()),
                RepositoryUtils.convertFromXMLGregorianCalendarToLocalDateTime(data.getModificationDate()),
                data.getSaveUserId(),
                data.getDeliveryPlaceIndication() != null ? data.getDeliveryPlaceIndication().value() : null,
                RepositoryUtils.convertBoolean(data.isEmergency()),
                RepositoryUtils.convertBoolean(data.isJointPurchase()),
                RepositoryUtils.convertBoolean(data.isForSmallOrMiddle()),
                RepositoryUtils.convertFromXMLGregorianCalendarToLocalDate(data.getChangeDecisionDate()),
                RepositoryUtils.convertBoolean(data.isAntimonopolyDecisionTaken()),
                RepositoryUtils.removeExtraSpaces(data.getAdditionalInfo()),
                RepositoryUtils.removeExtraSpaces(data.getApplSubmisionPlace()),
                RepositoryUtils.convertFromXMLGregorianCalendarToLocalDate(data.getApplSubmisionStartDate()),
                RepositoryUtils.removeExtraSpaces(data.getApplSubmisionOrder()),
                RepositoryUtils.removeExtraSpaces(data.getEnvelopeOpeningOrder()),
                RepositoryUtils.removeExtraSpaces(data.getApplExaminationOrder()),
                RepositoryUtils.removeExtraSpaces(data.getSummingupOrder()),
                RepositoryUtils.removeExtraSpaces(data.getAuctionOrder()),
                RepositoryUtils.removeExtraSpaces(data.getConsiderationSecondPartPlace()),
                RepositoryUtils.removeExtraSpaces(data.getConsiderationSecondPartOrder()),
                RepositoryUtils.convertBoolean(data.isIsUploadComplete()),
                electronicPlaceInfo != null ? electronicPlaceInfo.getElectronicPlaceId() : null,
                RepositoryUtils.convertFromXMLGregorianCalendarToLocalDateTime(submissionCloseDateTime),
                RepositoryUtils.convertFromXMLGregorianCalendarToLocalDate(publicationPlannedDate),
                region
            );
            if (data instanceof PurchaseNoticeDataType notice) {
                placingProcedureRepository.insert(notice.getPlacingProcedure(), data.getGuid());
                if (notice.getExtendFields() != null) {
                    for (var noticeField : notice.getExtendFields().getNoticeExtendField()) {
                        for (var field : noticeField.getExtendField()) {
                            purchaseNoticeExtraRepository.insert(field, data.getGuid());
                        }
                    }
                }
            }
            purchaseNoticeContactRepository.insert(data.getContact(), data.getGuid());
            purchaseNoticeDocumentationDelivery.insert(data.getDocumentationDelivery(), data.getGuid());
            if (data instanceof PurchaseNoticeDataType notice) {
                if (notice.getLots() != null) {
                    for (var lot : notice.getLots().getLot()) {
                        purchaseNoticeLotRepository.insert(lot);
                        purchaseNoticeToLotRepository.insert(data.getGuid(), lot.getGuid());
                    }
                }
            } else if (data instanceof PurchaseNoticeNonISBaseType noticeNonIS) {
                if (noticeNonIS.getLots() != null) {
                    for (var lot : noticeNonIS.getLots().getLot()) {
                        purchaseNoticeLotRepository.insert(lot);
                        purchaseNoticeToLotRepository.insert(data.getGuid(), lot.getGuid());
                    }
                }
            }
        } catch (DuplicateKeyException ignored) {
        } catch (Exception e) {
            logger.error("Error during inserting purchase notice with guid {}", data.getGuid(), e);
        }
    }
}
