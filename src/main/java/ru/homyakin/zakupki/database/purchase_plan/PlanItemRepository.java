package ru.homyakin.zakupki.database.purchase_plan;

import java.util.List;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.homyakin.zakupki.models._223fz.purchaseplan.BasePlanDataItemType;
import ru.homyakin.zakupki.models._223fz.purchaseplan.CancellationReasonType;
import ru.homyakin.zakupki.models._223fz.purchaseplan.InnovationPlanDataItemType;
import ru.homyakin.zakupki.models._223fz.purchaseplan.PurchasePlanDataItemType;
import ru.homyakin.zakupki.models._223fz.purchaseplan.PurchasePlanItemCheckResultType;
import ru.homyakin.zakupki.models._223fz.types.PurchasePlanItemStatusType;
import ru.homyakin.zakupki.utils.RepositoryUtils;

@Component
public class PlanItemRepository {
    private static final Logger logger = LoggerFactory.getLogger(PlanItemRepository.class);
    private final JdbcTemplate jdbcTemplate;
    private final LongTermVolumesRepository longTermVolumesRepository;
    private final PlanItemRowRepository planItemRowRepository;
    private final RepositoryUtils repositoryUtils;
    private final String INSERT_PLAN_ITEM = "INSERT INTO zakupki.plan_item (guid, purchase_plan_guid, ordinal_number," +
        "contract_subject, plan_item_customer_inn, minimum_requirements, contract_end_date," +
        "additional_info, modification_description, status_code, is_purchase_placed, changed_gws_and_dates," +
        "changed_nmsk_more_ten_percent, other_changes, check_result, error_messages, cancellation_reason," +
        "long_term, shared, initial_position_guid, initial_plan_guid, maximum_contract_price," +
        "currency_code, exchange_rate, exchange_rate_date, maximum_contract_price_rub, order_pricing," +
        "innovation_equivalent, purchase_category_code, is_smb, is_innovation)" +
        "VALUES" +
        "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private final String UPDATE = "UPDATE plan_item SET ordinal_number = ?, contract_subject = ?, minimum_requirements = ?, " +
        "contract_end_date = ?, additional_info = ?, modification_description = ?, status_code = ?, is_purchase_placed = ?," +
        "changed_gws_and_dates = ?, changed_nmsk_more_ten_percent = ?, other_changes = ?, check_result = ?, " +
        "error_messages = ?, cancellation_reason = ?, long_term = ?, shared = ?, initial_position_guid = ?, " +
        "initial_plan_guid = ?, maximum_contract_price = ?, currency_code = ?, exchange_rate = ?, " +
        "exchange_rate_date = ?, maximum_contract_price_rub = ?, order_pricing = ?, innovation_equivalent = ?, " +
        "purchase_category_code = ?, is_innovation = ? WHERE guid = ?";

    public PlanItemRepository(
        DataSource dataSource,
        LongTermVolumesRepository longTermVolumesRepository,
        PlanItemRowRepository planItemRowRepository,
        RepositoryUtils repositoryUtils
    ) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        this.longTermVolumesRepository = longTermVolumesRepository;
        this.planItemRowRepository = planItemRowRepository;
        this.repositoryUtils = repositoryUtils;
    }

    public void insert(PurchasePlanDataItemType purchasePlanItem, Boolean isSmb, String planGuid) {
        try {
            if (isSmb) {
                updateSmb(purchasePlanItem.getGuid(), true);
                return;
            } else {
                if (checkPlanItem(purchasePlanItem.getGuid())) {
                    updateSmb(purchasePlanItem.getGuid(), false);
                    updatePlanItem(purchasePlanItem);
                    if (purchasePlanItem.getLongTermVolumes() != null) {
                        longTermVolumesRepository.update(purchasePlanItem.getLongTermVolumes(), false,
                            purchasePlanItem.getGuid());
                    }
                    if (purchasePlanItem.getLongTermSMBVolumes() != null) {
                        longTermVolumesRepository.update(purchasePlanItem.getLongTermSMBVolumes(), true,
                            purchasePlanItem.getGuid());
                    }
                    if (purchasePlanItem.getPurchasePlanDataItemRows() != null) {
                        for (var i : purchasePlanItem.getPurchasePlanDataItemRows().getPurchasePlanRowItem()) {
                            planItemRowRepository.update(i, purchasePlanItem.getGuid());
                        }
                    }
                    return;
                }
            }
            jdbcTemplate.update(
                INSERT_PLAN_ITEM,
                purchasePlanItem.getGuid(),
                planGuid,
                purchasePlanItem.getOrdinalNumber(),
                repositoryUtils.removeExtraSpaces(purchasePlanItem.getContractSubject()),
                repositoryUtils.removeExtraSpaces(purchasePlanItem.getPlanItemCustomer().getMainInfo().getInn()), //required field
                repositoryUtils.removeExtraSpaces(purchasePlanItem.getMinimumRequirements()),
                repositoryUtils.convertFromXMLGregorianCalendarToLocalDate(purchasePlanItem.getContractEndDate()),
                repositoryUtils.removeExtraSpaces(purchasePlanItem.getAdditionalInfo()),
                repositoryUtils.removeExtraSpaces(purchasePlanItem.getModificationDescription()),
                repositoryUtils.removeExtraSpaces(getPlanItemStatus(purchasePlanItem.getStatus())),
                repositoryUtils.convertBoolean(purchasePlanItem.isIsPurchasePlaced()),
                repositoryUtils.convertBoolean(purchasePlanItem.isChangedGWSAndDates()),
                repositoryUtils.convertBoolean(purchasePlanItem.isChangedNMSKMoreTenPercent()),
                repositoryUtils.convertBoolean(purchasePlanItem.isOtherChanges()),
                repositoryUtils.removeExtraSpaces(getPlanItemCheckResult(purchasePlanItem.getCheckResult())),
                repositoryUtils.removeExtraSpaces(purchasePlanItem.getErrorMessages()),
                repositoryUtils.removeExtraSpaces(getCancellationReason(purchasePlanItem.getCancellationReason())),
                repositoryUtils.convertBoolean(purchasePlanItem.isLongTerm()),
                repositoryUtils.convertBoolean(purchasePlanItem.isShared()),
                getInitialPositionGuid(purchasePlanItem.getInitialPositionData()),
                getInitialPlanGuid(purchasePlanItem.getInitialPositionData()),
                purchasePlanItem.getMaximumContractPrice(),
                repositoryUtils.getCurrencyCode(purchasePlanItem.getCurrency()),
                purchasePlanItem.getExchangeRate(),
                repositoryUtils.convertFromXMLGregorianCalendarToLocalDate(purchasePlanItem.getExchangeRateDate()),
                purchasePlanItem.getMaximumContractPriceRub(),
                repositoryUtils.removeExtraSpaces(purchasePlanItem.getOrderPricing()),
                repositoryUtils.convertBoolean(purchasePlanItem.isInnovationEquivalent()),
                repositoryUtils.getCategoryCode(purchasePlanItem.getPurchaseCategory()),
                repositoryUtils.convertBoolean(false),
                repositoryUtils.convertBoolean(false)
            );
            insertToPurchasePlanItem(purchasePlanItem);
            if (purchasePlanItem.getLongTermVolumes() != null) {
                longTermVolumesRepository.insert(purchasePlanItem.getLongTermVolumes(), false,
                    purchasePlanItem.getGuid());
            }
            if (purchasePlanItem.getLongTermSMBVolumes() != null) {
                longTermVolumesRepository.insert(purchasePlanItem.getLongTermSMBVolumes(), true,
                    purchasePlanItem.getGuid());
            }
            if (purchasePlanItem.getPurchasePlanDataItemRows() != null) {
                for (var i : purchasePlanItem.getPurchasePlanDataItemRows().getPurchasePlanRowItem()) {
                    planItemRowRepository.insert(i, purchasePlanItem.getGuid());
                }
            }
        } catch (Exception e) {
            logger.error("Internal database error", e);
        }

    }

    public void insert(InnovationPlanDataItemType innovationPlanItem, Boolean isSmb, String planGuid) {
        try {
            if (isSmb) {
                updateSmb(innovationPlanItem.getGuid(), true);
                return;
            } else {
                if (checkPlanItem(innovationPlanItem.getGuid())) {
                    updateSmb(innovationPlanItem.getGuid(), false);
                    updatePlanItem(innovationPlanItem);
                    if (innovationPlanItem.getLongTermVolumes() != null) {
                        longTermVolumesRepository.update(innovationPlanItem.getLongTermVolumes(), false,
                            innovationPlanItem.getGuid());
                    }
                    if (innovationPlanItem.getLongTermSMBVolumes() != null) {
                        longTermVolumesRepository.update(innovationPlanItem.getLongTermSMBVolumes(), true,
                            innovationPlanItem.getGuid());
                    }
                    if (innovationPlanItem.getInnovationPlanDataItemRows() != null) {
                        for (var i : innovationPlanItem.getInnovationPlanDataItemRows().getInnovationPlanRowItem()) {
                            planItemRowRepository.update(i, innovationPlanItem.getGuid());
                        }
                    }
                    return;
                }
            }
            jdbcTemplate.update(
                INSERT_PLAN_ITEM,
                innovationPlanItem.getGuid(),
                planGuid,
                innovationPlanItem.getOrdinalNumber(),
                repositoryUtils.removeExtraSpaces(innovationPlanItem.getContractSubject()),
                repositoryUtils.removeExtraSpaces(innovationPlanItem.getPlanItemCustomer().getMainInfo().getInn()), //required field
                repositoryUtils.removeExtraSpaces(innovationPlanItem.getMinimumRequirements()),
                repositoryUtils.convertFromXMLGregorianCalendarToLocalDate(innovationPlanItem.getContractEndDate()),
                repositoryUtils.removeExtraSpaces(innovationPlanItem.getAdditionalInfo()),
                repositoryUtils.removeExtraSpaces(innovationPlanItem.getModificationDescription()),
                repositoryUtils.removeExtraSpaces(getPlanItemStatus(innovationPlanItem.getStatus())),
                repositoryUtils.convertBoolean(innovationPlanItem.isIsPurchasePlaced()),
                repositoryUtils.convertBoolean(innovationPlanItem.isChangedGWSAndDates()),
                repositoryUtils.convertBoolean(innovationPlanItem.isChangedNMSKMoreTenPercent()),
                repositoryUtils.convertBoolean(innovationPlanItem.isOtherChanges()),
                repositoryUtils.removeExtraSpaces(getPlanItemCheckResult(innovationPlanItem.getCheckResult())),
                repositoryUtils.removeExtraSpaces(innovationPlanItem.getErrorMessages()),
                repositoryUtils.removeExtraSpaces(getCancellationReason(innovationPlanItem.getCancellationReason())),
                repositoryUtils.convertBoolean(innovationPlanItem.isLongTerm()),
                repositoryUtils.convertBoolean(innovationPlanItem.isShared()),
                getInitialPositionGuid(innovationPlanItem.getInitialPositionData()),
                getInitialPlanGuid(innovationPlanItem.getInitialPositionData()),
                innovationPlanItem.getMaximumContractPrice(),
                repositoryUtils.getCurrencyCode(innovationPlanItem.getCurrency()),
                innovationPlanItem.getExchangeRate(),
                repositoryUtils.convertFromXMLGregorianCalendarToLocalDate(innovationPlanItem.getExchangeRateDate()),
                innovationPlanItem.getMaximumContractPriceRub(),
                repositoryUtils.removeExtraSpaces(innovationPlanItem.getOrderPricing()),
                repositoryUtils.convertBoolean(innovationPlanItem.isInnovationEquivalent()),
                repositoryUtils.getCategoryCode(innovationPlanItem.getPurchaseCategory()),
                repositoryUtils.convertBoolean(false),
                repositoryUtils.convertBoolean(true)
            );
            insertToInnovationPlanItem(innovationPlanItem);
            if (innovationPlanItem.getLongTermVolumes() != null) {
                longTermVolumesRepository.insert(innovationPlanItem.getLongTermVolumes(), false,
                    innovationPlanItem.getGuid());
            }
            if (innovationPlanItem.getLongTermSMBVolumes() != null) {
                longTermVolumesRepository.insert(innovationPlanItem.getLongTermSMBVolumes(), true,
                    innovationPlanItem.getGuid());
            }
            if (innovationPlanItem.getInnovationPlanDataItemRows() != null) {
                for (var i : innovationPlanItem.getInnovationPlanDataItemRows().getInnovationPlanRowItem()) {
                    planItemRowRepository.insert(i, innovationPlanItem.getGuid());
                }
            }
        } catch (Exception e) {
            logger.error("Internal database error", e);
        }
    }

    private void insertToInnovationPlanItem(InnovationPlanDataItemType innovationPlanItem) {
        String sql = "INSERT INTO zakupki.innovation_plan_item (guid, ignored_purchase, purchase_period_year)" +
            "VALUES" +
            "(?, ?, ?)";
        try {
            jdbcTemplate.update(
                sql,
                innovationPlanItem.getGuid(),
                repositoryUtils.convertBoolean(innovationPlanItem.isIgnoredPurchase()),
                innovationPlanItem.getPurchasePeriodYear()
            );
        } catch (Exception e) {
            logger.error("Internal database error e");
        }
    }

    private void insertToPurchasePlanItem(PurchasePlanDataItemType purchasePlanItem) {
        String sql = "INSERT INTO zakupki.purchase_plan_item (guid, notice_info_guid, lot_guid," +
            "okato, region, is_general_address, purchase_method_code, purchase_method_name, is_electronic, planned_after_second_year," +
            "is_purchase_ignored, purchase_period_year)" +
            "VALUES" +
            "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            String noticeInfoGuid = null;
            String lotGuid = null;
            if (purchasePlanItem.getPurchaseNoticeInfo() != null) {
                noticeInfoGuid = purchasePlanItem.getPurchaseNoticeInfo().getNoticeInfoGuid();
                lotGuid = purchasePlanItem.getPurchaseNoticeInfo().getLotGuid();
            }
            jdbcTemplate.update(
                sql,
                purchasePlanItem.getGuid(),
                noticeInfoGuid,
                lotGuid,
                purchasePlanItem.getOkato(),
                repositoryUtils.removeExtraSpaces(purchasePlanItem.getRegion()),
                repositoryUtils.convertBoolean(purchasePlanItem.isIsGeneralAddress()),
                purchasePlanItem.getPurchaseMethodCode(),
                repositoryUtils.removeExtraSpaces(purchasePlanItem.getPurchaseMethodName()),
                repositoryUtils.convertBoolean(purchasePlanItem.isIsElectronic()),
                repositoryUtils.convertBoolean(purchasePlanItem.isPlannedAfterSecondYear()),
                repositoryUtils.convertBoolean(purchasePlanItem.isIsPurchaseIgnored()),
                purchasePlanItem.getPurchasePeriodYear()
            );
        } catch (Exception e) {
            logger.error("Internal database error", e);
        }
    }

    private void updatePlanItem(PurchasePlanDataItemType purchasePlanItem) {
        try {
            jdbcTemplate.update(
                UPDATE,
                purchasePlanItem.getOrdinalNumber(),
                repositoryUtils.removeExtraSpaces(purchasePlanItem.getContractSubject()),
                repositoryUtils.removeExtraSpaces(purchasePlanItem.getMinimumRequirements()),
                repositoryUtils.convertFromXMLGregorianCalendarToLocalDate(purchasePlanItem.getContractEndDate()),
                repositoryUtils.removeExtraSpaces(purchasePlanItem.getAdditionalInfo()),
                repositoryUtils.removeExtraSpaces(purchasePlanItem.getModificationDescription()),
                repositoryUtils.removeExtraSpaces(getPlanItemStatus(purchasePlanItem.getStatus())),
                repositoryUtils.convertBoolean(purchasePlanItem.isIsPurchasePlaced()),
                repositoryUtils.convertBoolean(purchasePlanItem.isChangedGWSAndDates()),
                repositoryUtils.convertBoolean(purchasePlanItem.isChangedNMSKMoreTenPercent()),
                repositoryUtils.convertBoolean(purchasePlanItem.isOtherChanges()),
                repositoryUtils.removeExtraSpaces(getPlanItemCheckResult(purchasePlanItem.getCheckResult())),
                repositoryUtils.removeExtraSpaces(purchasePlanItem.getErrorMessages()),
                repositoryUtils.removeExtraSpaces(getCancellationReason(purchasePlanItem.getCancellationReason())),
                repositoryUtils.convertBoolean(purchasePlanItem.isLongTerm()),
                repositoryUtils.convertBoolean(purchasePlanItem.isShared()),
                repositoryUtils.removeExtraSpaces(getInitialPositionGuid(purchasePlanItem.getInitialPositionData())),
                repositoryUtils.removeExtraSpaces(getInitialPlanGuid(purchasePlanItem.getInitialPositionData())),
                purchasePlanItem.getMaximumContractPrice(),
                repositoryUtils.getCurrencyCode(purchasePlanItem.getCurrency()),
                purchasePlanItem.getExchangeRate(),
                repositoryUtils.convertFromXMLGregorianCalendarToLocalDate(purchasePlanItem.getExchangeRateDate()),
                purchasePlanItem.getMaximumContractPriceRub(),
                repositoryUtils.removeExtraSpaces(purchasePlanItem.getOrderPricing()),
                repositoryUtils.convertBoolean(purchasePlanItem.isInnovationEquivalent()),
                purchasePlanItem.getPurchaseCategory(),
                repositoryUtils.convertBoolean(false),
                repositoryUtils.removeExtraSpaces(purchasePlanItem.getGuid())
            );
            updatePurchasePlanItem(purchasePlanItem);
        } catch (Exception e) {
            logger.error("Internal database error", e);
        }
    }

    private void updatePlanItem(InnovationPlanDataItemType innovationPlanItem) {
        try {
            jdbcTemplate.update(
                UPDATE,
                innovationPlanItem.getOrdinalNumber(),
                repositoryUtils.removeExtraSpaces(innovationPlanItem.getContractSubject()),
                repositoryUtils.removeExtraSpaces(innovationPlanItem.getMinimumRequirements()),
                repositoryUtils.convertFromXMLGregorianCalendarToLocalDate(innovationPlanItem.getContractEndDate()),
                repositoryUtils.removeExtraSpaces(innovationPlanItem.getAdditionalInfo()),
                repositoryUtils.removeExtraSpaces(innovationPlanItem.getModificationDescription()),
                repositoryUtils.removeExtraSpaces(getPlanItemStatus(innovationPlanItem.getStatus())),
                repositoryUtils.convertBoolean(innovationPlanItem.isIsPurchasePlaced()),
                repositoryUtils.convertBoolean(innovationPlanItem.isChangedGWSAndDates()),
                repositoryUtils.convertBoolean(innovationPlanItem.isChangedNMSKMoreTenPercent()),
                repositoryUtils.convertBoolean(innovationPlanItem.isOtherChanges()),
                repositoryUtils.removeExtraSpaces(getPlanItemCheckResult(innovationPlanItem.getCheckResult())),
                repositoryUtils.removeExtraSpaces(innovationPlanItem.getErrorMessages()),
                repositoryUtils.removeExtraSpaces(getCancellationReason(innovationPlanItem.getCancellationReason())),
                repositoryUtils.convertBoolean(innovationPlanItem.isLongTerm()),
                repositoryUtils.convertBoolean(innovationPlanItem.isShared()),
                repositoryUtils.removeExtraSpaces(getInitialPositionGuid(innovationPlanItem.getInitialPositionData())),
                repositoryUtils.removeExtraSpaces(getInitialPlanGuid(innovationPlanItem.getInitialPositionData())),
                innovationPlanItem.getMaximumContractPrice(),
                repositoryUtils.getCurrencyCode(innovationPlanItem.getCurrency()),
                innovationPlanItem.getExchangeRate(),
                repositoryUtils.convertFromXMLGregorianCalendarToLocalDate(innovationPlanItem.getExchangeRateDate()),
                innovationPlanItem.getMaximumContractPriceRub(),
                repositoryUtils.removeExtraSpaces(innovationPlanItem.getOrderPricing()),
                repositoryUtils.convertBoolean(innovationPlanItem.isInnovationEquivalent()),
                innovationPlanItem.getPurchaseCategory(),
                repositoryUtils.convertBoolean(false),
                repositoryUtils.removeExtraSpaces(innovationPlanItem.getGuid())
            );
            updateInnovationPlanItem(innovationPlanItem);
        } catch (Exception e) {
            logger.error("Internal database error", e);
        }
    }

    private void updateInnovationPlanItem(InnovationPlanDataItemType innovationPlanItem) {
        String sql = "UPDATE innovation_plan_item SET ignored_purchase = ?, purchase_period_year = ? WHERE guid = ?";
        try {
            jdbcTemplate.update(
                sql,
                repositoryUtils.convertBoolean(innovationPlanItem.isIgnoredPurchase()),
                innovationPlanItem.getPurchasePeriodYear(),
                innovationPlanItem.getGuid()
            );
        } catch (Exception e) {
            logger.error("Internal database error", e);
        }
    }

    private void updatePurchasePlanItem(PurchasePlanDataItemType purchasePlanItem) {
        String sql = "UPDATE purchase_plan_item SET notice_info_guid = ?, lot_guid = ?, okato = ?, region = ?," +
            "is_general_address = ?, purchase_method_code = ?, purchase_method_name = ?, is_electronic = ?," +
            "planned_after_second_year = ?, is_purchase_ignored = ?, purchase_period_year = ? WHERE guid = ?";
        try {
            String noticeInfoGuid = null;
            String lotGuid = null;
            if (purchasePlanItem.getPurchaseNoticeInfo() != null) {
                noticeInfoGuid = purchasePlanItem.getPurchaseNoticeInfo().getNoticeInfoGuid();
                lotGuid = purchasePlanItem.getPurchaseNoticeInfo().getLotGuid();
            }
            jdbcTemplate.update(
                sql,
                noticeInfoGuid,
                lotGuid,
                purchasePlanItem.getOkato(),
                repositoryUtils.removeExtraSpaces(purchasePlanItem.getRegion()),
                repositoryUtils.convertBoolean(purchasePlanItem.isIsGeneralAddress()),
                purchasePlanItem.getPurchaseMethodCode(),
                repositoryUtils.removeExtraSpaces(purchasePlanItem.getPurchaseMethodName()),
                repositoryUtils.convertBoolean(purchasePlanItem.isIsElectronic()),
                repositoryUtils.convertBoolean(purchasePlanItem.isPlannedAfterSecondYear()),
                repositoryUtils.convertBoolean(purchasePlanItem.isIsPurchaseIgnored()),
                purchasePlanItem.getPurchasePeriodYear(),
                purchasePlanItem.getGuid()
            );
        } catch (Exception e) {
            logger.error("Internal database error", e);
        }
    }

    private void updateSmb(String guid, Boolean isSmb) {
        try {
            String sql = "UPDATE zakupki.plan_item SET is_smb = ? WHERE guid = ? ";
            jdbcTemplate.update(sql, repositoryUtils.convertBoolean(isSmb), guid);
        } catch (Exception e) {
            logger.error("Internal database error", e);
        }
    }

    private boolean checkPlanItem(String guid) {
        String sql = "SELECT guid FROM plan_item WHERE guid = ?";
        List<String> result = jdbcTemplate.query(sql, new Object[]{guid}, (rs, rowNum) -> rs.getString("guid"));
        return result.size() != 0;
    }

    private String getPlanItemStatus(PurchasePlanItemStatusType status) {
        if (status == null) return null;
        else return status.value();
    }

    private String getPlanItemCheckResult(PurchasePlanItemCheckResultType checkResult) {
        if (checkResult == null) return null;
        else return checkResult.value();
    }

    private String getInitialPositionGuid(BasePlanDataItemType.InitialPositionData positionData) {
        if (positionData == null) return null;
        else return positionData.getInitialPositionGuid();
    }

    private String getInitialPlanGuid(BasePlanDataItemType.InitialPositionData positionData) {
        if (positionData == null) return null;
        else return positionData.getInitialPlanGuid();
    }

    private String getCancellationReason(CancellationReasonType cancellationReason) {
        if (cancellationReason == null) return null;
        else return cancellationReason.value();
    }
}
