package ru.homyakin.zakupki.database;

import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.homyakin.zakupki.models._223fz.purchase.PurchaseNoticeDataType;

@Component
public class PlacingProcedureRepository {
    private static final Logger logger = LoggerFactory.getLogger(PlacingProcedureRepository.class);
    private final JdbcTemplate jdbcTemplate;
    private final RepositoryService repositoryService;

    public PlacingProcedureRepository(DataSource dataSource, RepositoryService repositoryService) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.repositoryService = repositoryService;
    }

    public void insert(PurchaseNoticeDataType.PlacingProcedure placingProcedure, String noticeGuid) {
        if (placingProcedure == null) return;
        String sql = "INSERT INTO zakupki.placing_procedure (purchase_notice_data_guid, examination_place," +
            "examination_date_time, summingup_date_time, summingup_place) VALUES (?, ?, ?, ?, ?)";
        try {
            jdbcTemplate.update(
                sql,
                noticeGuid,
                placingProcedure.getExaminationPlace(),
                repositoryService.convertFromXMLGregorianCalendarToLocalDateTime(placingProcedure.getExaminationDateTime()),
                repositoryService.convertFromXMLGregorianCalendarToLocalDateTime(placingProcedure.getSummingupDateTime()),
                placingProcedure.getSummingupPlace()
            );
        } catch (Exception e) {
            logger.error("Error during inserting placing procedure", e);
        }
    }
}
