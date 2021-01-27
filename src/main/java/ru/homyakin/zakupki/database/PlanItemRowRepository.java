package ru.homyakin.zakupki.database;

import java.util.List;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.homyakin.zakupki.models._223fz.purchaseplan.InnovationPlanDataItemRowType;
import ru.homyakin.zakupki.models._223fz.purchaseplan.PurchasePlanDataItemRowType;
import ru.homyakin.zakupki.utils.RepositoryUtils;

@Component
public class PlanItemRowRepository {
    private static final Logger logger = LoggerFactory.getLogger(PlanItemRowRepository.class);
    private final JdbcTemplate jdbcTemplate;
    private final RepositoryUtils repositoryUtils;
    private final ClassifierService classifierService;

    public PlanItemRowRepository(
        DataSource dataSource,
        RepositoryUtils repositoryUtils,
        ClassifierService classifierService
    ) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        this.repositoryUtils = repositoryUtils;
        this.classifierService = classifierService;
    }

    public void insert(PurchasePlanDataItemRowType purchasePlanItemRow, String planItemGuid) {
        String sql = "INSERT INTO zakupki.purchase_plan_item_row (plan_item_guid, ordinal_number, additional_info," +
            "okdp_code, okdp_name, okpd2_code, okpd2_name, okved_code, okved_name, okved2_code, okved2_name, okato," +
            " region, impossible_to_determine_attr, okei_code, okei_name, qty)" +
            "VALUES" +
            "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            jdbcTemplate.update(
                sql,
                planItemGuid,
                purchasePlanItemRow.getOrdinalNumber(),
                repositoryUtils.removeExtraSpaces(purchasePlanItemRow.getAdditionalInfo()),
                classifierService.getClassifierCode(purchasePlanItemRow.getOkdp()),
                classifierService.getClassifierName(purchasePlanItemRow.getOkdp()),
                classifierService.getClassifierCode(purchasePlanItemRow.getOkpd2()),
                classifierService.getClassifierName(purchasePlanItemRow.getOkpd2()),
                classifierService.getClassifierCode(purchasePlanItemRow.getOkved()),
                classifierService.getClassifierName(purchasePlanItemRow.getOkved()),
                classifierService.getClassifierCode(purchasePlanItemRow.getOkved2()),
                classifierService.getClassifierName(purchasePlanItemRow.getOkved2()),
                purchasePlanItemRow.getOkato(),
                repositoryUtils.removeExtraSpaces(purchasePlanItemRow.getRegion()),
                repositoryUtils.convertBoolean(purchasePlanItemRow.isImpossibleToDetermineAttr()),
                classifierService.getClassifierCode(purchasePlanItemRow.getOkei()),
                classifierService.getClassifierName(purchasePlanItemRow.getOkei()),
                purchasePlanItemRow.getQty()
            );
        } catch (Exception e) {
            logger.error("Internal database error", e);
        }
    }

    public void insert(InnovationPlanDataItemRowType innovationPlanItemRow, String planItemGuid) {
        String sql = "INSERT INTO zakupki.innovation_plan_item_row (plan_item_guid, ordinal_number, additional_info," +
            "okdp_code, okdp_name, okpd2_code, okpd2_name, okved_code, okved_name, okved2_code, okved2_name)" +
            "VALUES" +
            "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        jdbcTemplate.update(
            sql,
            planItemGuid,
            innovationPlanItemRow.getOrdinalNumber(),
            repositoryUtils.removeExtraSpaces(innovationPlanItemRow.getAdditionalInfo()),
            classifierService.getClassifierCode(innovationPlanItemRow.getOkdp()),
            classifierService.getClassifierName(innovationPlanItemRow.getOkdp()),
            classifierService.getClassifierCode(innovationPlanItemRow.getOkpd2()),
            classifierService.getClassifierName(innovationPlanItemRow.getOkpd2()),
            classifierService.getClassifierCode(innovationPlanItemRow.getOkved()),
            classifierService.getClassifierName(innovationPlanItemRow.getOkved()),
            classifierService.getClassifierCode(innovationPlanItemRow.getOkved2()),
            classifierService.getClassifierName(innovationPlanItemRow.getOkved2())
        );
    }

    public void update(PurchasePlanDataItemRowType purchasePlanItemRow, String planItemGuid) {
        if (!checkPlanItemRow(planItemGuid, purchasePlanItemRow.getOrdinalNumber(), "purchase_plan_item_row")) {
            insert(purchasePlanItemRow, planItemGuid);
            return;
        }
        try {
            String sql = "UPDATE purchase_plan_item_row SET additional_info = ?, okdp_code = ?, okdp_name = ?, " +
                "okpd2_code = ?, okpd2_name = ?, okved_code = ?, okved_name = ?, okved2_code = ?, okved2_name = ?, " +
                "okato = ?, region = ?, impossible_to_determine_attr = ?, okei_code = ?, okei_name = ?, qty = ? " +
                "WHERE plan_item_guid = ? and ordinal_number = ?";
            jdbcTemplate.update(
                sql,
                repositoryUtils.removeExtraSpaces(purchasePlanItemRow.getAdditionalInfo()),
                classifierService.getClassifierCode(purchasePlanItemRow.getOkdp()),
                classifierService.getClassifierName(purchasePlanItemRow.getOkdp()),
                classifierService.getClassifierCode(purchasePlanItemRow.getOkpd2()),
                classifierService.getClassifierName(purchasePlanItemRow.getOkpd2()),
                classifierService.getClassifierCode(purchasePlanItemRow.getOkved()),
                classifierService.getClassifierName(purchasePlanItemRow.getOkved()),
                classifierService.getClassifierCode(purchasePlanItemRow.getOkved2()),
                classifierService.getClassifierName(purchasePlanItemRow.getOkved2()),
                purchasePlanItemRow.getOkato(),
                repositoryUtils.removeExtraSpaces(purchasePlanItemRow.getRegion()),
                repositoryUtils.convertBoolean(purchasePlanItemRow.isImpossibleToDetermineAttr()),
                classifierService.getClassifierCode(purchasePlanItemRow.getOkei()),
                classifierService.getClassifierName(purchasePlanItemRow.getOkei()),
                purchasePlanItemRow.getQty(),
                planItemGuid,
                purchasePlanItemRow.getOrdinalNumber()
            );
        } catch (Exception e) {
            logger.error("Internal database error", e);
        }
    }

    public void update(InnovationPlanDataItemRowType innovationPlanItemRow, String planItemGuid) {
        if (!checkPlanItemRow(planItemGuid, innovationPlanItemRow.getOrdinalNumber(), "innovation_plan_item_row")) {
            insert(innovationPlanItemRow, planItemGuid);
            return;
        }
        try {
            String sql = "UPDATE innovation_plan_item_row SET additional_info = ?, okdp_code = ?, okdp_name = ?, " +
                "okpd2_code = ?, okpd2_name = ?, okved_code = ?, okved_name = ?, okved2_code = ?, okved2_name = ? " +
                "WHERE plan_item_guid = ? and ordinal_number = ?";
            jdbcTemplate.update(
                sql,
                innovationPlanItemRow.getAdditionalInfo(),
                classifierService.getClassifierCode(innovationPlanItemRow.getOkdp()),
                classifierService.getClassifierName(innovationPlanItemRow.getOkdp()),
                classifierService.getClassifierCode(innovationPlanItemRow.getOkpd2()),
                classifierService.getClassifierName(innovationPlanItemRow.getOkpd2()),
                classifierService.getClassifierCode(innovationPlanItemRow.getOkved()),
                classifierService.getClassifierName(innovationPlanItemRow.getOkved()),
                classifierService.getClassifierCode(innovationPlanItemRow.getOkved2()),
                classifierService.getClassifierName(innovationPlanItemRow.getOkved2()),
                planItemGuid,
                innovationPlanItemRow.getOrdinalNumber()
            );
        } catch (Exception e) {
            logger.error("Internal database error", e);
        }
    }

    private boolean checkPlanItemRow(String guid, Integer ordinalNumber, String table) {
        String sql = "SELECT plan_item_guid FROM " + table + " WHERE plan_item_guid = ? and ordinal_number = ?";
        List<String> result = jdbcTemplate.query(
            sql,
            new Object[]{guid, ordinalNumber},
            (rs, rowNum) -> rs.getString("plan_item_guid")
        );
        return result.size() != 0;
    }
}
