package ru.homyakin.zakupki.database.purchase_notice;

import java.math.BigDecimal;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.homyakin.zakupki.models._223fz.types.DocDeliveryInfoType;
import ru.homyakin.zakupki.utils.RepositoryUtils;

@Component
public class PurchaseNoticeDocumentationDelivery {
    private static final Logger logger = LoggerFactory.getLogger(PurchaseNoticeDocumentationDelivery.class);
    private final JdbcTemplate jdbcTemplate;
    private final RepositoryUtils repositoryUtils;

    public PurchaseNoticeDocumentationDelivery(DataSource dataSource, RepositoryUtils repositoryUtils) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.repositoryUtils = repositoryUtils;
    }

    public void insert(DocDeliveryInfoType delivery, String noticeGuid) {
        if (delivery == null) return;
        String sql = "INSERT INTO zakupki.purchase_notice_documentation_delivery (purchase_notice_guid," +
            "delivery_start_date_time, delivery_end_date_time, place, documentation_procedure, currency_code," +
            "sum, payment_procedure)" +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        String currencyCode = null;
        String paymentProcedure = null;
        BigDecimal sum = null;
        if (delivery.getPayment() != null) {
            currencyCode = RepositoryUtils.getCurrencyCode(delivery.getPayment().getCurrency());
            paymentProcedure = delivery.getPayment().getProcedure();
            sum = delivery.getPayment().getSum();
        }
        try {
            jdbcTemplate.update(
                sql,
                noticeGuid,
                RepositoryUtils.convertFromXMLGregorianCalendarToLocalDateTime(delivery.getDeliveryStartDateTime()),
                RepositoryUtils.convertFromXMLGregorianCalendarToLocalDateTime(delivery.getDeliveryEndDateTime()),
                delivery.getPlace(),
                delivery.getProcedure(),
                currencyCode,
                sum,
                paymentProcedure
            );
        } catch (DuplicateKeyException ignored) {
            //TODO посмотреть файлы
        } catch (Exception e) {
            logger.error("Error during inserting documentation delivery", e);
        }
    }
}
