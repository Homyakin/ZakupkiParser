package ru.homyakin.zakupki.database;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.homyakin.zakupki.documentsinfo._223fz.types.CustomerMainInfoType;

import javax.sql.DataSource;

@Component
public class CustomerRepository {
    private final JdbcTemplate jdbcTemplate;

    public CustomerRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void insert(CustomerMainInfoType customer) {
        String sql = "INSERT INTO customer (inn, full_name, short_name, iko, kpp, ogrn, legal_address," +
            "postal_address, phone, fax, email, okopf_code, customer_registration_date," +
            "timezone_offset, timezone_name, region, customer_assessed_compliance, customer_monitored_compliance)" +
            "VALUES" +
            "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
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
            customer.getOkopf(),
            customer.getCustomerRegistrationDate(),
            customer.getTimeZone().getOffset(),
            customer.getTimeZone().getName(),
            customer.getRegion(),
            customer.isCustomerAssessedCompliance() ? 1 : 0,
            customer.isCustomerMonitoredCompliance() ? 1 : 0
        );
    }
}
