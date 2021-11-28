package ru.homyakin.zakupki.database.contract;

import java.util.List;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
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
    private final ClassifierService classifierService;

    public ContractPositionRepository(
        DataSource dataSource,
        ClassifierService classifierService
    ) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        this.classifierService = classifierService;
    }

    public void insert(PositionType position, String contractGuid) {
        String sql = "INSERT INTO zakupki.contract_position (guid," +
            "name, okdp_code, okdp_name, okpd_code, okpd_name, okpd2_code, okpd2_name, country_code," +
            "producer_country, impossible_to_determine_attr, okei_code, okei_name, qty, unit_price, currency_code, " +
            "exchange_rate, rub_unit_price, source_info)" +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        var guid = CommonUtils.validateAndGetGuid(position.getGuid());
        try {
            if (!checkPosition(guid)) {
                jdbcTemplate.update(
                    sql,
                    guid,
                    RepositoryUtils.removeExtraSpaces(position.getName()),
                    classifierService.getClassifierCode(position.getOkdp()),
                    classifierService.getClassifierName(position.getOkdp()),
                    classifierService.getClassifierCode(position.getOkpd()),
                    classifierService.getClassifierName(position.getOkpd()),
                    classifierService.getClassifierCode(position.getOkpd2()),
                    classifierService.getClassifierName(position.getOkpd2()),
                    RepositoryUtils.getCountryCode(position.getCountryManufacturer()), //TODO страна происхождение плюс страна производитель????
                    RepositoryUtils.convertBoolean(position.isProducerCountry()),
                    RepositoryUtils.convertBoolean(position.isImpossibleToDetermineAttr()),
                    classifierService.getClassifierCode(position.getOkei()),
                    classifierService.getClassifierName(position.getOkei()),
                    position.getQty(),
                    position.getUnitPrice(),
                    RepositoryUtils.getCurrencyCode(position.getCurrency()),
                    position.getExchangeRate(),
                    position.getRubUnitPrice(),
                    position.getSourceInfo() != null ? position.getSourceInfo().value() : null
                );
            }
        } catch (DuplicateKeyException ignored) {
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
        } catch (DuplicateKeyException ignored) {
        } catch (Exception e) {
            logger.error("Error during insert in position_to_contract", e);
        }
    }

    private boolean checkPosition(String guid) {
        String sql = "SELECT guid FROM zakupki.contract_position WHERE guid = ?";
        List<String> result = jdbcTemplate.query(sql, new Object[]{guid}, (rs, rowNum) -> rs.getString("guid"));
        return result.size() != 0;
    }
}
