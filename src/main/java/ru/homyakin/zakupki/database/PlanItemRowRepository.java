package ru.homyakin.zakupki.database;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.homyakin.zakupki.documentsinfo._223fz.purchaseplan.InnovationPlanDataItemRowType;
import ru.homyakin.zakupki.documentsinfo._223fz.purchaseplan.PurchasePlanDataItemRowType;

import javax.sql.DataSource;

import static ru.homyakin.zakupki.database.ClassifierRepository.Classifier;

@Component
public class PlanItemRowRepository {
    private static final Logger logger = LoggerFactory.getLogger(PlanItemRowRepository.class);
    private final JdbcTemplate jdbcTemplate;
    private final ClassifierRepository classifierRepository;

    public PlanItemRowRepository(DataSource dataSource,
                                 ClassifierRepository classifierRepository) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        this.classifierRepository = classifierRepository;
    }

    public void insert(PurchasePlanDataItemRowType purchasePlanItemRow, String planItemGuid) {
        String sql = "INSERT INTO zakupki.purchase_plan_item_row (plan_item_guid, ordinal_number, additional_info," +
            "okdp_code, okdp_name, okpd2_code, okpd2_name, okved_code, okved_name, okved2_code, okved2_name, okato," +
            " region, impossible_to_determine_attr, okei_code, okei_name, qty)" +
            "VALUES" +
            "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        Classifier okdp = classifierRepository.getClassifier(purchasePlanItemRow.getOkdp());
        Classifier okpd2 = classifierRepository.getClassifier(purchasePlanItemRow.getOkpd2());
        Classifier okved = classifierRepository.getClassifier(purchasePlanItemRow.getOkved());
        Classifier okved2 = classifierRepository.getClassifier(purchasePlanItemRow.getOkved2());
        Classifier okei = classifierRepository.getClassifier(purchasePlanItemRow.getOkei());
        try {
            jdbcTemplate.update(sql,
                planItemGuid,
                purchasePlanItemRow.getOrdinalNumber(),
                RepositoryService.removeExtraSpaces(purchasePlanItemRow.getAdditionalInfo()),
                getClassifierCode(okdp),
                getClassifierName(okdp),
                getClassifierCode(okpd2),
                getClassifierName(okpd2),
                getClassifierCode(okved),
                getClassifierName(okved),
                getClassifierCode(okved2),
                getClassifierName(okved2),
                RepositoryService.removeExtraSpaces(purchasePlanItemRow.getOkato()),
                RepositoryService.removeExtraSpaces(purchasePlanItemRow.getRegion()),
                RepositoryService.convertBoolean(purchasePlanItemRow.isImpossibleToDetermineAttr()),
                getClassifierCode(okei),
                getClassifierName(okei),
                purchasePlanItemRow.getQty()
            );
        } catch (Exception e) {
            logger.error("Eternal error", e);
        }
    }

    public void insert(InnovationPlanDataItemRowType innovationPlanItemRow, String planItemGuid) {
        String sql = "INSERT INTO zakupki.innovation_plan_item_row (plan_item_guid, ordinal_number, additional_info," +
            "okdp_code, okdp_name, okpd2_code, okpd2_name, okved_code, okved_name, okved2_code, okved2_name)" +
            "VALUES" +
            "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        Classifier okdp = classifierRepository.getClassifier(innovationPlanItemRow.getOkdp());
        Classifier okpd2 = classifierRepository.getClassifier(innovationPlanItemRow.getOkpd2());
        Classifier okved = classifierRepository.getClassifier(innovationPlanItemRow.getOkved());
        Classifier okved2 = classifierRepository.getClassifier(innovationPlanItemRow.getOkved2());
        jdbcTemplate.update(sql,
            planItemGuid,
            innovationPlanItemRow.getOrdinalNumber(),
            RepositoryService.removeExtraSpaces(innovationPlanItemRow.getAdditionalInfo()),
            getClassifierCode(okdp),
            getClassifierName(okdp),
            getClassifierCode(okpd2),
            getClassifierName(okpd2),
            getClassifierCode(okved),
            getClassifierName(okved),
            getClassifierCode(okved2),
            getClassifierName(okved2)
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
            Classifier okdp = classifierRepository.getClassifier(purchasePlanItemRow.getOkdp());
            Classifier okpd2 = classifierRepository.getClassifier(purchasePlanItemRow.getOkpd2());
            Classifier okved = classifierRepository.getClassifier(purchasePlanItemRow.getOkved());
            Classifier okved2 = classifierRepository.getClassifier(purchasePlanItemRow.getOkved2());
            Classifier okei = classifierRepository.getClassifier(purchasePlanItemRow.getOkei());
            jdbcTemplate.update(sql,
                RepositoryService.removeExtraSpaces(purchasePlanItemRow.getAdditionalInfo()),
                getClassifierCode(okdp),
                getClassifierName(okdp),
                getClassifierCode(okpd2),
                getClassifierName(okpd2),
                getClassifierCode(okved),
                getClassifierName(okved),
                getClassifierCode(okved2),
                getClassifierName(okved2),
                RepositoryService.removeExtraSpaces(purchasePlanItemRow.getOkato()),
                RepositoryService.removeExtraSpaces(purchasePlanItemRow.getRegion()),
                RepositoryService.convertBoolean(purchasePlanItemRow.isImpossibleToDetermineAttr()),
                getClassifierCode(okei),
                getClassifierName(okei),
                purchasePlanItemRow.getQty(),
                planItemGuid,
                purchasePlanItemRow.getOrdinalNumber()
            );
        } catch (Exception e) {
            logger.error("Eternal error", e);
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
            Classifier okdp = classifierRepository.getClassifier(innovationPlanItemRow.getOkdp());
            Classifier okpd2 = classifierRepository.getClassifier(innovationPlanItemRow.getOkpd2());
            Classifier okved = classifierRepository.getClassifier(innovationPlanItemRow.getOkved());
            Classifier okved2 = classifierRepository.getClassifier(innovationPlanItemRow.getOkved2());
            jdbcTemplate.update(sql,
                innovationPlanItemRow.getAdditionalInfo(),
                getClassifierCode(okdp),
                getClassifierName(okdp),
                getClassifierCode(okpd2),
                getClassifierName(okpd2),
                getClassifierCode(okved),
                getClassifierName(okved),
                getClassifierCode(okved2),
                getClassifierName(okved2),
                planItemGuid,
                innovationPlanItemRow.getOrdinalNumber()
            );
        } catch (Exception e) {
            logger.error("Eternal error", e);
        }
    }

    private boolean checkPlanItemRow(String guid, Integer ordinalNumber, String table) {
        String sql = "SELECT plan_item_guid FROM " + table + " WHERE plan_item_guid = ? and ordinal_number = ?";
        List<String> result = jdbcTemplate.query(sql,
            new Object[]{guid, ordinalNumber},
            (rs, rowNum) -> rs.getString("plan_item_guid")
        );
        return result.size() != 0;
    }

    private String getClassifierCode(Classifier classifier) {
        if (classifier == null) return null;
        else return classifier.getCode();
    }

    private String getClassifierName(Classifier classifier) {
        if (classifier == null) return null;
        else return classifier.getName();
    }
}
