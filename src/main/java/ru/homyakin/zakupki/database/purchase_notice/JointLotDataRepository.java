package ru.homyakin.zakupki.database.purchase_notice;

import java.util.Optional;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.homyakin.zakupki.database.CustomerRepository;
import ru.homyakin.zakupki.models._223fz.types.CustomerMainInfoType;
import ru.homyakin.zakupki.models._223fz.types.LotCustomerType;
import ru.homyakin.zakupki.utils.RepositoryUtils;

@Component
public class JointLotDataRepository {
    private static final Logger logger = LoggerFactory.getLogger(JointLotDataRepository.class);
    private final JdbcTemplate jdbcTemplate;
    private final CustomerRepository customerRepository;

    public JointLotDataRepository(
        DataSource dataSource,
        CustomerRepository customerRepository
    ) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.customerRepository = customerRepository;
    }

    public void insert(LotCustomerType lotCustomer, String lotGuid) {
        String sql = "INSERT INTO zakupki.joint_lot_data (purchase_notice_lot_guid, customer_inn, additional_info," +
            "delivery_place_indication_code, lot_customer_edit_enabled, tax, non_resident, non_resident_name," +
            "non_resident_code, country_code)" +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            customerRepository.insert(lotCustomer.getCustomerInfo());
            jdbcTemplate.update(
                sql,
                lotGuid,
                //TODO кастомер обязательный
                Optional.ofNullable(lotCustomer.getCustomerInfo()).map(CustomerMainInfoType::getInn).orElse(null),
                lotCustomer.getAdditionalInfo(),
                lotCustomer.getDeliveryPlaceIndication() != null ? lotCustomer.getDeliveryPlaceIndication().value() : null,
                RepositoryUtils.convertBoolean(lotCustomer.isLotCustomerEditEnabled()),
                RepositoryUtils.convertBoolean(lotCustomer.isTax()),
                RepositoryUtils.convertBoolean(lotCustomer.isNonResident()),
                lotCustomer.getNonResidentInfo() != null ? lotCustomer.getNonResidentInfo().getName() : null,
                lotCustomer.getNonResidentInfo() != null ? lotCustomer.getNonResidentInfo().getCode() : null,
                lotCustomer.getNonResidentInfo() != null ? RepositoryUtils.getCountryCode(lotCustomer.getNonResidentInfo().getCountry()) : null
            );
        } catch (Exception e) {
            logger.error("Error during inserting joint lot data", e);
        }
    }
}
