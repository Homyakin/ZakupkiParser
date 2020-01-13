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
    private final ClassifierRepository classifierRepository;

    public PlanItemRowRepository(DataSource dataSource,
                                 ClassifierRepository classifierRepository) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        this.classifierRepository = classifierRepository;
    }

    public void insert(PurchasePlanDataItemRowType purchasePlanItemRow, String planItemGuid) {
        String sql = "INSERT INTO zakupki.purchase_plan_item_row (plan_item_guid, ordinal_number, additional_info," +
            "okdp_code, okpd2_code, okved_code, okved2_code, okato, region, impossible_to_determine_attr," +
            "okei_code, qty)" +
            "VALUES" +
            "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        ClassifierRepository.Classifier okdp = classifierRepository.getClassifier(purchasePlanItemRow.getOkdp());
        ClassifierRepository.Classifier okpd2 = classifierRepository.getClassifier(purchasePlanItemRow.getOkpd2());
        ClassifierRepository.Classifier okved = classifierRepository.getClassifier(purchasePlanItemRow.getOkved());
        ClassifierRepository.Classifier okved2 = classifierRepository.getClassifier(purchasePlanItemRow.getOkved2());
        ClassifierRepository.Classifier okei = classifierRepository.getClassifier(purchasePlanItemRow.getOkei());
        jdbcTemplate.update(sql,
            planItemGuid,
            purchasePlanItemRow.getOrdinalNumber(),
            purchasePlanItemRow.getAdditionalInfo(),
            getClassifierCode(okdp),
            getClassifierCode(okved),
            getClassifierCode(okpd2),
            getClassifierCode(okved2),
            purchasePlanItemRow.getOkato(),
            purchasePlanItemRow.getRegion(),
            RepositoryService.convertBoolean(purchasePlanItemRow.isImpossibleToDetermineAttr()),
            getClassifierCode(okei),
            purchasePlanItemRow.getQty()
        );

    }

    public void insert(InnovationPlanDataItemRowType innovationPlanItemRow, String planItemGuid) {
        String sql = "INSERT INTO zakupki.innovation_plan_item_row (plan_item_guid, ordinal_number, additional_info," +
            "okdp_code, okpd2_code, okved_code, okved2_code)" +
            "VALUES" +
            "(?, ?, ?, ?, ?, ?, ?);";
        ClassifierRepository.Classifier okdp = classifierRepository.getClassifier(innovationPlanItemRow.getOkdp());
        ClassifierRepository.Classifier okpd2 = classifierRepository.getClassifier(innovationPlanItemRow.getOkpd2());
        ClassifierRepository.Classifier okved = classifierRepository.getClassifier(innovationPlanItemRow.getOkved());
        ClassifierRepository.Classifier okved2 = classifierRepository.getClassifier(innovationPlanItemRow.getOkved2());
        jdbcTemplate.update(sql,
            planItemGuid,
            innovationPlanItemRow.getOrdinalNumber(),
            innovationPlanItemRow.getAdditionalInfo(),
            getClassifierCode(okdp),
            getClassifierCode(okved),
            getClassifierCode(okpd2),
            getClassifierCode(okved2)
        );
    }

    private String getClassifierCode(ClassifierRepository.Classifier classifier) {
        if(classifier == null) return null;
        else return classifier.getCode();
    }

    private String getClassifierName(ClassifierRepository.Classifier classifier) {
        if(classifier == null) return null;
        else return classifier.getName();
    }
}
