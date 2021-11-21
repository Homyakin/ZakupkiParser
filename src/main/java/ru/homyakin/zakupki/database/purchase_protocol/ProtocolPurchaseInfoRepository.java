package ru.homyakin.zakupki.database.purchase_protocol;

import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.homyakin.zakupki.models._223fz.types.PurchaseInfoType;
import ru.homyakin.zakupki.utils.CommonUtils;
import ru.homyakin.zakupki.utils.RepositoryUtils;

@Component
public class ProtocolPurchaseInfoRepository {
    private final static Logger logger = LoggerFactory.getLogger(ProtocolPurchaseInfoRepository.class);
    private final static String INSERT =
        """
        insert into protocol_purchase_info (guid, purchase_notice_number, publication_date_time, name, purchase_method_code,
         emergency) values (?, ?, ?, ?, ?, ?);
        """;
    private final JdbcTemplate jdbcTemplate;

    public ProtocolPurchaseInfoRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public String insert(PurchaseInfoType purchaseInfo) {
        try {
            final var guid = CommonUtils.validateAndGetGuid(purchaseInfo.getGuid());
            jdbcTemplate.update(
                INSERT,
                guid,
                purchaseInfo.getPurchaseNoticeNumber(),
                RepositoryUtils.convertFromXMLGregorianCalendarToLocalDateTime(purchaseInfo.getPublicationDateTime()),
                purchaseInfo.getName(),
                purchaseInfo.getPurchaseMethod() != null ? purchaseInfo.getPurchaseMethod().value() : null,
                RepositoryUtils.convertBoolean(purchaseInfo.isEmergency())
            );
            return guid;
        } catch (Exception e) {
            logger.error("Error during inserting into protocol_purchase_info", e);
            return null;
        }
    }
}
