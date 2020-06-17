package ru.homyakin.zakupki.database;

import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.homyakin.zakupki.models._223fz.types.LotItemType;

@Component
public class PurchaseNoticeLotItemRepository {
    private static final Logger logger = LoggerFactory.getLogger(PurchaseNoticeLotItemRepository.class);
    private final JdbcTemplate jdbcTemplate;
    private final RepositoryService repositoryService;

    public PurchaseNoticeLotItemRepository(DataSource dataSource, RepositoryService repositoryService) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.repositoryService = repositoryService;
    }

    public void insert(LotItemType item, String lotGuid) {
        String sql = "INSERT INTO zakupki.purchase_notice_lot_item (guid, purchase_notice_lot_data_guid," +
            "ordinalNumber, okdp_code, okdp_name, okpd2_code, okpd2_name, okved_code, okved_name, okved2_code," +
            "okved2_name, okei_code, okei_name, qty, additional_info, delivery_state, delivery_region," +
            "delivery_region_okato, delivery_address, commodity_item_price, commodity_item_price_rub)" +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            var okdp = repositoryService.getClassifier(item.getOkdp());
            var okpd2 = repositoryService.getClassifier(item.getOkpd2());
            var okved = repositoryService.getClassifier(item.getOkved());
            var okved2 = repositoryService.getClassifier(item.getOkved2());
            var okei = repositoryService.getClassifier(item.getOkei());
            jdbcTemplate.update(
                sql,
                item.getGuid(),
                lotGuid,
                item.getOrdinalNumber(),
                okdp.getCode(),
                okdp.getName(),
                okpd2.getCode(),
                okpd2.getName(),
                okved.getCode(),
                okved.getName(),
                okved2.getCode(),
                okved2.getName(),
                okei.getCode(),
                okei.getName(),
                item.getQty(),
                item.getAdditionalInfo(),
                item.getDeliveryPlace() != null ? item.getDeliveryPlace().getState() : null,
                item.getDeliveryPlace() != null ? item.getDeliveryPlace().getRegion() : null,
                item.getDeliveryPlace() != null ? repositoryService.getOkatoCode(item.getDeliveryPlace().getRegionOkato()) : null,
                item.getDeliveryPlace() != null ? item.getDeliveryPlace().getAddress() : null,
                item.getCommodityItemPrice(),
                item.getCommodityItemPriceRub()
            );
        } catch (Exception e) {
            logger.error("Error during inserting purchase notice lot item", e);
        }
    }
}
