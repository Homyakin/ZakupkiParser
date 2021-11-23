package ru.homyakin.zakupki.database.contract;

import java.util.List;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.homyakin.zakupki.models._223fz.contract.SupplierMainType;
import ru.homyakin.zakupki.utils.CommonUtils;
import ru.homyakin.zakupki.utils.RepositoryUtils;

@Component
public class SupplierRepository {
    private static final Logger logger = LoggerFactory.getLogger(SupplierRepository.class);
    private final JdbcTemplate jdbcTemplate;
    private final SupplierAddressRepository supplierAddressRepository;

    public SupplierRepository(
        DataSource dataSource,
        SupplierAddressRepository supplierAddressRepository
    ) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        this.supplierAddressRepository = supplierAddressRepository;
    }

    public void insert(SupplierMainType supplier, String contractGuid) {

        String sql = "INSERT INTO zakupki.supplier (guid, inn, name, short_name, brand_name, additional_full_name," +
            "kpp, okpo, code, additional_code, additional_info, type, provider, provider_code, subcontractor," +
            "subcontractor_code, individual, non_resident, registration_date, tax, okopf_code, okopf_name," +
            "address_id, address_rf_id, provider_include_msp_date, is_in_order_173n)" +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            var guid = CommonUtils.generateGuid();
            var inn = CommonUtils.validateInn(supplier.getInn());
            if (inn.isPresent() && !checkSupplier(inn.get())) {
                jdbcTemplate.update(
                    sql,
                    guid,
                    inn.orElse(null),
                    supplier.getName(),
                    supplier.getShortName(),
                    supplier.getBrandName(),
                    supplier.getAdditionalFullName(),
                    supplier.getKpp(),
                    supplier.getOkpo(),
                    supplier.getCode(),
                    supplier.getAdditionalCode(),
                    supplier.getAdditionalInfo(),
                    supplier.getType().value(),
                    RepositoryUtils.convertBoolean(supplier.isProvider()),
                    supplier.getProviderCode(),
                    RepositoryUtils.convertBoolean(supplier.isSubcontractor()),
                    supplier.getSubcontractorCode(),
                    RepositoryUtils.convertBoolean(supplier.isIndividual()),
                    RepositoryUtils.convertBoolean(supplier.isNonResident()),
                    RepositoryUtils.convertFromXMLGregorianCalendarToLocalDate(supplier.getRegistrationDate()),
                    RepositoryUtils.convertBoolean(supplier.isTax()),
                    supplier.getOkopf(),
                    supplier.getOkopfName(),
                    supplierAddressRepository.insert(supplier.getAddress()),
                    supplierAddressRepository.insert(supplier.getAddressRf()),
                    RepositoryUtils.convertFromXMLGregorianCalendarToLocalDate(supplier.getProviderIncludeMSPDate()),
                    RepositoryUtils.convertBoolean(supplier.isIsInOrder173N())
                );
            }
            insertSupplierToContract(guid, contractGuid);
        } catch (RuntimeException e) {
            logger.error("Error during inserting into supplier", e);
        }
    }

    private boolean checkSupplier(String inn) {
        String sql = "SELECT inn FROM supplier WHERE inn = ?";
        List<String> result = jdbcTemplate.query(sql, new Object[]{inn}, (rs, rowNum) -> rs.getString("inn"));
        return result.size() != 0;
    }

    private void insertSupplierToContract(String supplierGuid, String contractGuid) {
        String sql = "INSERT INTO zakupki.supplier_to_contract (contract_guid, supplier_guid)" +
            "VALUES (?, ?)";
        jdbcTemplate.update(
            sql,
            contractGuid,
            supplierGuid
        );
    }
}
