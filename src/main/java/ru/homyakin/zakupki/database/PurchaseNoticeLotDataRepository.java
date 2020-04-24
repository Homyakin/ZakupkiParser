package ru.homyakin.zakupki.database;

import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.homyakin.zakupki.models._223fz.types.LotType;

@Component
public class PurchaseNoticeLotDataRepository {
    private static final Logger logger = LoggerFactory.getLogger(PurchaseNoticeLotDataRepository.class);
    private final JdbcTemplate jdbcTemplate;
    private final RepositoryService repositoryService;
    private final PurchaseNoticeLotItemRepository purchaseNoticeLotItemRepository;
    public PurchaseNoticeLotDataRepository(
        DataSource dataSource,
        RepositoryService repositoryService,
        PurchaseNoticeLotItemRepository purchaseNoticeLotItemRepository
    ) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.repositoryService = repositoryService;
        this.purchaseNoticeLotItemRepository = purchaseNoticeLotItemRepository;
    }

    public void insert(LotType.LotData data, String lotGuid) {
        String sql = "INSERT INTO zakupki.purchase_notice_data (guid, create_date_time, url_eis, url_vsrz, url_kis_rmis," +
            "registration_number, name, customer_inn, detached_org_inn, blocked_customer_inn, purchase_method_code," +
            "purchase_code_name, placer_inn, publication_date_time, purchase_notice_status_code, version, modification_description," +
            "not_dishonest, modification_date, save_user_id, delivery_place_indication, emergency, joint_purchase," +
            "for_small_or_middle, change_decision_date, antimonopoly_decision_taken, additional_info, appl_submision_place," +
            "appl_submision_start_date, appl_submision_order, envelope_opening_order, appl_examination_order," +
            "summingup_order, auction_order, consideration_second_part_place, consideration_second_part_order," +
            "is_upload_complete, electronic_place_id, submission_close_date_time, publication_planned_date)" +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?," +
            "?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            jdbcTemplate.update(
                sql,
                lotGuid,
                data.getSubject(),
                repositoryService.getCurrencyCode(data.getCurrency()),
                data.getExchangeInfo() != null ? data.getExchangeInfo().getExchangeRate() : null,
                data.getExchangeInfo() != null ? repositoryService.convertFromXMLGregorianCalendarToLocalDate(data.getExchangeInfo().getExchangeRateDate()) : null,
                data.getInitialSum(),
                data.getStartingContractPriceRub(),
                data.getPriceFormula(),
                data.getCommodityPrice(),
                data.getCommodityPriceRub(),
                data.getMaxContractPrice(),
                data.getMaxContractPriceRub(),
                data.getInitialSumInfo(),
                data.getOrderPricing(),
                data.getDeliveryPlace() != null ? data.getDeliveryPlace().getState() : null,
                data.getDeliveryPlace() != null ? data.getDeliveryPlace().getRegion() : null,
                data.getDeliveryPlace() != null ? data.getDeliveryPlace().getRegionOkato() : null,
                data.getDeliveryPlace() != null ? data.getDeliveryPlace().getAddress() : null,
                repositoryService.convertBoolean(data.isForSmallOrMiddle()),
                repositoryService.convertBoolean(data.isExcludePurchaseFromPlan()),
                repositoryService.convertBoolean(data.isSubcontractorsRequirement()),
                repositoryService.convertBoolean(data.isIgnoredPurchase()),
                data.getPurchaseCategory() != null ? data.getPurchaseCategory().getCode() : null,
                repositoryService.convertBoolean(data.isCentralized()),
                data.getPurchaseDescription(),
                repositoryService.convertBoolean(data.isApplicationSupplyNeeded()),
                data.getApplicationSupplySumm(),
                repositoryService.getCurrencyCode(data.getApplicationSupplyCurrency()),
                data.getApplicationSupplyExtra(),
                data.getMajorContractConditions(),
                repositoryService.convertBoolean(data.isAntimonopolyDecisionTaken())
            );
            for(var item: data.getLotItems().getLotItem()) {
                purchaseNoticeLotItemRepository.insert(item, lotGuid);
            }
        } catch (Exception e) {
            logger.error("Error during inserting lot data", e);
        }
    }
}
