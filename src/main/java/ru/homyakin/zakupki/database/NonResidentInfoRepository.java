package ru.homyakin.zakupki.database;

import java.util.Optional;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.homyakin.zakupki.models._223fz.types.NonResidentInfoType;
import ru.homyakin.zakupki.utils.CommonUtils;
import ru.homyakin.zakupki.utils.RepositoryUtils;

@Component
public class NonResidentInfoRepository {
    private final static Logger logger = LoggerFactory.getLogger(NonResidentInfoRepository.class);
    private final JdbcTemplate jdbcTemplate;
    private final CommonUtils commonUtils;
    private final RepositoryUtils repositoryUtils;

    public NonResidentInfoRepository(
        DataSource dataSource,
        CommonUtils commonUtils,
        RepositoryUtils repositoryUtils
    ) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        this.commonUtils = commonUtils;
        this.repositoryUtils = repositoryUtils;
    }

    public Optional<String> insert(NonResidentInfoType nonResidentInfo) {
        var sql = "INSERT INTO zakupki.non_resident_info (guid, info, type, name, code, additional_code, country_code," +
            "address) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        if (nonResidentInfo == null) return Optional.empty();
        var guid = commonUtils.generateGuid();
        try {
            jdbcTemplate.update(
                sql,
                guid,
                nonResidentInfo.getInfo(),
                repositoryUtils.mapSupplierType(nonResidentInfo.getType()),
                nonResidentInfo.getName(),
                nonResidentInfo.getCode(),
                nonResidentInfo.getAdditionalCode(),
                repositoryUtils.getCountryCode(nonResidentInfo.getCountry()),
                nonResidentInfo.getAddress()
            );
            return Optional.of(guid);
        } catch (Exception e) {
            logger.error("Error during inserting in non_resident_info", e);
            return Optional.empty();
        }
    }
}
