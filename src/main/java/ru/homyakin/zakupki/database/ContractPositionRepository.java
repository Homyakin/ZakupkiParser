package ru.homyakin.zakupki.database;

import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.homyakin.zakupki.models._223fz.contract.PositionType;

@Component
public class ContractPositionRepository {
    private static final Logger logger = LoggerFactory.getLogger(ContractPositionRepository.class);
    private final JdbcTemplate jdbcTemplate;
    private final RepositoryService repositoryService;

    public ContractPositionRepository(DataSource dataSource, RepositoryService repositoryService) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        this.repositoryService = repositoryService;
    }

    public void insert(PositionType position, String contractGuid) {
        String sql = "INSERT INTO zakupki.contract_position (contract_guid, ordinal_number, guid," +
            "name, okdp_code, okdp_name, okpd_code, okpd_name, okpd2_code, okpd2_name, country_code," +
            "producer_country, impossible_to_determine_attr, okei_code, okei_name, qty)" +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            ClassifierRepository.Classifier okdp = repositoryService.getClassifier(position.getOkdp());
            ClassifierRepository.Classifier okpd = repositoryService.getClassifier(position.getOkpd());
            ClassifierRepository.Classifier okpd2 = repositoryService.getClassifier(position.getOkpd2());
            ClassifierRepository.Classifier okei = repositoryService.getClassifier(position.getOkei());

            jdbcTemplate.update(
                sql,
                contractGuid,
                position.getOrdinalNumber(),
                position.getGuid(),
                repositoryService.removeExtraSpaces(position.getName()),
                repositoryService.getClassifierCode(okdp),
                repositoryService.getClassifierName(okdp),
                repositoryService.getClassifierCode(okpd),
                repositoryService.getClassifierName(okpd),
                repositoryService.getClassifierCode(okpd2),
                repositoryService.getClassifierName(okpd2),
                repositoryService.getCountryCode(position.getCountry()),
                repositoryService.convertBoolean(position.isProducerCountry()),
                repositoryService.convertBoolean(position.isImpossibleToDetermineAttr()),
                repositoryService.getClassifierCode(okei),
                repositoryService.getClassifierName(okei),
                position.getQty()
            );
        } catch (RuntimeException e) {
            logger.error("Internal database error");
        }
    }
}
