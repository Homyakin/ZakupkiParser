package ru.homyakin.zakupki.database;

import java.util.List;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.homyakin.zakupki.models._223fz.contract.SupplierMainType;

@Component
public class SupplierRepository {
    private static final Logger logger = LoggerFactory.getLogger(SupplierRepository.class);
    private final JdbcTemplate jdbcTemplate;
    private final SupplierAddressRepository supplierAddressRepository;
    private final RepositoryService repositoryService;

    public SupplierRepository(
        DataSource dataSource,
        SupplierAddressRepository supplierAddressRepository,
        RepositoryService repositoryService
    ) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        this.supplierAddressRepository = supplierAddressRepository;
        this.repositoryService = repositoryService;
    }

    public void insert(SupplierMainType supplier, String contractGuid) {
        if (supplier.getInn() == null) {
            insertSupplierWithoutInn(supplier, contractGuid);
            return;
        }
        //TODO add existing check
        String sql = "INSERT INTO zakupki.supplier (inn, name, short_name, brand_name, additional_full_name," +
            "kpp, okpo, code, additional_code, additional_info, type, provider, provider_code, subcontractor," +
            "subcontractor_code, individual, non_resident, registration_date, tax, okopf_code, okopf_name," +
            "address_id, address_rf_id, provider_include_msp_date, is_in_order_173n)" +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            if (checkSupplier(supplier.getInn())) return;
            ClassifierRepository.Classifier okopf = repositoryService.getOkopf(supplier.getOkopf(), supplier.getOkopfName());
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
                supplier.getType(),
                repositoryService.convertBoolean(supplier.isProvider()),
                supplier.getProviderCode(),
                repositoryService.convertBoolean(supplier.isSubcontractor()),
                supplier.getSubcontractorCode(),
                repositoryService.convertBoolean(supplier.isIndividual()),
                repositoryService.convertBoolean(supplier.isNonResident()),
                repositoryService.convertFromXMLGregorianCalendarToLocalDate(supplier.getRegistrationDate()),
                repositoryService.convertBoolean(supplier.isTax()),
                repositoryService.getClassifierCode(okopf),
                repositoryService.getClassifierName(okopf),
                supplierAddressRepository.insert(supplier.getAddress()),
                supplierAddressRepository.insert(supplier.getAddressRf()),
                repositoryService.convertFromXMLGregorianCalendarToLocalDate(supplier.getProviderIncludeMSPDate()),
                repositoryService.convertBoolean(supplier.isIsInOrder173N())
            );
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
        ClassifierRepository.Classifier okopf = repositoryService.getOkopf(supplier.getOkopf(), supplier.getOkopfName());
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
                supplier.getType(),
                repositoryService.convertBoolean(supplier.isProvider()),
                supplier.getProviderCode(),
                repositoryService.convertBoolean(supplier.isSubcontractor()),
                supplier.getSubcontractorCode(),
                repositoryService.convertBoolean(supplier.isIndividual()),
                repositoryService.convertBoolean(supplier.isNonResident()),
                repositoryService.convertFromXMLGregorianCalendarToLocalDate(supplier.getRegistrationDate()),
                repositoryService.convertBoolean(supplier.isTax()),
                repositoryService.getClassifierCode(okopf),
                repositoryService.getClassifierName(okopf),
                supplierAddressRepository.insert(supplier.getAddress()),
                supplierAddressRepository.insert(supplier.getAddressRf()),
                repositoryService.convertFromXMLGregorianCalendarToLocalDate(supplier.getProviderIncludeMSPDate()),
                repositoryService.convertBoolean(supplier.isIsInOrder173N())
            );
        } catch (RuntimeException e) {
            logger.error("Internal database error", e);
        }
    }
}
