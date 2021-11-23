package ru.homyakin.zakupki.database.purchase_plan;

import java.util.List;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.homyakin.zakupki.models._223fz.purchaseplan.LongTermVolumeDetailType;
import ru.homyakin.zakupki.models._223fz.purchaseplan.LongTermVolumeType;
import ru.homyakin.zakupki.utils.RepositoryUtils;

@Component
public class LongTermVolumesRepository {
    private static final Logger logger = LoggerFactory.getLogger(LongTermVolumesRepository.class);
    private final JdbcTemplate jdbcTemplate;
    private final RepositoryUtils repositoryUtils;

    public LongTermVolumesRepository(DataSource dataSource, RepositoryUtils repositoryUtils) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        this.repositoryUtils = repositoryUtils;
    }

    public void insert(LongTermVolumeType longTermVolume, Boolean isSmb, String planItemGuid) {
        String sql = "INSERT INTO zakupki.long_term_volumes (plan_item_guid, is_smb, volume," +
            "volume_rub, currency_code, exchange_rate, exchange_rate_date)" +
            "VALUES" +
            "(?, ?, ?, ?, ?, ?, ?);";
        try {
            jdbcTemplate.update(
                sql,
                repositoryUtils.removeExtraSpaces(planItemGuid),
                repositoryUtils.convertBoolean(isSmb),
                longTermVolume.getVolume(),
                longTermVolume.getVolumeRub(),
                repositoryUtils.removeExtraSpaces(repositoryUtils.getCurrencyCode(longTermVolume.getCurrency())),
                longTermVolume.getExchangeRate(),
                repositoryUtils.convertFromXMLGregorianCalendarToLocalDate(longTermVolume.getExchangeRateDate())
            );
        } catch (Exception e) {
            logger.error("Error during inserting into long term volumes", e);
        }
        if (longTermVolume.getDetails() != null) {
            for (var i : longTermVolume.getDetails().getLongTermVolumeDetail()) {
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
            jdbcTemplate.update(
                sql,
                longTermVolume.getVolume(),
                longTermVolume.getVolumeRub(),
                repositoryUtils.removeExtraSpaces(repositoryUtils.getCurrencyCode(longTermVolume.getCurrency())),
                longTermVolume.getExchangeRate(),
                repositoryUtils.convertFromXMLGregorianCalendarToLocalDate(longTermVolume.getExchangeRateDate()),
                repositoryUtils.removeExtraSpaces(planItemGuid),
                repositoryUtils.convertBoolean(isSmb)
            );
            if (longTermVolume.getDetails() != null) {
                for (var i : longTermVolume.getDetails().getLongTermVolumeDetail()) {
                    insertToLongTermVolumeDetail(i, planItemGuid, isSmb);
                }
            }
        } catch (Exception e) {
            logger.error("Error during updating long term volumes", e);
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
            jdbcTemplate.update(
                sql,
                guid,
                longTermVolumeDetail.getYear(),
                repositoryUtils.convertBoolean(isSmb),
                longTermVolumeDetail.getSumm(),
                longTermVolumeDetail.getSummRub()
            );
        } catch (Exception e) {
            logger.error("Error during inserting into long term volume detail", e);
        }
    }

    private void updateLongTermVolumeDetail(LongTermVolumeDetailType longTermVolumeDetail, String guid, Boolean isSmb) {
        try {
            String sql = "UPDATE long_term_volume_detail SET summ = ?, summ_rub = ? WHERE long_term_value_guid = ?" +
                " and year = ? and is_smb = ?";
            jdbcTemplate.update(
                sql,
                longTermVolumeDetail.getSumm(),
                longTermVolumeDetail.getSummRub(),
                repositoryUtils.removeExtraSpaces(guid),
                longTermVolumeDetail.getYear(),
                repositoryUtils.convertBoolean(isSmb)
            );
        } catch (Exception e) {
            logger.error("Error during updating into long term volume details", e);
        }
    }

    private boolean checkLongTermVolume(String guid, Boolean isSmb) {
        String sql = "SELECT plan_item_guid FROM long_term_volumes WHERE plan_item_guid = ? and is_smb = ?";
        List<String> result = jdbcTemplate.query(sql,
            new Object[]{guid, repositoryUtils.convertBoolean(isSmb)},
            (rs, rowNum) -> rs.getString("plan_item_guid")
        );
        return result.size() != 0;
    }

    private boolean checkLongTermVolumeDetail(String guid, Boolean isSmb, Integer year) {
        String sql = "SELECT long_term_value_guid FROM long_term_volume_detail WHERE long_term_value_guid = ?" +
            " and year = ? and is_smb = ?";
        List<String> result = jdbcTemplate.query(sql,
            new Object[]{guid, year, repositoryUtils.convertBoolean(isSmb)},
            (rs, rowNum) -> rs.getString("long_term_value_guid")
        );
        return result.size() != 0;
    }
}
