package ru.homyakin.zakupki.database.purchase_notice;

import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.homyakin.zakupki.database.purchase_plan.JointCustomerLotItemRepository;
import ru.homyakin.zakupki.models._223fz.types.LotCustomerType;
import ru.homyakin.zakupki.utils.RepositoryUtils;

@Component
public class LotCustomerDataRepository {
    private static final Logger logger = LoggerFactory.getLogger(LotCustomerDataRepository.class);
    private final JdbcTemplate jdbcTemplate;
    private final RepositoryUtils repositoryUtils;
    private final JointCustomerLotItemRepository jointCustomerLotItemRepository;

    public LotCustomerDataRepository(
        DataSource dataSource,
        RepositoryUtils repositoryUtils,
        JointCustomerLotItemRepository jointCustomerLotItemRepository
    ) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.repositoryUtils = repositoryUtils;
        this.jointCustomerLotItemRepository = jointCustomerLotItemRepository;
    }

    public void insert(LotCustomerType lotCustomer, String lotGuid) {
        String sql = "INSERT INTO zakupki.lot_customer_data (joint_lot_data_purchase_notice_lot_guid," +
            "joint_lot_data_customer_inn, currency_code, exchange_rate, exchange_rate_date, initial_sum," +
            "starting_contract_price_rub, price_formula, price, price_rub, max_contract_price," +
            "max_contract_price_rub, exclude_purchase_from_plan, order_pricing, delivery_state," +
            "delivery_region, delivery_region_okato, delivery_address, not_in_law_223, plan_guid, position_number," +
            "lot_plan_position, position_guid, plan_registration_number)" +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        var data = lotCustomer.getLotCustomerData();
        var info = lotCustomer.getLotPlanInfo();
        try {
            jdbcTemplate.update(
                sql,
                lotGuid,
                lotCustomer.getCustomerInfo().getInn(),
                repositoryUtils.getCurrencyCode(data.getCurrency()),
                data.getExchangeInfo() != null ? data.getExchangeInfo().getExchangeRate() : null,
                data.getExchangeInfo() != null ? repositoryUtils.convertFromXMLGregorianCalendarToLocalDate(data.getExchangeInfo().getExchangeRateDate()) : null,
                data.getInitialSum(),
                data.getStartingContractPriceRub(),
                data.getPriceFormula(),
                data.getPrice(),
                data.getPriceRub(),
                data.getMaxContractPrice(),
                data.getMaxContractPriceRub(),
                repositoryUtils.convertBoolean(data.isExcludePurchaseFromPlan()),
                data.getOrderPricing(),
                data.getDeliveryPlace() != null ? data.getDeliveryPlace().getState() : null,
                data.getDeliveryPlace() != null ? data.getDeliveryPlace().getRegion() : null,
                data.getDeliveryPlace() != null ? data.getDeliveryPlace().getRegionOkato() : null,
                data.getDeliveryPlace() != null ? data.getDeliveryPlace().getAddress() : null,
                repositoryUtils.convertBoolean(data.isNotInLaw223()),
                info.getPlanGuid(),
                info.getPositionNumber(),
                info.getLotPlanPosition().value(),
                info.getPositionGuid(),
                info.getPlanRegistrationNumber()
            );
            for (var item : data.getLotItems().getLotItem()) {
                jointCustomerLotItemRepository.insert(item, lotGuid, lotCustomer.getCustomerInfo().getInn());
            }
        } catch (Exception e) {
            logger.error("Error during inserting lot customer data", e);
        }
    }
}
