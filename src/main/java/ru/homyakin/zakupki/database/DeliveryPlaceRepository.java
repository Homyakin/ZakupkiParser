package ru.homyakin.zakupki.database;

import java.util.Optional;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.homyakin.zakupki.models._223fz.types.DeliveryPlaceType;
import ru.homyakin.zakupki.utils.CommonUtils;

@Component
public class DeliveryPlaceRepository {
    private final static Logger logger = LoggerFactory.getLogger(DeliveryPlaceRepository.class);
    private final JdbcTemplate jdbcTemplate;
    private final CommonUtils commonUtils;

    public DeliveryPlaceRepository(
        DataSource dataSource,
        CommonUtils commonUtils
    ) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        this.commonUtils = commonUtils;
    }

    public Optional<String> insert(DeliveryPlaceType deliveryPlace) {
        var sql = "INSERT INTO zakupki.delivery_place (guid, state, region, region_okato, address)" +
            "VALUES (?, ?, ?, ?, ?)";
        if (deliveryPlace == null) return Optional.empty();
        var guid = commonUtils.generateGuid();
        try {
            jdbcTemplate.update(
                sql,
                guid,
                deliveryPlace.getState(),
                deliveryPlace.getRegion(),
                deliveryPlace.getRegionOkato(),
                deliveryPlace.getAddress()
            );
            return Optional.of(guid);
        } catch (Exception e) {
            logger.error("Error during inserting in delivery_place", e);
            return Optional.empty();
        }
    }
}
