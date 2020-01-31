package ru.homyakin.zakupki.database;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import ru.homyakin.zakupki.models._223fz.contract.SupplierAddressType;

@Component
public class SupplierAddressRepository {
    private static final Logger logger = LoggerFactory.getLogger(SupplierAddressRepository.class);
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;
    private final RepositoryService repositoryService;

    public SupplierAddressRepository(DataSource dataSource, RepositoryService repositoryService) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        this.repositoryService = repositoryService;
        simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
            .withTableName("supplier_address")
            .usingGeneratedKeyColumns("id");
    }

    public Number insert(SupplierAddressType address) {
        if (address == null) return null;
        try {
            Map<String, Object> parameters = new HashMap<>(19);
            ClassifierRepository.Classifier oktmo = repositoryService.getOktmo(address.getOktmo(), address.getOktmoName());
            parameters.put("egrul_egrip_changed", repositoryService.convertBoolean(address.isEgrulEgripChanged()));
            parameters.put("country_code", repositoryService.getCountryCode(address.getCountry()));
            parameters.put("post_code", address.getPostCode());
            parameters.put("oktmo_code", repositoryService.getClassifierCode(oktmo));
            parameters.put("oktmo_name", repositoryService.getClassifierName(oktmo));
            parameters.put("region_code", getRegionCode(address.getRegion()));
            parameters.put("region_name", getRegionName(address.getRegion()));
            parameters.put("area", address.getArea());
            parameters.put("city", address.getCity());
            parameters.put("settlement", address.getSettlement());
            parameters.put("street", address.getStreet());
            parameters.put("email", address.getEmail());
            parameters.put("phone", address.getPhone());
            parameters.put("office", address.getOffice());
            parameters.put("building", address.getBuilding());
            parameters.put("house", address.getHouse());
            parameters.put("planning_structure", address.getPlanningStructure());
            parameters.put("admin_division", address.getAdminDivision());
            return simpleJdbcInsert.executeAndReturnKey(parameters);
        } catch (RuntimeException e) {
            logger.error("Internal database error", e);
            return null;
        }
    }

    private BigInteger getRegionCode(SupplierAddressType.Region region) {
        if (region == null) return null;
        else return region.getCode();
    }

    private String getRegionName(SupplierAddressType.Region region) {
        if (region == null) return null;
        else return region.getName();
    }
}
