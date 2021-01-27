package ru.homyakin.zakupki.database;

import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.homyakin.zakupki.models._223fz.types.LotItemType;
import ru.homyakin.zakupki.utils.RepositoryUtils;

@Component
public class JointCustomerLotItemRepository {
    private static final Logger logger = LoggerFactory.getLogger(JointCustomerLotItemRepository.class);
    private final JdbcTemplate jdbcTemplate;
    private final RepositoryUtils repositoryUtils;
    private final ClassifierService classifierService;

    public JointCustomerLotItemRepository(
        DataSource dataSource,
        RepositoryUtils repositoryUtils,
        ClassifierService classifierService
    ) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.repositoryUtils = repositoryUtils;
        this.classifierService = classifierService;
    }

    public void insert(LotItemType item, String lotGuid, String customerInn) {
        String sql = "INSERT INTO zakupki.joint_customer_lot_item (guid, lot_customer_data_joint_lot_data_purchase_notice_lot_guid," +
            "lot_customer_data_joint_lot_data_customer_inn, ordinalNumber, okdp_code, okdp_name, okpd2_code," +
            "okpd2_name, okved_code, okved_name, okved2_code, okved2_name, okei_code, okei_name, qty, additional_info," +
            "delivery_state, delivery_region, delivary_region_okato, delivery_address, commodity_item_price," +
            "commodity_item_price_rub)" +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            jdbcTemplate.update(
                sql,
                item.getGuid(),
                lotGuid,
                customerInn,
                item.getOrdinalNumber(),
                classifierService.getClassifierCode(item.getOkdp()),
                classifierService.getClassifierName(item.getOkdp()),
                classifierService.getClassifierCode(item.getOkpd2()),
                classifierService.getClassifierName(item.getOkpd2()),
                classifierService.getClassifierCode(item.getOkved()),
                classifierService.getClassifierName(item.getOkved()),
                classifierService.getClassifierCode(item.getOkved2()),
                classifierService.getClassifierName(item.getOkved2()),
                classifierService.getClassifierCode(item.getOkei()),
                classifierService.getClassifierName(item.getOkei()),
                item.getQty(),
                item.getAdditionalInfo(),
                item.getDeliveryPlace() != null ? item.getDeliveryPlace().getState() : null,
                item.getDeliveryPlace() != null ? item.getDeliveryPlace().getRegion() : null,
                item.getDeliveryPlace() != null ? item.getDeliveryPlace().getRegionOkato() : null,
                item.getDeliveryPlace() != null ? item.getDeliveryPlace().getAddress() : null,
                item.getCommodityItemPrice(),
                item.getCommodityItemPriceRub()
            );
        } catch (Exception e) {
            logger.error("Error during inserting joint customer lot item", e);
        }
    }
}
