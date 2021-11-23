package ru.homyakin.zakupki.database.contract;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import ru.homyakin.zakupki.models._223fz.contract.SupplierAddressType;
import ru.homyakin.zakupki.utils.RepositoryUtils;

@Component
public class SupplierAddressRepository {
    private static final Logger logger = LoggerFactory.getLogger(SupplierAddressRepository.class);
    private final SimpleJdbcInsert simpleJdbcInsert;
    private final RepositoryUtils repositoryUtils;

    public SupplierAddressRepository(DataSource dataSource, RepositoryUtils repositoryUtils) {
        this.repositoryUtils = repositoryUtils;
        simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
            .withTableName("supplier_address")
            .usingGeneratedKeyColumns("id");
    }

    public Number insert(SupplierAddressType address) {
        if (address == null) return null;
        try {
            Map<String, Object> parameters = new HashMap<>(19);
            parameters.put("egrul_egrip_changed", repositoryUtils.convertBoolean(address.isEgrulEgripChanged()));
            parameters.put("country_code", repositoryUtils.getCountryCode(address.getCountry()));
            parameters.put("post_code", address.getPostCode());
            parameters.put("oktmo_code", address.getOktmo());
            parameters.put("oktmo_name", address.getOktmoName());
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
            logger.error("Error during inserting into supplier address", e);
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
