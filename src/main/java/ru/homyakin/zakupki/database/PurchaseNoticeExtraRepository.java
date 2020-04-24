package ru.homyakin.zakupki.database;

import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.homyakin.zakupki.models._223fz.types.BaseExtendFieldType;

@Component
public class PurchaseNoticeExtraRepository {
    private static final Logger logger = LoggerFactory.getLogger(PurchaseNoticeExtraRepository.class);
    private final JdbcTemplate jdbcTemplate;
    private final RepositoryService repositoryService;

    public PurchaseNoticeExtraRepository(DataSource dataSource, RepositoryService repositoryService) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.repositoryService = repositoryService;
    }

    public void insert(BaseExtendFieldType field, String noticeGuid) {
        String sql = "INSERT INTO zakupki.purchase_notice_extra (purchase_notice_data_guid, integr_code," +
            "description, text, number_int, number, boolean, datetime, date, url, nsi_code, nsi_name)" +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            var value = field.getValue();
            var nsi = getNsi(field);
            jdbcTemplate.update(
                sql,
                noticeGuid,
                field.getIntegrCode(),
                field.getDescription(),
                value.getText(),
                value.getInteger(),
                value.getNumber(),
                repositoryService.convertBoolean(value.isBoolean()),
                repositoryService.convertFromXMLGregorianCalendarToLocalDateTime(value.getDateTime()),
                repositoryService.convertFromXMLGregorianCalendarToLocalDate(value.getDate()),
                value.getUrl(),
                nsi.code,
                nsi.name
            );
        } catch (Exception e) {
            logger.error("Error during inserting purchase notice extra", e);
        }
    }

    private Nsi getNsi(BaseExtendFieldType field) {
        var name = field.getType().value();
        return switch (field.getType()) {
            case OKDP -> new Nsi(field.getValue().getNsi().getOkdp().getCode(), name);
            case OKPD_2 -> new Nsi(field.getValue().getNsi().getOkpd2().getCode(), name);
            case OKVED -> new Nsi(field.getValue().getNsi().getOkved().getCode(), name);
            case OKVED_2 -> new Nsi(field.getValue().getNsi().getOkved2().getCode(), name);
            case CURRENCY -> new Nsi(repositoryService.getCurrencyCode(field.getValue().getNsi().getCurrency()), name);
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
