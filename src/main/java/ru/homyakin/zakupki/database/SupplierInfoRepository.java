package ru.homyakin.zakupki.database;

import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.homyakin.zakupki.models._223fz.types.SupplierMainInfoType;
import ru.homyakin.zakupki.utils.CommonUtils;
import ru.homyakin.zakupki.utils.RepositoryUtils;

@Component
public class SupplierInfoRepository {
    private final static Logger logger = LoggerFactory.getLogger(SupplierInfoRepository.class);
    private final JdbcTemplate jdbcTemplate;
    private final CommonUtils commonUtils;
    private final RepositoryUtils repositoryUtils;

    public SupplierInfoRepository(
        DataSource dataSource,
        CommonUtils commonUtils,
        RepositoryUtils repositoryUtils
    ) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        this.commonUtils = commonUtils;
        this.repositoryUtils = repositoryUtils;
    }

    public Optional<String> insert(SupplierMainInfoType supplier) {
        var sql = "INSERT INTO zakupki.supplier_info (guid, inn, name, kpp, ogrn, type, address) VALUES (?, ?, ?, ?, ?, ?, ?)";
        if (supplier == null) return Optional.empty();
        try {
            var inn = validateInn(supplier.getInn());
            var ogrn = validateOgrn(supplier.getOgrn());
            Optional<String> guid = Optional.empty();
            if (inn.isPresent()) {
                guid = getSupplierByInn(inn.get());
            }
            if (guid.isPresent()) return guid;
            if (ogrn.isPresent()) {
                guid = getSupplierByOgrn(ogrn.get());
            }
            if (guid.isPresent()) return guid;
            guid = Optional.of(commonUtils.generateGuid());
            jdbcTemplate.update(
                sql,
                guid.get(),
                inn.orElse(null),
                supplier.getName(),
                validateKpp(supplier.getKpp()).orElse(null),
                ogrn.orElse(null),
                repositoryUtils.mapSupplierType(supplier.getType()),
                supplier.getAddress()
            );
            return guid;
        } catch (Exception e) {
            logger.error("Error during inserting in purchase_contract_supplier", e);
            return Optional.empty();
        }
    }

    private Optional<String> validateInn(String inn) {
        if (inn == null || inn.equals("0000000000")) {
            return Optional.empty();
        }
        return Optional.of(inn);
    }

    private Optional<String> validateOgrn(String ogrn) {
        if (ogrn == null || ogrn.equals("0000000000000")) {
            return Optional.empty();
        }
        return Optional.of(ogrn);
    }

    private Optional<String> validateKpp(String kpp) {
        if (kpp == null || kpp.equals("000000000")) {
            return Optional.empty();
        }
        if (kpp.length() > 9) {
            logger.warn("Kpp {} is more than 9", kpp);
            return Optional.empty();
        }
        return Optional.of(kpp);
    }

    private Optional<String> getSupplierByInn(String inn) {
        String sql = "SELECT guid FROM supplier_info WHERE inn = ?";
        List<String> result = jdbcTemplate.query(sql, new Object[]{inn}, (rs, rowNum) -> rs.getString("guid"));
        if (result.size() == 0) {
            return Optional.empty();
        } else {
            return Optional.of(result.get(0));
        }
    }

    private Optional<String> getSupplierByOgrn(String ogrn) {
        String sql = "SELECT guid FROM supplier_info WHERE ogrn = ?";
        List<String> result = jdbcTemplate.query(sql, new Object[]{ogrn}, (rs, rowNum) -> rs.getString("guid"));
        if (result.size() == 0) {
            return Optional.empty();
        } else {
            return Optional.of(result.get(0));
        }
    }
}
