package ru.homyakin.zakupki.database.purchase_plan;

import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.homyakin.zakupki.database.BaseRepository;
import ru.homyakin.zakupki.database.CustomerRepository;
import ru.homyakin.zakupki.models._223fz.purchaseplan.PurchasePlan;
import ru.homyakin.zakupki.models._223fz.purchaseplan.PurchasePlanDataType;
import ru.homyakin.zakupki.utils.RepositoryUtils;

@Component
public class PurchasePlanRepository extends BaseRepository<PurchasePlan> {
    private static final Logger logger = LoggerFactory.getLogger(PurchasePlanRepository.class);
    private final JdbcTemplate jdbcTemplate;
    private final CustomerRepository customerRepository;
    private final PlanItemRepository planItemRepository;
    private final RepositoryUtils repositoryUtils;

    public PurchasePlanRepository(
        DataSource dataSource,
        CustomerRepository customerRepository,
        PlanItemRepository planItemRepository,
        RepositoryUtils repositoryUtils
    ) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        this.customerRepository = customerRepository;
        this.planItemRepository = planItemRepository;
        this.repositoryUtils = repositoryUtils;
    }

    public void insert(PurchasePlan purchasePlan) {
        String sql = "INSERT INTO purchase_plan (guid, customer_inn, placer_inn, plan_type, is_upload_complete," +
            "create_date_time, url_eis, url_vsrz, url_kis_rmis, registration_number, name, additional_info," +
            "start_date, end_date, approve_date, publication_date_time, is_digit_form, summ_size_ch15," +
            "is_imported_from_vsrz, status_code, version, modification_description, use_new_classifiers," +
            "exclude_volume, volume_smb, annual_volume, percent_smb, smb_partition_changed, annual_volume_smb_less_18_percent," +
            "reporting_year, previous_year_annual_volume, previous_year_annual_volume_hitech, previous_year_annual_volume_hitech_smb," +
            "annual_year_annual_volume_hitech_summ, annual_year_annual_volume_hitech_increase," +
            "annual_year_annual_volume_hitech_percent, annual_year_annual_volume_hitech_smb_summ," +
            "annual_year_annual_volume_hitech_smb_increase, annual_year_annual_volume_hitech_smb_percent)" +
            "VALUES" +
            "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PurchasePlanDataType purchasePlanData = purchasePlan.getBody().getItem().getPurchasePlanData();
        logger.info("Inserting purchase plan with guid: {}", purchasePlanData.getGuid());
        String customerInn = null;
        if (purchasePlanData.getCustomer() != null) {
            customerRepository.insert(purchasePlanData.getCustomer().getMainInfo());
            customerInn = purchasePlanData.getCustomer().getMainInfo().getInn();
        }
        customerRepository.insert(purchasePlanData.getPlacer().getMainInfo()); //required field
        try {
            String planStatus = purchasePlanData.getStatus() != null ? purchasePlanData.getStatus().value() : null;
            String planType = purchasePlanData.getPlanType() != null ? purchasePlanData.getPlanType().value() : null;
            jdbcTemplate.update(
                sql,
                purchasePlanData.getGuid(),
                repositoryUtils.removeExtraSpaces(customerInn),
                repositoryUtils.removeExtraSpaces(purchasePlanData.getPlacer().getMainInfo().getInn()),
                repositoryUtils.removeExtraSpaces(planType),
                repositoryUtils.convertBoolean(purchasePlanData.isIsUploadComplete()),
                repositoryUtils.convertFromXMLGregorianCalendarToLocalDateTime(purchasePlanData.getCreateDateTime()),
                repositoryUtils.removeExtraSpaces(purchasePlanData.getUrlEIS()),
                repositoryUtils.removeExtraSpaces(purchasePlanData.getUrlVSRZ()),
                repositoryUtils.removeExtraSpaces(purchasePlanData.getUrlKisRmis()),
                repositoryUtils.removeExtraSpaces(purchasePlanData.getRegistrationNumber()),
                repositoryUtils.removeExtraSpaces(purchasePlanData.getName()),
                repositoryUtils.removeExtraSpaces(purchasePlanData.getAdditionalInfo()),
                repositoryUtils.convertFromXMLGregorianCalendarToLocalDate(purchasePlanData.getStartDate()),
                repositoryUtils.convertFromXMLGregorianCalendarToLocalDate(purchasePlanData.getEndDate()),
                repositoryUtils.convertFromXMLGregorianCalendarToLocalDate(purchasePlanData.getApproveDate()),
                repositoryUtils.convertFromXMLGregorianCalendarToLocalDateTime(purchasePlanData.getPublicationDateTime()),
                repositoryUtils.convertBoolean(purchasePlanData.isIsDigitForm()),
                repositoryUtils.convertBoolean(purchasePlanData.isSummSizeCh15()),
                repositoryUtils.convertBoolean(purchasePlanData.isIsImportedFromVSRZ()),
                repositoryUtils.removeExtraSpaces(planStatus),
                purchasePlanData.getVersion(),
                repositoryUtils.removeExtraSpaces(purchasePlanData.getModificationDescription()),
                repositoryUtils.convertBoolean(purchasePlanData.isUseNewClassifiers()),
                purchasePlanData.getExcludeVolume(),
                purchasePlanData.getVolumeSMB(),
                purchasePlanData.getAnnualVolume(),
                purchasePlanData.getPercentSMB(),
                repositoryUtils.convertBoolean(purchasePlanData.isSmbPartitionChanged()),
                repositoryUtils.convertBoolean(purchasePlanData.isAnnualVolumeSMBLess18Percent()),
                purchasePlanData.getReportingYear(),
                purchasePlanData.getPreviousYearAnnualVolume(),
                purchasePlanData.getPreviousYearAnnualVolumeHiTech(),
                purchasePlanData.getPreviousYearAnnualVolumeHiTechSMB(),
                purchasePlanData.getAnnualVolumeHiTechSumm(),
                purchasePlanData.getAnnualVolumeHiTechIncrease(),
                purchasePlanData.getAnnualVolumeHiTechPercent(),
                purchasePlanData.getAnnualVolumeHiTechSMBSumm(),
                purchasePlanData.getAnnualVolumeHiTechSMBIncrease(),
                purchasePlanData.getAnnualVolumeHiTechSMBPercent()
            );
            if (purchasePlanData.getPurchasePlanItems() != null) {
                for (var i : purchasePlanData.getPurchasePlanItems().getPurchasePlanItem()) {
                    planItemRepository.insert(i, false, purchasePlanData.getGuid());
                }
            }
            if (purchasePlanData.getInnovationPlanItems() != null) {
                for (var i : purchasePlanData.getInnovationPlanItems().getInnovationPlanItem()) {
                    planItemRepository.insert(i, false, purchasePlanData.getGuid());
                }
            }
            if (purchasePlanData.getPurchasePlanItemsSMB() != null) {
                for (var i : purchasePlanData.getPurchasePlanItemsSMB().getPurchasePlanItem()) {
                    planItemRepository.insert(i, true, purchasePlanData.getGuid());
                }
            }
            if (purchasePlanData.getInnovationPlanItemsSMB() != null) {
                for (var i : purchasePlanData.getInnovationPlanItemsSMB().getInnovationPlanItem()) {
                    planItemRepository.insert(i, true, purchasePlanData.getGuid());
                }
            }
        } catch (Exception e) {
            logger.error("Error during inserting into purchase plan", e);
        }
    }
}
