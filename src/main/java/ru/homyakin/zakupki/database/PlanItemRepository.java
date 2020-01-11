package ru.homyakin.zakupki.database;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.homyakin.zakupki.documentsinfo._223fz.purchaseplan.InnovationPlanDataItemRowType;
import ru.homyakin.zakupki.documentsinfo._223fz.purchaseplan.InnovationPlanDataItemType;
import ru.homyakin.zakupki.documentsinfo._223fz.purchaseplan.PurchasePlanDataItemRowType;
import ru.homyakin.zakupki.documentsinfo._223fz.purchaseplan.PurchasePlanDataItemType;

import javax.sql.DataSource;

@Component
public class PlanItemRepository {
    private static final Logger logger = LoggerFactory.getLogger(CustomerRepository.class);
    private final JdbcTemplate jdbcTemplate;
    private final LongTermVolumesRepository longTermVolumesRepository;
    private final PlanItemRowRepository planItemRowRepository;
    private final String INSERT_PLAN_ITEM = "INSERT INTO zakupki.plan_item (guid, purchase_plan_guid, ordinal_number," +
        "contract_subject, plan_item_customer_inn, minimum_requirements, contract_end_date," +
        "additional_info, modification_description, status_code, is_purchase_placed, changed_gws_and_dates," +
        "changed_nmsk_more_ten_percent, other_changes, check_result, error_messages, cancellation_reason," +
        "long_term, shared, initial_position_guid, initial_plan_guid, maximum_contract_price," +
        "currency_code, exchange_rate, exchange_rate_date, maximum_contract_price_rub, order_pricing," +
        "innovation_equivalent, purchase_category_code, is_smb, is_innovation)" +
        "VALUES" +
        "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    public PlanItemRepository(DataSource dataSource,
                              LongTermVolumesRepository longTermVolumesRepository,
                              PlanItemRowRepository planItemRowRepository) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        this.longTermVolumesRepository = longTermVolumesRepository;
        this.planItemRowRepository = planItemRowRepository;
    }

    public void insert(PurchasePlanDataItemType purchasePlanItem, Boolean isSmb, String planGuid) {
        try {
            jdbcTemplate.update(INSERT_PLAN_ITEM,
                purchasePlanItem.getGuid(),
                planGuid,
                purchasePlanItem.getOrdinalNumber(),
                purchasePlanItem.getContractSubject(),
                purchasePlanItem.getPlanItemCustomer().getMainInfo().getInn(),
                purchasePlanItem.getMinimumRequirements(),
                purchasePlanItem.getContractEndDate(),
                purchasePlanItem.getAdditionalInfo(),
                purchasePlanItem.getModificationDescription(),
                purchasePlanItem.getStatus().value(),
                RepositoryService.convertBoolean(purchasePlanItem.isIsPurchasePlaced()),
                RepositoryService.convertBoolean(purchasePlanItem.isChangedGWSAndDates()),
                RepositoryService.convertBoolean(purchasePlanItem.isChangedNMSKMoreTenPercent()),
                RepositoryService.convertBoolean(purchasePlanItem.isOtherChanges()),
                purchasePlanItem.getCheckResult().value(),
                purchasePlanItem.getErrorMessages(),
                purchasePlanItem.getCancellationReason(),
                RepositoryService.convertBoolean(purchasePlanItem.isLongTerm()),
                RepositoryService.convertBoolean(purchasePlanItem.isShared()),
                purchasePlanItem.getInitialPositionData().getInitialPositionGuid(),
                purchasePlanItem.getInitialPositionData().getInitialPlanGuid(),
                purchasePlanItem.getMaximumContractPrice(),
                RepositoryService.getCurrencyCode(purchasePlanItem.getCurrency()),
                purchasePlanItem.getExchangeRate(),
                purchasePlanItem.getExchangeRateDate(),
                purchasePlanItem.getMaximumContractPriceRub(),
                purchasePlanItem.getOrderPricing(),
                RepositoryService.convertBoolean(purchasePlanItem.isInnovationEquivalent()),
                purchasePlanItem.getPurchaseCategory(),
                RepositoryService.convertBoolean(isSmb),
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
            for(PurchasePlanDataItemRowType i: purchasePlanItem.getPurchasePlanDataItemRows().getPurchasePlanRowItem()) {
                planItemRowRepository.insert(i, purchasePlanItem.getGuid());
            }
        } catch (Exception e) {
            logger.error("Eternal error", e);
        }

    }

    public void insert(InnovationPlanDataItemType innovationPlanItem, Boolean isSmb, String planGuid) {
        try {
            jdbcTemplate.update(INSERT_PLAN_ITEM,
                innovationPlanItem.getGuid(),
                planGuid,
                innovationPlanItem.getOrdinalNumber(),
                innovationPlanItem.getContractSubject(),
                innovationPlanItem.getPlanItemCustomer().getMainInfo().getInn(),
                innovationPlanItem.getMinimumRequirements(),
                innovationPlanItem.getContractEndDate(),
                innovationPlanItem.getAdditionalInfo(),
                innovationPlanItem.getModificationDescription(),
                innovationPlanItem.getStatus().value(),
                RepositoryService.convertBoolean(innovationPlanItem.isIsPurchasePlaced()),
                RepositoryService.convertBoolean(innovationPlanItem.isChangedGWSAndDates()),
                RepositoryService.convertBoolean(innovationPlanItem.isChangedNMSKMoreTenPercent()),
                RepositoryService.convertBoolean(innovationPlanItem.isOtherChanges()),
                innovationPlanItem.getCheckResult().value(),
                innovationPlanItem.getErrorMessages(),
                innovationPlanItem.getCancellationReason(),
                RepositoryService.convertBoolean(innovationPlanItem.isLongTerm()),
                RepositoryService.convertBoolean(innovationPlanItem.isShared()),
                innovationPlanItem.getInitialPositionData().getInitialPositionGuid(),
                innovationPlanItem.getInitialPositionData().getInitialPlanGuid(),
                innovationPlanItem.getMaximumContractPrice(),
                RepositoryService.getCurrencyCode(innovationPlanItem.getCurrency()),
                innovationPlanItem.getExchangeRate(),
                innovationPlanItem.getExchangeRateDate(),
                innovationPlanItem.getMaximumContractPriceRub(),
                innovationPlanItem.getOrderPricing(),
                RepositoryService.convertBoolean(innovationPlanItem.isInnovationEquivalent()),
                innovationPlanItem.getPurchaseCategory(),
                RepositoryService.convertBoolean(isSmb),
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
            for(InnovationPlanDataItemRowType i: innovationPlanItem.getInnovationPlanDataItemRows().getInnovationPlanRowItem()) {
                planItemRowRepository.insert(i, innovationPlanItem.getGuid());
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
            jdbcTemplate.update(sql,
                purchasePlanItem.getPurchaseNoticeInfo().getNoticeInfoGuid(),
                purchasePlanItem.getPurchaseNoticeInfo().getLotGuid(),
                purchasePlanItem.getOkato(),
                purchasePlanItem.getRegion(),
                RepositoryService.convertBoolean(purchasePlanItem.isIsGeneralAddress()),
                purchasePlanItem.getPurchaseMethodCode(),
                purchasePlanItem.getPurchaseMethodName(),
                RepositoryService.convertBoolean(purchasePlanItem.isIsElectronic()),
                RepositoryService.convertBoolean(purchasePlanItem.isPlannedAfterSecondYear()),
                RepositoryService.convertBoolean(purchasePlanItem.isIsPurchaseIgnored()),
                purchasePlanItem.getPurchasePeriodYear()
            );
        } catch (Exception e) {
            logger.error("Eternal error", e);
        }
    }
}
