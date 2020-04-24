package ru.homyakin.zakupki.database;

import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.homyakin.zakupki.models._223fz.types.LotCriteriaType;

@Component
public class LotCriteriaRepository {
    private static final Logger logger = LoggerFactory.getLogger(LotCriteriaRepository.class);
    private final JdbcTemplate jdbcTemplate;

    public LotCriteriaRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void insert(LotCriteriaType criteria, String lotGuid) {
        String sql = "INSERT INTO zakupki.lot_criteria (guid, purchase_notice_lot_guid, name, description," +
            "max_value, weight) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            jdbcTemplate.update(
                sql,
                criteria.getGuid(),
                lotGuid,
                criteria.getName(),
                criteria.getDescription(),
                criteria.getMaxValue(),
                criteria.getWeight()
            );
        } catch (Exception e) {
            logger.error("Error during inserting lot criteria");
        }
    }
}
