package ru.homyakin.zakupki.database;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.homyakin.zakupki.Application;
import ru.homyakin.zakupki.documentsinfo._223fz.types.CustomerMainInfoType;

import javax.sql.DataSource;

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
                customer.getOkpo(),
                customer.getCustomerRegistrationDate(),
                customer.getTimeZone().getOffset(),
                customer.getTimeZone().getName(),
                customer.getRegion(),
                RepositoryService.convertBoolean(customer.isCustomerAssessedCompliance()),
                RepositoryService.convertBoolean(customer.isCustomerMonitoredCompliance())
            );
        } catch (Exception e) {
            logger.error("Eternal error", e);
        }
    }
}
