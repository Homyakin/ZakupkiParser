package ru.homyakin.zakupki.database;

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

    public LongTermVolumesRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void insert(LongTermVolumeType longTermVolume, Boolean isSmb, String planItemGuid) {
        String sql = "INSERT INTO zakupki.long_term_volumes (plan_item_guid, is_smb, volume," +
            "volume_rub, currency_code, exchange_rate, exchange_rate_date)" +
            "VALUES" +
            "(?, ?, ?, ?, ?, ?, ?);";
        try {
            jdbcTemplate.update(sql,
                planItemGuid,
                RepositoryService.convertBoolean(isSmb),
                longTermVolume.getVolume(),
                longTermVolume.getVolumeRub(),
                RepositoryService.getCurrencyCode(longTermVolume.getCurrency()),
                longTermVolume.getExchangeRate(),
                longTermVolume.getExchangeRateDate()
            );
        } catch (Exception e) {
            logger.error("Eternal error", e);
        }
        for (LongTermVolumeDetailType i : longTermVolume.getDetails().getLongTermVolumeDetail()) {
            insertToLongTermVolumeDetail(i, planItemGuid);
        }
    }

    private void insertToLongTermVolumeDetail(LongTermVolumeDetailType longTermVolumeDetail, String guid) {
        String sql = "INSERT INTO zakupki.long_term_volume_detail (long_term_value_guid, year," +
            "summ, summ_rub)" +
            "VALUES" +
            "(?, ?, ?, ?);";
        jdbcTemplate.update(sql,
            guid,
            longTermVolumeDetail.getYear(),
            longTermVolumeDetail.getSumm(),
            longTermVolumeDetail.getSummRub()
        );
    }
}
