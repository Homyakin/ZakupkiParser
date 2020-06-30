package ru.homyakin.zakupki.database;

import java.util.List;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.homyakin.zakupki.exceptions.NoXmlnsException;
import ru.homyakin.zakupki.models._223fz.types.LotPlanPositionType;
import ru.homyakin.zakupki.models._223fz.types.PlanInfoType;

@Component
public class PlanPositionRepository {
    private static final Logger logger = LoggerFactory.getLogger(PlanPositionRepository.class);
    private final JdbcTemplate jdbcTemplate;
    private final RepositoryService repositoryService;

    public PlanPositionRepository(DataSource dataSource, RepositoryService repositoryService) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        this.repositoryService = repositoryService;
    }

    public void insert(PlanInfoType position) {
        if (position == null) return;
        if (position.getPositionGuid() == null) throw new NoXmlnsException("No xmlns");
        String sql = "INSERT INTO zakupki.plan_position (guid, plan_guid, plan_registration_number," +
            "position_number, lot_plan_position, contract_subject)" +
            "VALUES (?, ?, ?, ?, ?, ?)";
        try {
            jdbcTemplate.update(
                sql,
                position.getPositionGuid(),
                position.getPlanGuid(),
                position.getPlanRegistrationNumber(),
                position.getPositionNumber(),
                getLotPlanPosition(position.getLotPlanPosition()),
                repositoryService.removeExtraSpaces(position.getContractSubject())
            );
        } catch (DuplicateKeyException e) {

        } catch (RuntimeException e) {
            logger.error("Internal database error", e);
        }
    }

    private String getLotPlanPosition(LotPlanPositionType lotPlanPosition) {
        if (lotPlanPosition == null) return null;
        else return lotPlanPosition.value();
    }
}
