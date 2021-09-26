package ru.homyakin.zakupki.database.purchase_notice;

import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class PurchaseNoticeToLotRepository {
    private final static Logger logger = LoggerFactory.getLogger(PurchaseNoticeToLotRepository.class);
    private final static String INSERT =
        """
        insert into purchase_notice_to_lot (purchase_notice_guid, purchase_notice_lot_guid) values (?, ?);
        """;
    private final JdbcTemplate jdbcTemplate;

    public PurchaseNoticeToLotRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void insert(String noticeGuid, String lotGuid) {
        try {
            jdbcTemplate.update(
                INSERT,
                noticeGuid,
                lotGuid
            );
        } catch (Exception e) {
            logger.error("Error during inserting into purchase_notice_to_lot", e);
        }
    }
}
