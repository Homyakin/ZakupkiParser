package ru.homyakin.zakupki.database;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.homyakin.zakupki.documentsinfo._223fz.purchaseplan.LongTermVolumeDetailType;
import ru.homyakin.zakupki.documentsinfo._223fz.purchaseplan.LongTermVolumeType;

import javax.sql.DataSource;

@Component
public class LongTermVolumesRepository {
    private static final Logger logger = LoggerFactory.getLogger(LongTermVolumesRepository.class);
    private final JdbcTemplate jdbcTemplate;
    private final RepositoryService repositoryService;

    public LongTermVolumesRepository(DataSource dataSource, RepositoryService repositoryService) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        this.repositoryService =repositoryService;
    }

    public void insert(LongTermVolumeType longTermVolume, Boolean isSmb, String planItemGuid) {
        String sql = "INSERT INTO zakupki.long_term_volumes (plan_item_guid, is_smb, volume," +
            "volume_rub, currency_code, exchange_rate, exchange_rate_date)" +
            "VALUES" +
            "(?, ?, ?, ?, ?, ?, ?);";
        try {
            jdbcTemplate.update(sql,
                repositoryService.removeExtraSpaces(planItemGuid),
                repositoryService.convertBoolean(isSmb),
                longTermVolume.getVolume(),
                longTermVolume.getVolumeRub(),
                repositoryService.removeExtraSpaces(repositoryService.getCurrencyCode(longTermVolume.getCurrency())),
                longTermVolume.getExchangeRate(),
                repositoryService.convertFromXMLGregorianCalendarToLocalDate(longTermVolume.getExchangeRateDate())
            );
        } catch (Exception e) {
            logger.error("Eternal error", e);
        }
        if (longTermVolume.getDetails() != null) {
            for (LongTermVolumeDetailType i : longTermVolume.getDetails().getLongTermVolumeDetail()) {
                insertToLongTermVolumeDetail(i, planItemGuid, isSmb);
            }
        }
    }

    public void update(LongTermVolumeType longTermVolume, Boolean isSmb, String planItemGuid) {
        if (!checkLongTermVolume(planItemGuid, isSmb)) {
            insert(longTermVolume, isSmb, planItemGuid);
            return;
        }
        String sql = "UPDATE long_term_volumes SET  volume = ?, volume_rub = ?, currency_code = ?, exchange_rate = ?, " +
            "exchange_rate_date = ? WHERE plan_item_guid = ? and is_smb = ?";
        try {
            jdbcTemplate.update(sql,
                longTermVolume.getVolume(),
                longTermVolume.getVolumeRub(),
                repositoryService.removeExtraSpaces(repositoryService.getCurrencyCode(longTermVolume.getCurrency())),
                longTermVolume.getExchangeRate(),
                repositoryService.convertFromXMLGregorianCalendarToLocalDate(longTermVolume.getExchangeRateDate()),
                repositoryService.removeExtraSpaces(planItemGuid),
                repositoryService.convertBoolean(isSmb)
            );
            if (longTermVolume.getDetails() != null) {
                for (LongTermVolumeDetailType i : longTermVolume.getDetails().getLongTermVolumeDetail()) {
                    insertToLongTermVolumeDetail(i, planItemGuid, isSmb);
                }
            }
        } catch (Exception e) {
            logger.error("Eternal error", e);
        }

    }

    private void insertToLongTermVolumeDetail(LongTermVolumeDetailType longTermVolumeDetail, String guid, Boolean isSmb) {
        if (checkLongTermVolumeDetail(guid, isSmb, longTermVolumeDetail.getYear())) {
            updateLongTermVolumeDetail(longTermVolumeDetail, guid, isSmb);
            return;
        }
        try {
            String sql = "INSERT INTO zakupki.long_term_volume_detail (long_term_value_guid, year, is_smb, " +
                "summ, summ_rub)" +
                "VALUES" +
                "(?, ?, ?, ?, ?);";
            jdbcTemplate.update(sql,
                guid,
                longTermVolumeDetail.getYear(),
                repositoryService.convertBoolean(isSmb),
                longTermVolumeDetail.getSumm(),
                longTermVolumeDetail.getSummRub()
            );
        } catch (Exception e) {
            logger.error("Eternal error", e);
        }
    }

    private void updateLongTermVolumeDetail(LongTermVolumeDetailType longTermVolumeDetail, String guid, Boolean isSmb) {
        try {
            String sql = "UPDATE long_term_volume_detail SET summ = ?, summ_rub = ? WHERE long_term_value_guid = ?" +
                " and year = ? and is_smb = ?";
            jdbcTemplate.update(sql,
                longTermVolumeDetail.getSumm(),
                longTermVolumeDetail.getSummRub(),
                repositoryService.removeExtraSpaces(guid),
                longTermVolumeDetail.getYear(),
                repositoryService.convertBoolean(isSmb)
            );
        } catch (Exception e) {
            logger.error("Eternal error", e);
        }
    }

    private boolean checkLongTermVolume(String guid, Boolean isSmb) {
        String sql = "SELECT plan_item_guid FROM long_term_volumes WHERE plan_item_guid = ? and is_smb = ?";
        List<String> result = jdbcTemplate.query(sql,
            new Object[]{guid, repositoryService.convertBoolean(isSmb)},
            (rs, rowNum) -> rs.getString("plan_item_guid")
        );
        return result.size() != 0;
    }

    private boolean checkLongTermVolumeDetail(String guid, Boolean isSmb, Integer year) {
        String sql = "SELECT long_term_value_guid FROM long_term_volume_detail WHERE long_term_value_guid = ?" +
            " and year = ? and is_smb = ?";
        List<String> result = jdbcTemplate.query(sql,
            new Object[]{guid, year, repositoryService.convertBoolean(isSmb)},
            (rs, rowNum) -> rs.getString("long_term_value_guid")
        );
        return result.size() != 0;
    }
}
