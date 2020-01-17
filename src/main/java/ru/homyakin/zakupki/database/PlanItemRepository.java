package ru.homyakin.zakupki.database;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.homyakin.zakupki.documentsinfo._223fz.purchaseplan.*;
import ru.homyakin.zakupki.documentsinfo._223fz.types.PurchasePlanItemStatusType;

import javax.sql.DataSource;
import java.util.List;

@Component
public class PlanItemRepository {
    private static final Logger logger = LoggerFactory.getLogger(PlanItemRepository.class);
    private final JdbcTemplate jdbcTemplate;
    private final LongTermVolumesRepository longTermVolumesRepository;
    private final PlanItemRowRepository planItemRowRepository;
    private final PurchaseCategoryRepository purchaseCategoryRepository;
    private final ClassifierRepository classifierRepository;
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
        PurchaseCategoryRepository purchaseCategoryRepository,
        ClassifierRepository classifierRepository
    ) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        this.longTermVolumesRepository = longTermVolumesRepository;
        this.planItemRowRepository = planItemRowRepository;
        this.purchaseCategoryRepository = purchaseCategoryRepository;
        this.classifierRepository = classifierRepository;
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
                        for (PurchasePlanDataItemRowType i : purchasePlanItem.getPurchasePlanDataItemRows().getPurchasePlanRowItem()) {
                            planItemRowRepository.update(i, purchasePlanItem.getGuid());
                        }
                    }
                    return;
                }
            }
            jdbcTemplate.update(INSERT_PLAN_ITEM,
                purchasePlanItem.getGuid(),
                planGuid,
                purchasePlanItem.getOrdinalNumber(),
                RepositoryService.removeExtraSpaces(purchasePlanItem.getContractSubject()),
                RepositoryService.removeExtraSpaces(purchasePlanItem.getPlanItemCustomer().getMainInfo().getInn()), //required field
                RepositoryService.removeExtraSpaces(purchasePlanItem.getMinimumRequirements()),
                RepositoryService.convertFromXMLGregorianCalendarToLocalDate(purchasePlanItem.getContractEndDate()),
                RepositoryService.removeExtraSpaces(purchasePlanItem.getAdditionalInfo()),
                RepositoryService.removeExtraSpaces(purchasePlanItem.getModificationDescription()),
                RepositoryService.removeExtraSpaces(getPlanItemStatus(purchasePlanItem.getStatus())),
                RepositoryService.convertBoolean(purchasePlanItem.isIsPurchasePlaced()),
                RepositoryService.convertBoolean(purchasePlanItem.isChangedGWSAndDates()),
                RepositoryService.convertBoolean(purchasePlanItem.isChangedNMSKMoreTenPercent()),
                RepositoryService.convertBoolean(purchasePlanItem.isOtherChanges()),
                RepositoryService.removeExtraSpaces(getPlanItemCheckResult(purchasePlanItem.getCheckResult())),
                RepositoryService.removeExtraSpaces(purchasePlanItem.getErrorMessages()),
                RepositoryService.removeExtraSpaces(getCancellationReason(purchasePlanItem.getCancellationReason())),
                RepositoryService.convertBoolean(purchasePlanItem.isLongTerm()),
                RepositoryService.convertBoolean(purchasePlanItem.isShared()),
                getInitialPositionGuid(purchasePlanItem.getInitialPositionData()),
                getInitialPlanGuid(purchasePlanItem.getInitialPositionData()),
                purchasePlanItem.getMaximumContractPrice(),
                RepositoryService.getCurrencyCode(purchasePlanItem.getCurrency()),
                purchasePlanItem.getExchangeRate(),
                RepositoryService.convertFromXMLGregorianCalendarToLocalDate(purchasePlanItem.getExchangeRateDate()),
                purchasePlanItem.getMaximumContractPriceRub(),
                RepositoryService.removeExtraSpaces(purchasePlanItem.getOrderPricing()),
                RepositoryService.convertBoolean(purchasePlanItem.isInnovationEquivalent()),
                purchaseCategoryRepository.getCategoryCode(purchasePlanItem.getPurchaseCategory()),
                RepositoryService.convertBoolean(false),
                RepositoryService.convertBoolean(false)
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
                for (PurchasePlanDataItemRowType i : purchasePlanItem.getPurchasePlanDataItemRows().getPurchasePlanRowItem()) {
                    planItemRowRepository.insert(i, purchasePlanItem.getGuid());
                }
            }
        } catch (Exception e) {
            logger.error("Eternal error", e);
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
                        for (InnovationPlanDataItemRowType i : innovationPlanItem.getInnovationPlanDataItemRows().getInnovationPlanRowItem()) {
                            planItemRowRepository.update(i, innovationPlanItem.getGuid());
                        }
                    }
                    return;
                }
            }
            jdbcTemplate.update(INSERT_PLAN_ITEM,
                innovationPlanItem.getGuid(),
                planGuid,
                innovationPlanItem.getOrdinalNumber(),
                RepositoryService.removeExtraSpaces(innovationPlanItem.getContractSubject()),
                RepositoryService.removeExtraSpaces(innovationPlanItem.getPlanItemCustomer().getMainInfo().getInn()), //required field
                RepositoryService.removeExtraSpaces(innovationPlanItem.getMinimumRequirements()),
                RepositoryService.convertFromXMLGregorianCalendarToLocalDate(innovationPlanItem.getContractEndDate()),
                RepositoryService.removeExtraSpaces(innovationPlanItem.getAdditionalInfo()),
                RepositoryService.removeExtraSpaces(innovationPlanItem.getModificationDescription()),
                RepositoryService.removeExtraSpaces(getPlanItemStatus(innovationPlanItem.getStatus())),
                RepositoryService.convertBoolean(innovationPlanItem.isIsPurchasePlaced()),
                RepositoryService.convertBoolean(innovationPlanItem.isChangedGWSAndDates()),
                RepositoryService.convertBoolean(innovationPlanItem.isChangedNMSKMoreTenPercent()),
                RepositoryService.convertBoolean(innovationPlanItem.isOtherChanges()),
                RepositoryService.removeExtraSpaces(getPlanItemCheckResult(innovationPlanItem.getCheckResult())),
                RepositoryService.removeExtraSpaces(innovationPlanItem.getErrorMessages()),
                RepositoryService.removeExtraSpaces(getCancellationReason(innovationPlanItem.getCancellationReason())),
                RepositoryService.convertBoolean(innovationPlanItem.isLongTerm()),
                RepositoryService.convertBoolean(innovationPlanItem.isShared()),
                getInitialPositionGuid(innovationPlanItem.getInitialPositionData()),
                getInitialPlanGuid(innovationPlanItem.getInitialPositionData()),
                innovationPlanItem.getMaximumContractPrice(),
                RepositoryService.getCurrencyCode(innovationPlanItem.getCurrency()),
                innovationPlanItem.getExchangeRate(),
                RepositoryService.convertFromXMLGregorianCalendarToLocalDate(innovationPlanItem.getExchangeRateDate()),
                innovationPlanItem.getMaximumContractPriceRub(),
                RepositoryService.removeExtraSpaces(innovationPlanItem.getOrderPricing()),
                RepositoryService.convertBoolean(innovationPlanItem.isInnovationEquivalent()),
                purchaseCategoryRepository.getCategoryCode(innovationPlanItem.getPurchaseCategory()),
                RepositoryService.convertBoolean(false),
                RepositoryService.convertBoolean(true)
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
                for (InnovationPlanDataItemRowType i : innovationPlanItem.getInnovationPlanDataItemRows().getInnovationPlanRowItem()) {
                    planItemRowRepository.insert(i, innovationPlanItem.getGuid());
                }
            }
        } catch (Exception e) {
            logger.error("Eternal error", e);
        }
    }

    private void insertToInnovationPlanItem(InnovationPlanDataItemType innovationPlanItem) {
        String sql = "INSERT INTO zakupki.innovation_plan_item (guid, ignored_purchase, purchase_period_year)" +
            "VALUES" +
            "(?, ?, ?)";
        try {
            jdbcTemplate.update(sql,
                innovationPlanItem.getGuid(),
                RepositoryService.convertBoolean(innovationPlanItem.isIgnoredPurchase()),
                innovationPlanItem.getPurchasePeriodYear()
            );
        } catch (Exception e) {
            logger.error("Eternal error e");
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
            jdbcTemplate.update(sql,
                purchasePlanItem.getGuid(),
                noticeInfoGuid,
                lotGuid,
                classifierRepository.getOkatoCode(purchasePlanItem.getOkato()),
                RepositoryService.removeExtraSpaces(purchasePlanItem.getRegion()),
                RepositoryService.convertBoolean(purchasePlanItem.isIsGeneralAddress()),
                purchasePlanItem.getPurchaseMethodCode(),
                RepositoryService.removeExtraSpaces(purchasePlanItem.getPurchaseMethodName()),
                RepositoryService.convertBoolean(purchasePlanItem.isIsElectronic()),
                RepositoryService.convertBoolean(purchasePlanItem.isPlannedAfterSecondYear()),
                RepositoryService.convertBoolean(purchasePlanItem.isIsPurchaseIgnored()),
                purchasePlanItem.getPurchasePeriodYear()
            );
        } catch (Exception e) {
            logger.error("Eternal error", e);
        }
    }

    private void updatePlanItem(PurchasePlanDataItemType purchasePlanItem) {
        try {
            jdbcTemplate.update(UPDATE,
                purchasePlanItem.getOrdinalNumber(),
                RepositoryService.removeExtraSpaces(purchasePlanItem.getContractSubject()),
                RepositoryService.removeExtraSpaces(purchasePlanItem.getMinimumRequirements()),
                RepositoryService.convertFromXMLGregorianCalendarToLocalDate(purchasePlanItem.getContractEndDate()),
                RepositoryService.removeExtraSpaces(purchasePlanItem.getAdditionalInfo()),
                RepositoryService.removeExtraSpaces(purchasePlanItem.getModificationDescription()),
                RepositoryService.removeExtraSpaces(getPlanItemStatus(purchasePlanItem.getStatus())),
                RepositoryService.convertBoolean(purchasePlanItem.isIsPurchasePlaced()),
                RepositoryService.convertBoolean(purchasePlanItem.isChangedGWSAndDates()),
                RepositoryService.convertBoolean(purchasePlanItem.isChangedNMSKMoreTenPercent()),
                RepositoryService.convertBoolean(purchasePlanItem.isOtherChanges()),
                RepositoryService.removeExtraSpaces(getPlanItemCheckResult(purchasePlanItem.getCheckResult())),
                RepositoryService.removeExtraSpaces(purchasePlanItem.getErrorMessages()),
                RepositoryService.removeExtraSpaces(getCancellationReason(purchasePlanItem.getCancellationReason())),
                RepositoryService.convertBoolean(purchasePlanItem.isLongTerm()),
                RepositoryService.convertBoolean(purchasePlanItem.isShared()),
                RepositoryService.removeExtraSpaces(getInitialPositionGuid(purchasePlanItem.getInitialPositionData())),
                RepositoryService.removeExtraSpaces(getInitialPlanGuid(purchasePlanItem.getInitialPositionData())),
                purchasePlanItem.getMaximumContractPrice(),
                RepositoryService.getCurrencyCode(purchasePlanItem.getCurrency()),
                purchasePlanItem.getExchangeRate(),
                RepositoryService.convertFromXMLGregorianCalendarToLocalDate(purchasePlanItem.getExchangeRateDate()),
                purchasePlanItem.getMaximumContractPriceRub(),
                RepositoryService.removeExtraSpaces(purchasePlanItem.getOrderPricing()),
                RepositoryService.convertBoolean(purchasePlanItem.isInnovationEquivalent()),
                purchasePlanItem.getPurchaseCategory(),
                RepositoryService.convertBoolean(false),
                RepositoryService.removeExtraSpaces(purchasePlanItem.getGuid())
            );
            updatePurchasePlanItem(purchasePlanItem);
        } catch (Exception e) {
            logger.error("Eternal error", e);
        }
    }

    private void updatePlanItem(InnovationPlanDataItemType innovationPlanItem) {
        try {
            jdbcTemplate.update(UPDATE,
                innovationPlanItem.getOrdinalNumber(),
                RepositoryService.removeExtraSpaces(innovationPlanItem.getContractSubject()),
                RepositoryService.removeExtraSpaces(innovationPlanItem.getMinimumRequirements()),
                RepositoryService.convertFromXMLGregorianCalendarToLocalDate(innovationPlanItem.getContractEndDate()),
                RepositoryService.removeExtraSpaces(innovationPlanItem.getAdditionalInfo()),
                RepositoryService.removeExtraSpaces(innovationPlanItem.getModificationDescription()),
                RepositoryService.removeExtraSpaces(getPlanItemStatus(innovationPlanItem.getStatus())),
                RepositoryService.convertBoolean(innovationPlanItem.isIsPurchasePlaced()),
                RepositoryService.convertBoolean(innovationPlanItem.isChangedGWSAndDates()),
                RepositoryService.convertBoolean(innovationPlanItem.isChangedNMSKMoreTenPercent()),
                RepositoryService.convertBoolean(innovationPlanItem.isOtherChanges()),
                RepositoryService.removeExtraSpaces(getPlanItemCheckResult(innovationPlanItem.getCheckResult())),
                RepositoryService.removeExtraSpaces(innovationPlanItem.getErrorMessages()),
                RepositoryService.removeExtraSpaces(getCancellationReason(innovationPlanItem.getCancellationReason())),
                RepositoryService.convertBoolean(innovationPlanItem.isLongTerm()),
                RepositoryService.convertBoolean(innovationPlanItem.isShared()),
                RepositoryService.removeExtraSpaces(getInitialPositionGuid(innovationPlanItem.getInitialPositionData())),
                RepositoryService.removeExtraSpaces(getInitialPlanGuid(innovationPlanItem.getInitialPositionData())),
                innovationPlanItem.getMaximumContractPrice(),
                RepositoryService.getCurrencyCode(innovationPlanItem.getCurrency()),
                innovationPlanItem.getExchangeRate(),
                RepositoryService.convertFromXMLGregorianCalendarToLocalDate(innovationPlanItem.getExchangeRateDate()),
                innovationPlanItem.getMaximumContractPriceRub(),
                RepositoryService.removeExtraSpaces(innovationPlanItem.getOrderPricing()),
                RepositoryService.convertBoolean(innovationPlanItem.isInnovationEquivalent()),
                innovationPlanItem.getPurchaseCategory(),
                RepositoryService.convertBoolean(false),
                RepositoryService.removeExtraSpaces(innovationPlanItem.getGuid())
            );
            updateInnovationPlanItem(innovationPlanItem);
        } catch (Exception e) {
            logger.error("Eternal error", e);
        }
    }

    private void updateInnovationPlanItem(InnovationPlanDataItemType innovationPlanItem) {
        String sql = "UPDATE innovation_plan_item SET ignored_purchase = ?, purchase_period_year = ? WHERE guid = ?";
        try {
            jdbcTemplate.update(sql,
                RepositoryService.convertBoolean(innovationPlanItem.isIgnoredPurchase()),
                innovationPlanItem.getPurchasePeriodYear(),
                innovationPlanItem.getGuid()
            );
        } catch (Exception e) {
            logger.error("Eternal error", e);
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
            jdbcTemplate.update(sql,
                noticeInfoGuid,
                lotGuid,
                RepositoryService.removeExtraSpaces(purchasePlanItem.getOkato()),
                RepositoryService.removeExtraSpaces(purchasePlanItem.getRegion()),
                RepositoryService.convertBoolean(purchasePlanItem.isIsGeneralAddress()),
                purchasePlanItem.getPurchaseMethodCode(),
                RepositoryService.removeExtraSpaces(purchasePlanItem.getPurchaseMethodName()),
                RepositoryService.convertBoolean(purchasePlanItem.isIsElectronic()),
                RepositoryService.convertBoolean(purchasePlanItem.isPlannedAfterSecondYear()),
                RepositoryService.convertBoolean(purchasePlanItem.isIsPurchaseIgnored()),
                purchasePlanItem.getPurchasePeriodYear(),
                purchasePlanItem.getGuid()
            );
        } catch (Exception e) {
            logger.error("Eternal error", e);
        }
    }

    private void updateSmb(String guid, Boolean isSmb) {
        try {
            String sql = "UPDATE zakupki.plan_item SET is_smb = ? WHERE guid = ? ";
            jdbcTemplate.update(sql, RepositoryService.convertBoolean(isSmb), guid);
        } catch (Exception e) {
            logger.error("Eternal error", e);
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
