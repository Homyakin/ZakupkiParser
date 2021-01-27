package ru.homyakin.zakupki.database;

import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.homyakin.zakupki.models._223fz.types.LotType;
import ru.homyakin.zakupki.utils.RepositoryUtils;

@Component
public class PurchaseNoticeLotDataRepository {
    private static final Logger logger = LoggerFactory.getLogger(PurchaseNoticeLotDataRepository.class);
    private final JdbcTemplate jdbcTemplate;
    private final RepositoryUtils repositoryUtils;
    private final PurchaseNoticeLotItemRepository purchaseNoticeLotItemRepository;
    public PurchaseNoticeLotDataRepository(
        DataSource dataSource,
        RepositoryUtils repositoryUtils,
        PurchaseNoticeLotItemRepository purchaseNoticeLotItemRepository
    ) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.repositoryUtils = repositoryUtils;
        this.purchaseNoticeLotItemRepository = purchaseNoticeLotItemRepository;
    }

    public void insert(LotType.LotData data, String lotGuid) {
        if (data == null) return;
        String sql = "INSERT INTO zakupki.purchase_notice_lot_data (purchase_notice_lot_guid, subject, currency_code," +
            "exchange_rate, exchange_rate_date, initial_sum, starting_contract_price_rub, price_formula, commodity_price," +
            "commodity_price_rub, max_contract_price, max_contract_price_rub, initial_sum_info, order_pricing," +
            "delivery_state, delivery_region, delivery_region_okato, delivery_address, for_small_or_middle," +
            "exclude_purchase_from_plan, subcontractors_requirement, ignored_purchase, purchase_category_code," +
            "centralized, purchase_description, application_supply_needed, application_supply_summ, application_supply_currency_code," +
            "application_supply_extra, major_contract_conditions, antimonopoly_decision_taken)" +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            jdbcTemplate.update(
                sql,
                lotGuid,
                data.getSubject(),
                repositoryUtils.getCurrencyCode(data.getCurrency()),
                data.getExchangeInfo() != null ? data.getExchangeInfo().getExchangeRate() : null,
                data.getExchangeInfo() != null ? repositoryUtils.convertFromXMLGregorianCalendarToLocalDate(data.getExchangeInfo().getExchangeRateDate()) : null,
                data.getInitialSum(),
                data.getStartingContractPriceRub(),
                data.getPriceFormula(),
                data.getCommodityPrice(),
                data.getCommodityPriceRub(),
                data.getMaxContractPrice(),
                data.getMaxContractPriceRub(),
                data.getInitialSumInfo(),
                data.getOrderPricing(),
                data.getDeliveryPlace() != null ? data.getDeliveryPlace().getState() : null,
                data.getDeliveryPlace() != null ? data.getDeliveryPlace().getRegion() : null,
                data.getDeliveryPlace() != null ? data.getDeliveryPlace().getRegionOkato() : null,
                data.getDeliveryPlace() != null ? data.getDeliveryPlace().getAddress() : null,
                repositoryUtils.convertBoolean(data.isForSmallOrMiddle()),
                repositoryUtils.convertBoolean(data.isExcludePurchaseFromPlan()),
                repositoryUtils.convertBoolean(data.isSubcontractorsRequirement()),
                repositoryUtils.convertBoolean(data.isIgnoredPurchase()),
                data.getPurchaseCategory() != null ? data.getPurchaseCategory().getCode() : null,
                repositoryUtils.convertBoolean(data.isCentralized()),
                data.getPurchaseDescription(),
                repositoryUtils.convertBoolean(data.isApplicationSupplyNeeded()),
                data.getApplicationSupplySumm(),
                repositoryUtils.getCurrencyCode(data.getApplicationSupplyCurrency()),
                data.getApplicationSupplyExtra(),
                data.getMajorContractConditions(),
                repositoryUtils.convertBoolean(data.isAntimonopolyDecisionTaken())
            );
            for(var item: data.getLotItems().getLotItem()) {
                purchaseNoticeLotItemRepository.insert(item, lotGuid);
            }
        } catch (Exception e) {
            logger.error("Error during inserting lot data", e);
        }
    }
}
