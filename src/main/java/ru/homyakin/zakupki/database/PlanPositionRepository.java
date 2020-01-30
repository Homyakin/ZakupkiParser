package ru.homyakin.zakupki.database;

import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.homyakin.zakupki.models._223fz.types.LotPlanPositionType;
import ru.homyakin.zakupki.models._223fz.types.PlanInfoType;

@Component
public class PlanPositionRepository {
    private static final Logger logger = LoggerFactory.getLogger(ContractRepository.class);
    private final JdbcTemplate jdbcTemplate;
    private final RepositoryService repositoryService;

    public PlanPositionRepository(DataSource dataSource, RepositoryService repositoryService) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        this.repositoryService = repositoryService;
    }

    public void insert(PlanInfoType position) {
        String sql = "INSERT INTO zakupki.plan_position (guid, plan_guid, plan_registration_number," +
            "position_number, lot_plan_position, contract_subject)" +
            "VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(
            sql,
            position.getPositionGuid(),
            position.getPlanGuid(),
            position.getPlanRegistrationNumber(),
            position.getPositionNumber(),
            getLotPlanPosition(position.getLotPlanPosition()),
            position.getContractSubject()
        );
    }

    private String getLotPlanPosition(LotPlanPositionType lotPlanPosition) {
        if (lotPlanPosition == null) return null;
        else return lotPlanPosition.value();
    }
}
