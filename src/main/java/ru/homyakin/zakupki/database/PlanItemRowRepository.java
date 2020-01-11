package ru.homyakin.zakupki.database;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.homyakin.zakupki.documentsinfo._223fz.purchaseplan.InnovationPlanDataItemRowType;
import ru.homyakin.zakupki.documentsinfo._223fz.purchaseplan.PurchasePlanDataItemRowType;
import ru.homyakin.zakupki.documentsinfo._223fz.types.OkdpProductType;
import ru.homyakin.zakupki.documentsinfo._223fz.types.Okpd2ProductType;
import ru.homyakin.zakupki.documentsinfo._223fz.types.Okved2ProductType;
import ru.homyakin.zakupki.documentsinfo._223fz.types.OkvedProductType;

import javax.sql.DataSource;

@Component
public class PlanItemRowRepository {
    private static final Logger logger = LoggerFactory.getLogger(CustomerRepository.class);
    private final JdbcTemplate jdbcTemplate;

    public PlanItemRowRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void insert(PurchasePlanDataItemRowType purchasePlanDataItemRow, String planItemGuid) {
        String sql = "INSERT INTO zakupki.purchase_plan_item_row (plan_item_guid, ordinal_number, additional_info," +
            "okdp_code, okpd2_code, okved_code, okved2_code, okato, region, impossible_to_determine_attr," +
            "okei_code, qty)" +
            "VALUES" +
            "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        String okeiCode = purchasePlanDataItemRow.getOkei() != null ? purchasePlanDataItemRow.getOkei().getCode() : null;
        jdbcTemplate.update(sql,
            planItemGuid,
            purchasePlanDataItemRow.getOrdinalNumber(),
            purchasePlanDataItemRow.getAdditionalInfo(),
            getOkdpCode(purchasePlanDataItemRow.getOkdp()),
            getOkpd2Code(purchasePlanDataItemRow.getOkpd2()),
            getOkvedCode(purchasePlanDataItemRow.getOkved()),
            getOkved2Code(purchasePlanDataItemRow.getOkved2()),
            purchasePlanDataItemRow.getOkato(),
            purchasePlanDataItemRow.getRegion(),
            RepositoryService.convertBoolean(purchasePlanDataItemRow.isImpossibleToDetermineAttr()),
            okeiCode,
            purchasePlanDataItemRow.getQty()
        );

    }

    public void insert(InnovationPlanDataItemRowType innovationPlanDataItemRow, String planItemGuid) {
        String sql = "INSERT INTO zakupki.innovation_plan_item_row (plan_item_guid, ordinal_number, additional_info," +
            "okdp_code, okpd2_code, okved_code, okved2_code)" +
            "VALUES" +
            "(?, ?, ?, ?, ?, ?, ?);";
        jdbcTemplate.update(sql,
            planItemGuid,
            innovationPlanDataItemRow.getOrdinalNumber(),
            innovationPlanDataItemRow.getAdditionalInfo(),
            getOkdpCode(innovationPlanDataItemRow.getOkdp()),
            getOkpd2Code(innovationPlanDataItemRow.getOkpd2()),
            getOkvedCode(innovationPlanDataItemRow.getOkved()),
            getOkved2Code(innovationPlanDataItemRow.getOkved2())
        );
    }

    private String getOkdpCode(OkdpProductType okdp) {
        if (okdp == null) return null;
        else return okdp.getCode();
    }

    private String getOkpd2Code(Okpd2ProductType okpd2) {
        if (okpd2 == null) return null;
        else return okpd2.getCode();
    }

    private String getOkvedCode(OkvedProductType okved) {
        if (okved == null) return null;
        else return okved.getCode();
    }

    private String getOkved2Code(Okved2ProductType okved2) {
        if (okved2 == null) return null;
        else return okved2.getCode();
    }
}
