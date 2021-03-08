package ru.homyakin.zakupki.database.purchase_notice;

import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.homyakin.zakupki.models._223fz.purchase.PurchaseNoticeDataType;
import ru.homyakin.zakupki.utils.RepositoryUtils;

@Component
public class PlacingProcedureRepository {
    private static final Logger logger = LoggerFactory.getLogger(PlacingProcedureRepository.class);
    private final JdbcTemplate jdbcTemplate;
    private final RepositoryUtils repositoryUtils;

    public PlacingProcedureRepository(DataSource dataSource, RepositoryUtils repositoryUtils) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.repositoryUtils = repositoryUtils;
    }

    public void insert(PurchaseNoticeDataType.PlacingProcedure placingProcedure, String noticeGuid) {
        if (placingProcedure == null) return;
        String sql = "INSERT INTO zakupki.placing_procedure (purchase_notice_guid, examination_place," +
            "examination_date_time, summingup_date_time, summingup_place) VALUES (?, ?, ?, ?, ?)";
        try {
            jdbcTemplate.update(
                sql,
                noticeGuid,
                placingProcedure.getExaminationPlace(),
                repositoryUtils.convertFromXMLGregorianCalendarToLocalDateTime(placingProcedure.getExaminationDateTime()),
                repositoryUtils.convertFromXMLGregorianCalendarToLocalDateTime(placingProcedure.getSummingupDateTime()),
                placingProcedure.getSummingupPlace()
            );
        } catch (Exception e) {
            logger.error("Error during inserting placing procedure", e);
        }
    }
}
