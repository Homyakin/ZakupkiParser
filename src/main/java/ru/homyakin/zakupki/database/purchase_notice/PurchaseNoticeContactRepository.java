package ru.homyakin.zakupki.database.purchase_notice;

import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.homyakin.zakupki.models._223fz.types.ContactInfoType;

@Component
public class PurchaseNoticeContactRepository {
    private static final Logger logger = LoggerFactory.getLogger(PurchaseNoticeContactRepository.class);
    private final JdbcTemplate jdbcTemplate;

    public PurchaseNoticeContactRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void insert(ContactInfoType contact, String noticeGuid) {
        String sql = "INSERT INTO zakupki.purchase_notice_contact (purchase_notice_guid, first_name, middle_name," +
            "last_name, phone, fax, email, additional_contact_info)" +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(
            sql,
            noticeGuid,
            contact.getFirstName(),
            contact.getMiddleName(),
            contact.getLastName(),
            contact.getPhone(),
            contact.getFax(),
            contact.getEmail(),
            contact.getAdditionalContactInfo()
        );
    }
}
