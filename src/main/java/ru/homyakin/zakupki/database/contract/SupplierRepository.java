package ru.homyakin.zakupki.database.contract;

import java.util.List;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.homyakin.zakupki.models._223fz.contract.SupplierMainType;
import ru.homyakin.zakupki.utils.RepositoryUtils;

@Component
public class SupplierRepository {
    private static final Logger logger = LoggerFactory.getLogger(SupplierRepository.class);
    private final JdbcTemplate jdbcTemplate;
    private final SupplierAddressRepository supplierAddressRepository;
    private final RepositoryUtils repositoryUtils;

    public SupplierRepository(
        DataSource dataSource,
        SupplierAddressRepository supplierAddressRepository,
        RepositoryUtils repositoryUtils
    ) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        this.supplierAddressRepository = supplierAddressRepository;
        this.repositoryUtils = repositoryUtils;
    }

    public void insert(SupplierMainType supplier, String contractGuid) {
        if (supplier.getInn() == null || supplier.getInn().equals("000000000000")) {
            insertSupplierWithoutInn(supplier, contractGuid);
            return;
        }

        String sql = "INSERT INTO zakupki.supplier (inn, name, short_name, brand_name, additional_full_name," +
            "kpp, okpo, code, additional_code, additional_info, type, provider, provider_code, subcontractor," +
            "subcontractor_code, individual, non_resident, registration_date, tax, okopf_code, okopf_name," +
            "address_id, address_rf_id, provider_include_msp_date, is_in_order_173n)" +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            if (!checkSupplier(supplier.getInn())) {
                jdbcTemplate.update(
                    sql,
                    supplier.getInn(),
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
                    repositoryUtils.convertBoolean(supplier.isProvider()),
                    supplier.getProviderCode(),
                    repositoryUtils.convertBoolean(supplier.isSubcontractor()),
                    supplier.getSubcontractorCode(),
                    repositoryUtils.convertBoolean(supplier.isIndividual()),
                    repositoryUtils.convertBoolean(supplier.isNonResident()),
                    repositoryUtils.convertFromXMLGregorianCalendarToLocalDate(supplier.getRegistrationDate()),
                    repositoryUtils.convertBoolean(supplier.isTax()),
                    supplier.getOkopf(),
                    supplier.getOkopfName(),
                    supplierAddressRepository.insert(supplier.getAddress()),
                    supplierAddressRepository.insert(supplier.getAddressRf()),
                    repositoryUtils.convertFromXMLGregorianCalendarToLocalDate(supplier.getProviderIncludeMSPDate()),
                    repositoryUtils.convertBoolean(supplier.isIsInOrder173N())
                );
            }
            insertSupplierToContract(supplier.getInn(), contractGuid);
        } catch (RuntimeException e) {
            logger.error("Internal database error", e);
        }
    }

    private boolean checkSupplier(String inn) {
        String sql = "SELECT inn FROM supplier WHERE inn = ?";
        List<String> result = jdbcTemplate.query(sql, new Object[]{inn}, (rs, rowNum) -> rs.getString("inn"));
        return result.size() != 0;
    }

    private void insertSupplierToContract(String supplierInn, String contractGuid) {
        String sql = "INSERT INTO zakupki.supplier_to_contract (contract_guid, supplier_inn)" +
            "VALUES (?, ?)";
        jdbcTemplate.update(
            sql,
            contractGuid,
            supplierInn
        );
    }

    private void insertSupplierWithoutInn(SupplierMainType supplier, String contractGuid) {
        String sql = "INSERT INTO zakupki.supplier_without_inn (name, contract_guid, short_name," +
            "brand_name, additional_full_name, inn, kpp, okpo, code, additional_code, additional_info," +
            "type, provider, provider_code, subcontractor, subcontractor_code, individual, non_resident," +
            "registration_date, tax, okopf_code, okopf_name, address_id, address_rf_id, provider_include_msp_date," +
            "is_in_order_173n)" +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            jdbcTemplate.update(
                sql,
                supplier.getName(),
                contractGuid,
                supplier.getShortName(),
                supplier.getBrandName(),
                supplier.getAdditionalFullName(),
                supplier.getInn(),
                supplier.getKpp(),
                supplier.getOkpo(),
                supplier.getCode(),
                supplier.getAdditionalCode(),
                supplier.getAdditionalInfo(),
                supplier.getType().value(),
                repositoryUtils.convertBoolean(supplier.isProvider()),
                supplier.getProviderCode(),
                repositoryUtils.convertBoolean(supplier.isSubcontractor()),
                supplier.getSubcontractorCode(),
                repositoryUtils.convertBoolean(supplier.isIndividual()),
                repositoryUtils.convertBoolean(supplier.isNonResident()),
                repositoryUtils.convertFromXMLGregorianCalendarToLocalDate(supplier.getRegistrationDate()),
                repositoryUtils.convertBoolean(supplier.isTax()),
                supplier.getOkopf(),
                supplier.getOkopfName(),
                supplierAddressRepository.insert(supplier.getAddress()),
                supplierAddressRepository.insert(supplier.getAddressRf()),
                repositoryUtils.convertFromXMLGregorianCalendarToLocalDate(supplier.getProviderIncludeMSPDate()),
                repositoryUtils.convertBoolean(supplier.isIsInOrder173N())
            );
        } catch (RuntimeException e) {
            logger.error("Internal database error", e);
        }
    }
}
