package ru.homyakin.zakupki.database.purchase_protocol;

import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.homyakin.zakupki.models._223fz.purchase.LotParameters;
import ru.homyakin.zakupki.models._223fz.purchase.ProtocolLotType;
import ru.homyakin.zakupki.utils.CommonUtils;
import ru.homyakin.zakupki.utils.RepositoryUtils;

@Component
public class ProtocolLotRepository {
    private final static Logger logger = LoggerFactory.getLogger(ProtocolLotRepository.class);
    private final static String INSERT =
        """
        insert into protocol_lot (guid, ordinal_number, subject, currency_code, initial_sum, price_formula, commodity_price,
         max_contract_price, initial_sum_info, non_price, offer_currency_code, offer_info, assessment_order)
         values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);
        """;
    private final JdbcTemplate jdbcTemplate;

    public ProtocolLotRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void insert(ProtocolLotType lot, LotParameters lotParameters) {
        try {
            lot.setGuid(CommonUtils.validateAndGetGuid(lot.getGuid()));
            jdbcTemplate.update(
                INSERT,
                lot.getGuid(),
                lot.getOrdinalNumber(),
                lot.getSubject(),
                RepositoryUtils.getCurrencyCode(lot.getCurrency()),
                lot.getInitialSum(),
                lot.getPriceFormula(),
                lot.getCommodityPrice(),
                lot.getMaxContractPrice(),
                lot.getInitialSumInfo(),
                lotParameters != null ? RepositoryUtils.convertBoolean(lotParameters.isNonPrice()) : null,
                lotParameters != null ? RepositoryUtils.getCurrencyCode(lotParameters.getCurrency()) : null,
                lotParameters != null ? lotParameters.getOfferInfo() : null,
                lotParameters != null ? lotParameters.getAssessmentOrder() : null
            );
        } catch (DuplicateKeyException ignored) {
        } catch (Exception e) {
            logger.error("Error during inserting into protocol_lot", e);
        }

    }
}
