package ru.homyakin.zakupki.database.purchase_protocol;

import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.homyakin.zakupki.models._223fz.types.ApplicationLotCriteriaType;
import ru.homyakin.zakupki.models._223fz.types.LotApplicationsCriteriaType;

@Component
public class ProtocolLotCriteriaRepository {
    private final static Logger logger = LoggerFactory.getLogger(ProtocolLotCriteriaRepository.class);
    private final static String INSERT =
        """
        insert into protocol_lot_criteria (guid, name, description, max_value, weight)
         values (?, ?, ?, ?, ?);
        """;

    private final JdbcTemplate jdbcTemplate;

    public ProtocolLotCriteriaRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void insert(LotApplicationsCriteriaType criteria) {
        try {
            jdbcTemplate.update(
                INSERT,
                criteria.getGuid(),
                criteria.getName(),
                criteria.getDescription(),
                criteria.getMaxValue(),
                criteria.getWeight()
            );
        } catch (DuplicateKeyException ignored) {
        } catch (Exception e) {
            logger.error("Error during inserting into protocol_lot_criteria", e);
        }
    }

    public void insert(ApplicationLotCriteriaType criteria) {
        try {
            jdbcTemplate.update(
                INSERT,
                criteria.getLotApplcationsCriteria().getLotApplcationsCriteriaGuid(),
                criteria.getLotApplcationsCriteria().getName(),
                criteria.getDescription(),
                criteria.getValue(),
                null
            );
        } catch (DuplicateKeyException ignored) {
            logger.warn("Same criteria guid {}", criteria.getLotApplcationsCriteria().getLotApplcationsCriteriaGuid());
        } catch (Exception e) {
            logger.error("Error during inserting into protocol_lot_criteria", e);
        }
    }
}
