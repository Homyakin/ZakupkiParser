package ru.homyakin.zakupki.database.purchase_contract;

import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.homyakin.zakupki.models._223fz.types.PurchaseInfoType;
import ru.homyakin.zakupki.utils.CommonUtils;
import ru.homyakin.zakupki.utils.RepositoryUtils;

@Component
public class PurchaseInfoRepository {
    private final static Logger logger = LoggerFactory.getLogger(PurchaseInfoRepository.class);
    private final JdbcTemplate jdbcTemplate;

    public PurchaseInfoRepository(
        DataSource dataSource
    ) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Optional<String> insert(PurchaseInfoType purchaseInfo) {
        String sql = "INSERT INTO zakupki.purchase_info (guid, purchase_notice_number, publication_date_time," +
            "name, emergency) VALUES (?, ?, ?, ?, ?)";
        try {
            var guid = CommonUtils.validateAndGetGuid(purchaseInfo.getGuid());
            if (checkPurchaseInfo(guid)) return Optional.of(guid);
            jdbcTemplate.update(
                sql,
                guid,
                purchaseInfo.getPurchaseNoticeNumber(),
                RepositoryUtils.convertFromXMLGregorianCalendarToLocalDate(purchaseInfo.getPublicationDateTime()),
                purchaseInfo.getName(),
                RepositoryUtils.convertBoolean(purchaseInfo.isEmergency())
            );
            return Optional.of(guid);
        } catch (Exception e) {
            logger.error("Error during inserting in purchase_contract_lot", e);
            return Optional.empty();
        }
    }

    private boolean checkPurchaseInfo(String guid) {
        String sql = "SELECT guid FROM zakupki.purchase_info WHERE guid = ?";
        List<String> result = jdbcTemplate.query(sql, new Object[]{guid}, (rs, rowNum) -> rs.getString("guid"));
        return result.size() != 0;
    }
}
