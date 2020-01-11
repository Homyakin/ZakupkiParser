package ru.homyakin.zakupki.database;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.homyakin.zakupki.documentsinfo._223fz.purchaseplan.InnovationPlanDataItemRowType;
import ru.homyakin.zakupki.documentsinfo._223fz.purchaseplan.PurchasePlanDataItemRowType;

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
        jdbcTemplate.update(sql,
            planItemGuid,
            purchasePlanDataItemRow.getOrdinalNumber(),
            purchasePlanDataItemRow.getAdditionalInfo(),
            purchasePlanDataItemRow.getOkdp().getCode(),
            purchasePlanDataItemRow.getOkpd2().getCode(),
            purchasePlanDataItemRow.getOkved().getCode(),
            purchasePlanDataItemRow.getOkved2().getCode(),
            purchasePlanDataItemRow.getOkato(),
            purchasePlanDataItemRow.getRegion(),
            RepositoryService.convertBoolean(purchasePlanDataItemRow.isImpossibleToDetermineAttr()),
            purchasePlanDataItemRow.getOkei().getCode(),
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
            innovationPlanDataItemRow.getOkdp().getCode(),
            innovationPlanDataItemRow.getOkpd2().getCode(),
            innovationPlanDataItemRow.getOkved().getCode(),
            innovationPlanDataItemRow.getOkved2().getCode()
        );
    }
}
