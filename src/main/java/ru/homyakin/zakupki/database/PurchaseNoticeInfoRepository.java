package ru.homyakin.zakupki.database;

import java.util.List;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.homyakin.zakupki.models._223fz.contract.PurchaseNoticeInfoType;
import ru.homyakin.zakupki.utils.RepositoryUtils;

@Component
public class PurchaseNoticeInfoRepository {
    private static final Logger logger = LoggerFactory.getLogger(PurchaseNoticeInfoRepository.class);
    private final JdbcTemplate jdbcTemplate;
    private final RepositoryUtils repositoryUtils;

    public PurchaseNoticeInfoRepository(DataSource dataSource, RepositoryUtils repositoryUtils) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        this.repositoryUtils = repositoryUtils;
    }

    public void insert(PurchaseNoticeInfoType purchaseNoticeInfo) {
        if (purchaseNoticeInfo == null) return;
        if (checkPurchaseNotice(purchaseNoticeInfo.getGuid(), purchaseNoticeInfo.getPurchaseNoticeNumber())) return;
        String sql = "INSERT INTO zakupki.purchase_notice_info (guid, number, publication_date_time," +
            "name) VALUES (?, ?, ?, ?)";
        try {
            jdbcTemplate.update(
                sql,
                purchaseNoticeInfo.getGuid(),
                purchaseNoticeInfo.getPurchaseNoticeNumber(),
                repositoryUtils.convertFromXMLGregorianCalendarToLocalDateTime(purchaseNoticeInfo.getPublicationDateTime()),
                purchaseNoticeInfo.getName()
            );
        } catch (RuntimeException e) {
            logger.error("Internal database error", e);
        }
    }

    private boolean checkPurchaseNotice(String guid, String number) {
        String sql = "SELECT guid FROM purchase_notice_info WHERE guid = ? and number = ?";
        List<String> result = jdbcTemplate.query(
            sql,
            new Object[]{guid, number},
            (rs, rowNum) -> rs.getString("guid")
        );
        return result.size() != 0;
    }
}
