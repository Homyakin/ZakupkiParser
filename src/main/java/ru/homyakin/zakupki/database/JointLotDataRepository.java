package ru.homyakin.zakupki.database;

import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.homyakin.zakupki.models._223fz.types.LotCustomerType;
import ru.homyakin.zakupki.utils.RepositoryUtils;

@Component
public class JointLotDataRepository {
    private static final Logger logger = LoggerFactory.getLogger(JointLotDataRepository.class);
    private final JdbcTemplate jdbcTemplate;
    private final CustomerRepository customerRepository;
    private final RepositoryUtils repositoryUtils;

    public JointLotDataRepository(
        DataSource dataSource,
        CustomerRepository customerRepository,
        RepositoryUtils repositoryUtils
    ) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.customerRepository = customerRepository;
        this.repositoryUtils = repositoryUtils;
    }

    public void insert(LotCustomerType lotCustomer, String lotGuid) {
        String sql = "INSERT INTO zakupki.joint_lot_data (purchase_notice_lot_guid, customer_inn, additional_info," +
            "delivary_place_indication_code, lot_customer_edit_enabled, tax, non_resident, non_resident_name," +
            "non_resident_code, country_code)" +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            customerRepository.insert(lotCustomer.getCustomerInfo());
            jdbcTemplate.update(
                sql,
                lotGuid,
                lotCustomer.getCustomerInfo().getInn(),
                lotCustomer.getAdditionalInfo(),
                lotCustomer.getDeliveryPlaceIndication().value(),
                repositoryUtils.convertBoolean(lotCustomer.isLotCustomerEditEnabled()),
                repositoryUtils.convertBoolean(lotCustomer.isTax()),
                repositoryUtils.convertBoolean(lotCustomer.isNonResident()),
                lotCustomer.getNonResidentInfo() != null ? lotCustomer.getNonResidentInfo().getName() : null,
                lotCustomer.getNonResidentInfo() != null ? lotCustomer.getNonResidentInfo().getCode() : null,
                lotCustomer.getNonResidentInfo() != null ? repositoryUtils.getCountryCode(lotCustomer.getNonResidentInfo().getCountry()) : null
            );
        } catch (Exception e) {
            logger.error("Error during inserting joint lot data", e);
        }
    }
}
