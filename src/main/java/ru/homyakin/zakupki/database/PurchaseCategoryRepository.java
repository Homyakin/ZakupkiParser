package ru.homyakin.zakupki.database;

import java.util.List;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class PurchaseCategoryRepository {
    private static final Logger logger = LoggerFactory.getLogger(PurchaseCategoryRepository.class);
    private final JdbcTemplate jdbcTemplate;

    public PurchaseCategoryRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Long getCategoryCode(Long code) {
        if (code == null) return null;
        String sql = "SELECT code FROM purchase_category WHERE code = ?";
        List<Long> result = jdbcTemplate.query(sql,
            new Object[]{code},
            (rs, rowNum) -> rs.getLong("code")
        );
        if (result.size() == 0) return 0L;
        else return code;
    }

}
