package ru.homyakin.zakupki.database.purchase_contract;

import java.util.List;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.homyakin.zakupki.models._223fz.types.LotLinkType;

@Component
public class PurchaseContractLotRepository {
    private final static Logger logger = LoggerFactory.getLogger(PurchaseContractLotRepository.class);
    private final JdbcTemplate jdbcTemplate;

    public PurchaseContractLotRepository(
        DataSource dataSource
    ) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void insert(LotLinkType lot) {
        String sql = "INSERT INTO zakupki.purchase_contract_lot(guid, subject) VALUES (?, ?)";
        try {
            if (checkLot(lot.getGuid())) return;
            jdbcTemplate.update(
                sql,
                lot.getGuid(),
                lot.getSubject()
            );
        } catch (Exception e) {
            logger.error("Error during inserting in purchase_contract_lot", e);
        }
    }

    private boolean checkLot(String guid) {
        String sql = "SELECT guid FROM zakupki.purchase_contract_lot WHERE guid = ?";
        List<String> result = jdbcTemplate.query(sql, new Object[]{guid}, (rs, rowNum) -> rs.getString("guid"));
        return result.size() != 0;
    }
}
