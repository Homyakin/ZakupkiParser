package ru.homyakin.zakupki.database.purchase_notice;

import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.homyakin.zakupki.models._223fz.types.LotType;
import ru.homyakin.zakupki.models._223fz.types.LotTypeIS;
import ru.homyakin.zakupki.utils.RepositoryUtils;

@Component
public class PurchaseNoticeLotRepository {
    private static final Logger logger = LoggerFactory.getLogger(PurchaseNoticeLotRepository.class);
    private final JdbcTemplate jdbcTemplate;
    private final RepositoryUtils repositoryUtils;
    private final LotCriteriaRepository lotCriteriaRepository;
    private final PurchaseNoticeLotDataRepository purchaseNoticeLotDataRepository;
    private final LotExtraRepository lotExtraRepository;
    private final JointLotDataRepository jointLotDataRepository;

    public PurchaseNoticeLotRepository(
        DataSource dataSource,
        RepositoryUtils repositoryUtils,
        LotCriteriaRepository lotCriteriaRepository,
        PurchaseNoticeLotDataRepository purchaseNoticeLotDataRepository,
        LotExtraRepository lotExtraRepository,
        JointLotDataRepository jointLotDataRepository
    ) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.repositoryUtils = repositoryUtils;
        this.lotCriteriaRepository = lotCriteriaRepository;
        this.purchaseNoticeLotDataRepository = purchaseNoticeLotDataRepository;
        this.lotExtraRepository = lotExtraRepository;
        this.jointLotDataRepository = jointLotDataRepository;
    }

    public void insert(LotType lot, String noticeGuid) {
        String sql = "INSERT INTO zakupki.purchase_notice_lot (guid, purchase_notice_guid, ordinal_number," +
            "lot_edit_enabled, delivery_place_indication_code, joint_lot, plan_guid, position_number," +
            "lot_plan_position, position_guid, contract_subject, cancelled, cancel_date, cancel_info, emergency)" +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            var plan = lot.getLotPlanInfo();
            jdbcTemplate.update(
                sql,
                lot.getGuid(),
                noticeGuid,
                lot.getOrdinalNumber(),
                repositoryUtils.convertBoolean(lot.isLotEditEnabled()),
                lot.getDeliveryPlaceIndication() != null ? lot.getDeliveryPlaceIndication().value() : null,
                repositoryUtils.convertBoolean(isJointLot(lot.getJointLotData())),
                plan != null ? plan.getPlanGuid() : null,
                plan != null ? plan.getPositionNumber() : null,
                plan != null ? plan.getLotPlanPosition().value() : null,
                plan != null ? plan.getPositionGuid() : null,
                plan != null ? plan.getContractSubject() : null,
                repositoryUtils.convertBoolean(lot.isCancelled()),
                lot.getCancellation() != null ? repositoryUtils.convertFromXMLGregorianCalendarToLocalDate(lot.getCancellation().getCancelDate()) : null,
                lot.getCancellation() != null ? lot.getCancellation().getCancelInfo() : null,
                lot.getCancellation() != null ? repositoryUtils.convertBoolean(lot.getCancellation().isEmergency()) : null
            );
            if (lot instanceof LotTypeIS) {
                var lotIS = (LotTypeIS) lot;
                if (lotIS.getExtendFields() != null) {
                    for (var noticeField : lotIS.getExtendFields().getNoticeExtendField()) {
                        for (var field : noticeField.getExtendField()) {
                            lotExtraRepository.insert(field, lot.getGuid());
                        }
                    }
                }
            }
            if (lot.getCriteria() != null) {
                for (var criteria : lot.getCriteria().getLotCriteria()) {
                    lotCriteriaRepository.insert(criteria, lot.getGuid());
                }
            }
            purchaseNoticeLotDataRepository.insert(lot.getLotData(), lot.getGuid());
            if (isJointLot(lot.getJointLotData())) {
                for (var lotCustomer : lot.getJointLotData().getLotCustomers().getLotCustomer()) {
                    jointLotDataRepository.insert(lotCustomer, lot.getGuid());
                }
            }

        } catch (DuplicateKeyException ignored) {
            //TODO посмотреть файлы
        } catch (Exception e) {
            logger.error("Error during inserting purchase notice lot", e);
        }
    }

    private boolean isJointLot(LotType.JointLotData joint) {
        if (joint == null) return false;
        else return joint.isJointLot();
    }
}
