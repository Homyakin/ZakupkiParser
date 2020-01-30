package ru.homyakin.zakupki.database;

import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.homyakin.zakupki.models._223fz.contract.PurchaseNoticeInfoType;

@Component
public class PurchaseNoticeInfoRepository {
    private static final Logger logger = LoggerFactory.getLogger(PurchaseNoticeInfoRepository.class);
    private final JdbcTemplate jdbcTemplate;
    private final RepositoryService repositoryService;

    public PurchaseNoticeInfoRepository(DataSource dataSource, RepositoryService repositoryService) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        this.repositoryService = repositoryService;
    }

    public void insert(PurchaseNoticeInfoType purchaseNoticeInfo) {
        String sql = "INSERT INTO zakupki.purchase_notice_info (guid, number, publication_date_time," +
            "name) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(
            sql,
            purchaseNoticeInfo.getGuid(),
            purchaseNoticeInfo.getPurchaseNoticeNumber(),
            repositoryService.convertFromXMLGregorianCalendarToLocalDateTime(purchaseNoticeInfo.getPublicationDateTime()),
            purchaseNoticeInfo.getName()
        );
    }
}
