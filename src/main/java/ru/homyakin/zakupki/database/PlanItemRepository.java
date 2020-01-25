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
    private final RepositoryService repositoryService;
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
        RepositoryService repositoryService
    ) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        this.longTermVolumesRepository = longTermVolumesRepository;
        this.planItemRowRepository = planItemRowRepository;
        this.repositoryService = repositoryService;
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
                repositoryService.removeExtraSpaces(purchasePlanItem.getContractSubject()),
                repositoryService.removeExtraSpaces(purchasePlanItem.getPlanItemCustomer().getMainInfo().getInn()), //required field
                repositoryService.removeExtraSpaces(purchasePlanItem.getMinimumRequirements()),
                repositoryService.convertFromXMLGregorianCalendarToLocalDate(purchasePlanItem.getContractEndDate()),
                repositoryService.removeExtraSpaces(purchasePlanItem.getAdditionalInfo()),
                repositoryService.removeExtraSpaces(purchasePlanItem.getModificationDescription()),
                repositoryService.removeExtraSpaces(getPlanItemStatus(purchasePlanItem.getStatus())),
                repositoryService.convertBoolean(purchasePlanItem.isIsPurchasePlaced()),
                repositoryService.convertBoolean(purchasePlanItem.isChangedGWSAndDates()),
                repositoryService.convertBoolean(purchasePlanItem.isChangedNMSKMoreTenPercent()),
                repositoryService.convertBoolean(purchasePlanItem.isOtherChanges()),
                repositoryService.removeExtraSpaces(getPlanItemCheckResult(purchasePlanItem.getCheckResult())),
                repositoryService.removeExtraSpaces(purchasePlanItem.getErrorMessages()),
                repositoryService.removeExtraSpaces(getCancellationReason(purchasePlanItem.getCancellationReason())),
                repositoryService.convertBoolean(purchasePlanItem.isLongTerm()),
                repositoryService.convertBoolean(purchasePlanItem.isShared()),
                getInitialPositionGuid(purchasePlanItem.getInitialPositionData()),
                getInitialPlanGuid(purchasePlanItem.getInitialPositionData()),
                purchasePlanItem.getMaximumContractPrice(),
                repositoryService.getCurrencyCode(purchasePlanItem.getCurrency()),
                purchasePlanItem.getExchangeRate(),
                repositoryService.convertFromXMLGregorianCalendarToLocalDate(purchasePlanItem.getExchangeRateDate()),
                purchasePlanItem.getMaximumContractPriceRub(),
                repositoryService.removeExtraSpaces(purchasePlanItem.getOrderPricing()),
                repositoryService.convertBoolean(purchasePlanItem.isInnovationEquivalent()),
                repositoryService.getCategoryCode(purchasePlanItem.getPurchaseCategory()),
                repositoryService.convertBoolean(false),
                repositoryService.convertBoolean(false)
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
                repositoryService.removeExtraSpaces(innovationPlanItem.getContractSubject()),
                repositoryService.removeExtraSpaces(innovationPlanItem.getPlanItemCustomer().getMainInfo().getInn()), //required field
                repositoryService.removeExtraSpaces(innovationPlanItem.getMinimumRequirements()),
                repositoryService.convertFromXMLGregorianCalendarToLocalDate(innovationPlanItem.getContractEndDate()),
                repositoryService.removeExtraSpaces(innovationPlanItem.getAdditionalInfo()),
                repositoryService.removeExtraSpaces(innovationPlanItem.getModificationDescription()),
                repositoryService.removeExtraSpaces(getPlanItemStatus(innovationPlanItem.getStatus())),
                repositoryService.convertBoolean(innovationPlanItem.isIsPurchasePlaced()),
                repositoryService.convertBoolean(innovationPlanItem.isChangedGWSAndDates()),
                repositoryService.convertBoolean(innovationPlanItem.isChangedNMSKMoreTenPercent()),
                repositoryService.convertBoolean(innovationPlanItem.isOtherChanges()),
                repositoryService.removeExtraSpaces(getPlanItemCheckResult(innovationPlanItem.getCheckResult())),
                repositoryService.removeExtraSpaces(innovationPlanItem.getErrorMessages()),
                repositoryService.removeExtraSpaces(getCancellationReason(innovationPlanItem.getCancellationReason())),
                repositoryService.convertBoolean(innovationPlanItem.isLongTerm()),
                repositoryService.convertBoolean(innovationPlanItem.isShared()),
                getInitialPositionGuid(innovationPlanItem.getInitialPositionData()),
                getInitialPlanGuid(innovationPlanItem.getInitialPositionData()),
                innovationPlanItem.getMaximumContractPrice(),
                repositoryService.getCurrencyCode(innovationPlanItem.getCurrency()),
                innovationPlanItem.getExchangeRate(),
                repositoryService.convertFromXMLGregorianCalendarToLocalDate(innovationPlanItem.getExchangeRateDate()),
                innovationPlanItem.getMaximumContractPriceRub(),
                repositoryService.removeExtraSpaces(innovationPlanItem.getOrderPricing()),
                repositoryService.convertBoolean(innovationPlanItem.isInnovationEquivalent()),
                repositoryService.getCategoryCode(innovationPlanItem.getPurchaseCategory()),
                repositoryService.convertBoolean(false),
                repositoryService.convertBoolean(true)
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
                repositoryService.convertBoolean(innovationPlanItem.isIgnoredPurchase()),
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
                repositoryService.getOkatoCode(purchasePlanItem.getOkato()),
                repositoryService.removeExtraSpaces(purchasePlanItem.getRegion()),
                repositoryService.convertBoolean(purchasePlanItem.isIsGeneralAddress()),
                purchasePlanItem.getPurchaseMethodCode(),
                repositoryService.removeExtraSpaces(purchasePlanItem.getPurchaseMethodName()),
                repositoryService.convertBoolean(purchasePlanItem.isIsElectronic()),
                repositoryService.convertBoolean(purchasePlanItem.isPlannedAfterSecondYear()),
                repositoryService.convertBoolean(purchasePlanItem.isIsPurchaseIgnored()),
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
                repositoryService.removeExtraSpaces(purchasePlanItem.getContractSubject()),
                repositoryService.removeExtraSpaces(purchasePlanItem.getMinimumRequirements()),
                repositoryService.convertFromXMLGregorianCalendarToLocalDate(purchasePlanItem.getContractEndDate()),
                repositoryService.removeExtraSpaces(purchasePlanItem.getAdditionalInfo()),
                repositoryService.removeExtraSpaces(purchasePlanItem.getModificationDescription()),
                repositoryService.removeExtraSpaces(getPlanItemStatus(purchasePlanItem.getStatus())),
                repositoryService.convertBoolean(purchasePlanItem.isIsPurchasePlaced()),
                repositoryService.convertBoolean(purchasePlanItem.isChangedGWSAndDates()),
                repositoryService.convertBoolean(purchasePlanItem.isChangedNMSKMoreTenPercent()),
                repositoryService.convertBoolean(purchasePlanItem.isOtherChanges()),
                repositoryService.removeExtraSpaces(getPlanItemCheckResult(purchasePlanItem.getCheckResult())),
                repositoryService.removeExtraSpaces(purchasePlanItem.getErrorMessages()),
                repositoryService.removeExtraSpaces(getCancellationReason(purchasePlanItem.getCancellationReason())),
                repositoryService.convertBoolean(purchasePlanItem.isLongTerm()),
                repositoryService.convertBoolean(purchasePlanItem.isShared()),
                repositoryService.removeExtraSpaces(getInitialPositionGuid(purchasePlanItem.getInitialPositionData())),
                repositoryService.removeExtraSpaces(getInitialPlanGuid(purchasePlanItem.getInitialPositionData())),
                purchasePlanItem.getMaximumContractPrice(),
                repositoryService.getCurrencyCode(purchasePlanItem.getCurrency()),
                purchasePlanItem.getExchangeRate(),
                repositoryService.convertFromXMLGregorianCalendarToLocalDate(purchasePlanItem.getExchangeRateDate()),
                purchasePlanItem.getMaximumContractPriceRub(),
                repositoryService.removeExtraSpaces(purchasePlanItem.getOrderPricing()),
                repositoryService.convertBoolean(purchasePlanItem.isInnovationEquivalent()),
                purchasePlanItem.getPurchaseCategory(),
                repositoryService.convertBoolean(false),
                repositoryService.removeExtraSpaces(purchasePlanItem.getGuid())
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
                repositoryService.removeExtraSpaces(innovationPlanItem.getContractSubject()),
                repositoryService.removeExtraSpaces(innovationPlanItem.getMinimumRequirements()),
                repositoryService.convertFromXMLGregorianCalendarToLocalDate(innovationPlanItem.getContractEndDate()),
                repositoryService.removeExtraSpaces(innovationPlanItem.getAdditionalInfo()),
                repositoryService.removeExtraSpaces(innovationPlanItem.getModificationDescription()),
                repositoryService.removeExtraSpaces(getPlanItemStatus(innovationPlanItem.getStatus())),
                repositoryService.convertBoolean(innovationPlanItem.isIsPurchasePlaced()),
                repositoryService.convertBoolean(innovationPlanItem.isChangedGWSAndDates()),
                repositoryService.convertBoolean(innovationPlanItem.isChangedNMSKMoreTenPercent()),
                repositoryService.convertBoolean(innovationPlanItem.isOtherChanges()),
                repositoryService.removeExtraSpaces(getPlanItemCheckResult(innovationPlanItem.getCheckResult())),
                repositoryService.removeExtraSpaces(innovationPlanItem.getErrorMessages()),
                repositoryService.removeExtraSpaces(getCancellationReason(innovationPlanItem.getCancellationReason())),
                repositoryService.convertBoolean(innovationPlanItem.isLongTerm()),
                repositoryService.convertBoolean(innovationPlanItem.isShared()),
                repositoryService.removeExtraSpaces(getInitialPositionGuid(innovationPlanItem.getInitialPositionData())),
                repositoryService.removeExtraSpaces(getInitialPlanGuid(innovationPlanItem.getInitialPositionData())),
                innovationPlanItem.getMaximumContractPrice(),
                repositoryService.getCurrencyCode(innovationPlanItem.getCurrency()),
                innovationPlanItem.getExchangeRate(),
                repositoryService.convertFromXMLGregorianCalendarToLocalDate(innovationPlanItem.getExchangeRateDate()),
                innovationPlanItem.getMaximumContractPriceRub(),
                repositoryService.removeExtraSpaces(innovationPlanItem.getOrderPricing()),
                repositoryService.convertBoolean(innovationPlanItem.isInnovationEquivalent()),
                innovationPlanItem.getPurchaseCategory(),
                repositoryService.convertBoolean(false),
                repositoryService.removeExtraSpaces(innovationPlanItem.getGuid())
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
                repositoryService.convertBoolean(innovationPlanItem.isIgnoredPurchase()),
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
                repositoryService.getOkatoCode(purchasePlanItem.getOkato()),
                repositoryService.removeExtraSpaces(purchasePlanItem.getRegion()),
                repositoryService.convertBoolean(purchasePlanItem.isIsGeneralAddress()),
                purchasePlanItem.getPurchaseMethodCode(),
                repositoryService.removeExtraSpaces(purchasePlanItem.getPurchaseMethodName()),
                repositoryService.convertBoolean(purchasePlanItem.isIsElectronic()),
                repositoryService.convertBoolean(purchasePlanItem.isPlannedAfterSecondYear()),
                repositoryService.convertBoolean(purchasePlanItem.isIsPurchaseIgnored()),
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
            jdbcTemplate.update(sql, repositoryService.convertBoolean(isSmb), guid);
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
