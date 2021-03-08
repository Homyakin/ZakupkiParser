package ru.homyakin.zakupki.database.purchase_notice;

import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.homyakin.zakupki.models._223fz.types.BaseExtendFieldType;
import ru.homyakin.zakupki.utils.RepositoryUtils;

@Component
public class LotExtraRepository {
    private static final Logger logger = LoggerFactory.getLogger(LotExtraRepository.class);
    private final JdbcTemplate jdbcTemplate;
    private final RepositoryUtils repositoryUtils;

    public LotExtraRepository(DataSource dataSource, RepositoryUtils repositoryUtils) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.repositoryUtils = repositoryUtils;
    }

    public void insert(BaseExtendFieldType field, String lotGuid) {
        String sql = "INSERT INTO zakupki.lot_extra (purchase_notice_lot_guid, integr_code, description," +
            "text, number_int, number, boolean, datetime, date, url, nsi_code, nsi_name)" +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            var value = field.getValue();
            var nsi = getNsi(field);
            jdbcTemplate.update(
                sql,
                lotGuid,
                field.getIntegrCode(),
                field.getDescription(),
                value.getText(),
                value.getInteger(),
                value.getNumber(),
                repositoryUtils.convertBoolean(value.isBoolean()),
                repositoryUtils.convertFromXMLGregorianCalendarToLocalDateTime(value.getDateTime()),
                repositoryUtils.convertFromXMLGregorianCalendarToLocalDate(value.getDate()),
                value.getUrl(),
                nsi.code,
                nsi.name
            );
        } catch (Exception e) {
            logger.error("Error during inserting lot extra", e);
        }
    }

    private Nsi getNsi(BaseExtendFieldType field) {
        var name = field.getType().value();
        return switch (field.getType()) {
            case OKDP -> new Nsi(field.getValue().getNsi().getOkdp().getCode(), name);
            case OKPD_2 -> new Nsi(field.getValue().getNsi().getOkpd2().getCode(), name);
            case OKVED -> new Nsi(field.getValue().getNsi().getOkved().getCode(), name);
            case OKVED_2 -> new Nsi(field.getValue().getNsi().getOkved2().getCode(), name);
            case CURRENCY -> new Nsi(repositoryUtils.getCurrencyCode(field.getValue().getNsi().getCurrency()), name);
            default -> new Nsi(null, null);
        };
    }

    private static class Nsi {
        private final String code;
        private final String name;

        public Nsi(String code, String name) {
            this.code = code;
            this.name = name;
        }
    }
}
