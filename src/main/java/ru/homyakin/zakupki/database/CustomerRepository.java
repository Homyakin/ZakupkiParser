package ru.homyakin.zakupki.database;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.homyakin.zakupki.documentsinfo._223fz.types.CustomerMainInfoType;

import javax.sql.DataSource;
import java.util.List;

@Component
public class CustomerRepository {
    private static final Logger logger = LoggerFactory.getLogger(CustomerRepository.class);
    private final JdbcTemplate jdbcTemplate;

    public CustomerRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void insert(CustomerMainInfoType customer) {
        String sql = "INSERT INTO customer (inn, full_name, short_name, iko, kpp, ogrn, legal_address," +
            "postal_address, phone, fax, email, okato, okopf_code, okpo, customer_registration_date," +
            "timezone_offset, timezone_name, region, customer_assessed_compliance, customer_monitored_compliance)" +
            "VALUES" +
            "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            if (checkCustomer(customer.getInn())) return; //TODO make update if exists
            Integer timeZoneOffset = customer.getTimeZone() != null ? customer.getTimeZone().getOffset() : null;
            String timeZoneName = customer.getTimeZone() != null ? customer.getTimeZone().getName() : null;
            jdbcTemplate.update(
                sql,
                customer.getInn(),
                customer.getFullName(),
                customer.getShortName(),
                customer.getIko(),
                customer.getKpp(),
                customer.getOgrn(),
                customer.getLegalAddress(),
                customer.getPostalAddress(),
                customer.getPhone(),
                customer.getFax(),
                customer.getEmail(),
                customer.getOkato(),
                customer.getOkopf(),
                customer.getOkpo().trim(),
                RepositoryService.convertFromXMLGregorianCalendarToLocalDateTime(customer.getCustomerRegistrationDate()),
                timeZoneOffset,
                timeZoneName,
                customer.getRegion(),
                RepositoryService.convertBoolean(customer.isCustomerAssessedCompliance()),
                RepositoryService.convertBoolean(customer.isCustomerMonitoredCompliance())
            );
        } catch (Exception e) {
            logger.error("Eternal error", e);
        }
    }

    private boolean checkCustomer(String inn) {
        String sql = "SELECT inn FROM customer WHERE inn = ?";
        List<String> result = jdbcTemplate.query(sql, new Object[]{inn}, (rs, rowNum) -> rs.getString("inn"));
        return result.size() != 0;
    }
}
