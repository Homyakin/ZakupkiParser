package ru.homyakin.zakupki.database.contract;

import java.util.List;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.homyakin.zakupki.database.ClassifierService;
import ru.homyakin.zakupki.models._223fz.contract.PositionType;
import ru.homyakin.zakupki.utils.CommonUtils;
import ru.homyakin.zakupki.utils.RepositoryUtils;

@Component
public class ContractPositionRepository {
    private static final Logger logger = LoggerFactory.getLogger(ContractPositionRepository.class);
    private final JdbcTemplate jdbcTemplate;
    private final RepositoryUtils repositoryUtils;
    private final ClassifierService classifierService;
    private final CommonUtils commonUtils;

    public ContractPositionRepository(
        DataSource dataSource,
        RepositoryUtils repositoryUtils,
        ClassifierService classifierService,
        CommonUtils commonUtils
    ) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        this.repositoryUtils = repositoryUtils;
        this.classifierService = classifierService;
        this.commonUtils = commonUtils;
    }

    public void insert(PositionType position, String contractGuid) {
        String sql = "INSERT INTO zakupki.contract_position (guid," +
            "name, okdp_code, okdp_name, okpd_code, okpd_name, okpd2_code, okpd2_name, country_code," +
            "producer_country, impossible_to_determine_attr, okei_code, okei_name, qty)" +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            var guid = getGuid(position.getGuid());
            if (!checkPosition(guid)) {
                jdbcTemplate.update(
                    sql,
                    guid,
                    repositoryUtils.removeExtraSpaces(position.getName()),
                    classifierService.getClassifierCode(position.getOkdp()),
                    classifierService.getClassifierName(position.getOkdp()),
                    classifierService.getClassifierCode(position.getOkpd()),
                    classifierService.getClassifierName(position.getOkpd()),
                    classifierService.getClassifierCode(position.getOkpd2()),
                    classifierService.getClassifierName(position.getOkpd2()),
                    repositoryUtils.getCountryCode(position.getCountryManufacturer()), //TODO страна происхождение плюс страна производитель????
                    repositoryUtils.convertBoolean(position.isProducerCountry()),
                    repositoryUtils.convertBoolean(position.isImpossibleToDetermineAttr()),
                    classifierService.getClassifierCode(position.getOkei()),
                    classifierService.getClassifierName(position.getOkei()),
                    position.getQty()
                );
            }
            insertPositionToContract(guid, contractGuid);
        } catch (RuntimeException e) {
            logger.error("Error during insert in contract_position", e);
        }
    }

    private void insertPositionToContract(String positionGuid, String contractGuid) {
        var sql = "INSERT INTO zakupki.position_to_contract (contract_guid, position_guid) VALUES (?, ?)";
        try {
            jdbcTemplate.update(
                sql,
                contractGuid,
                positionGuid
            );
        } catch (Exception e) {
            logger.error("Error during insert in position_to_contract", e);
        }
    }

    private boolean checkPosition(String guid) {
        String sql = "SELECT guid FROM zakupki.contract_position WHERE guid = ?";
        List<String> result = jdbcTemplate.query(sql, new Object[]{guid}, (rs, rowNum) -> rs.getString("guid"));
        return result.size() != 0;
    }

    private String getGuid(String guid) {
        if (guid != null) {
            return guid;
        } else {
            return String.format("autogenerated-%s", commonUtils.generateGuid());
        }
    }
}