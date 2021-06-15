package ru.homyakin.zakupki.database.purchase_protocol;

import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.homyakin.zakupki.models._223fz.purchase.PurchaseProtocol;

@Component
public class PurchaseProtocolRepository {
    private final JdbcTemplate jdbcTemplate;

    public PurchaseProtocolRepository(
        DataSource dataSource
    ) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void insert(PurchaseProtocol protocol) {

    }
}
