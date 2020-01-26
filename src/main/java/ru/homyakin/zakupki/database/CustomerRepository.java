package ru.homyakin.zakupki.database;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.homyakin.zakupki.models._223fz.types.CustomerMainInfoType;

import javax.sql.DataSource;
import java.util.List;

@Component
public class CustomerRepository {
    private static final Logger logger = LoggerFactory.getLogger(CustomerRepository.class);
    private final JdbcTemplate jdbcTemplate;
    private final RepositoryService repositoryService;

    public CustomerRepository(DataSource dataSource, RepositoryService repositoryService) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        this.repositoryService = repositoryService;
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
                repositoryService.removeExtraSpaces(customer.getInn()),
                repositoryService.removeExtraSpaces(customer.getFullName()),
                repositoryService.removeExtraSpaces(customer.getShortName()),
                repositoryService.removeExtraSpaces(customer.getIko()),
                repositoryService.removeExtraSpaces(customer.getKpp()),
                repositoryService.removeExtraSpaces(customer.getOgrn()),
                repositoryService.removeExtraSpaces(customer.getLegalAddress()),
                repositoryService.removeExtraSpaces(customer.getPostalAddress()),
                repositoryService.removeExtraSpaces(customer.getPhone()),
                repositoryService.removeExtraSpaces(customer.getFax()),
                repositoryService.removeExtraSpaces(customer.getEmail()),
                repositoryService.getOkatoCode(customer.getOkato()),
                repositoryService.removeExtraSpaces(customer.getOkopf()),
                repositoryService.validateOkpo(customer.getOkpo()),
                repositoryService.convertFromXMLGregorianCalendarToLocalDateTime(customer.getCustomerRegistrationDate()),
                timeZoneOffset,
                repositoryService.removeExtraSpaces(timeZoneName),
                repositoryService.removeExtraSpaces(customer.getRegion()),
                repositoryService.convertBoolean(customer.isCustomerAssessedCompliance()),
                repositoryService.convertBoolean(customer.isCustomerMonitoredCompliance())
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
