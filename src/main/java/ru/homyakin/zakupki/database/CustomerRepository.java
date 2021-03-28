package ru.homyakin.zakupki.database;

import java.util.List;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.homyakin.zakupki.models._223fz.types.CustomerMainInfo3Type;
import ru.homyakin.zakupki.models._223fz.types.CustomerMainInfoType;
import ru.homyakin.zakupki.utils.RepositoryUtils;

@Component
public class CustomerRepository {
    private static final Logger logger = LoggerFactory.getLogger(CustomerRepository.class);
    private final JdbcTemplate jdbcTemplate;
    private final RepositoryUtils repositoryUtils;

    public CustomerRepository(DataSource dataSource, RepositoryUtils repositoryUtils) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        this.repositoryUtils = repositoryUtils;
    }

    public void insert(CustomerMainInfoType customer) {
        if (customer == null) return;
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
                repositoryUtils.removeExtraSpaces(customer.getInn()),
                repositoryUtils.removeExtraSpaces(customer.getFullName()),
                repositoryUtils.removeExtraSpaces(customer.getShortName()),
                repositoryUtils.removeExtraSpaces(customer.getIko()),
                repositoryUtils.removeExtraSpaces(customer.getKpp()),
                repositoryUtils.removeExtraSpaces(customer.getOgrn()),
                repositoryUtils.removeExtraSpaces(customer.getLegalAddress()),
                repositoryUtils.removeExtraSpaces(customer.getPostalAddress()),
                repositoryUtils.removeExtraSpaces(customer.getPhone()),
                repositoryUtils.removeExtraSpaces(customer.getFax()),
                repositoryUtils.removeExtraSpaces(customer.getEmail()),
                customer.getOkato(),
                customer.getOkopf(),
                repositoryUtils.validateOkpo(customer.getOkpo()),
                repositoryUtils.convertFromXMLGregorianCalendarToLocalDateTime(customer.getCustomerRegistrationDate()),
                timeZoneOffset,
                repositoryUtils.removeExtraSpaces(timeZoneName),
                repositoryUtils.removeExtraSpaces(customer.getRegion()),
                repositoryUtils.convertBoolean(customer.isCustomerAssessedCompliance()),
                repositoryUtils.convertBoolean(customer.isCustomerMonitoredCompliance())
            );
        } catch (DuplicateKeyException ignored) {

        } catch (Exception e) {
            logger.error("Internal database error", e);
        }
    }

    public void insert(CustomerMainInfo3Type customer) {
        if (customer == null) return;
        try {
            if (checkCustomer(customer.getInn())) return; //TODO make update if exists
            String sql = "INSERT INTO customer (inn, full_name, short_name, kpp, ogrn, legal_address," +
                "postal_address) VALUES (?, ?, ?, ?, ?, ?, ?)";
            jdbcTemplate.update(
                sql,
                customer.getInn(),
                customer.getFullName(),
                customer.getShortName(),
                customer.getKpp(),
                customer.getOgrn(),
                customer.getLegalAddress(),
                customer.getPostalAddress()
            );
        } catch (Exception e) {
            logger.error("Internal database error", e);
        }
    }

    private boolean checkCustomer(String inn) {
        String sql = "SELECT inn FROM customer WHERE inn = ?";
        List<String> result = jdbcTemplate.query(sql, new Object[]{inn}, (rs, rowNum) -> rs.getString("inn"));
        return result.size() != 0;
    }
}
