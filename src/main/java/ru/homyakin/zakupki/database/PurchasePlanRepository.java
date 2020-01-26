package ru.homyakin.zakupki.database;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.homyakin.zakupki.models._223fz.purchaseplan.InnovationPlanDataItemType;
import ru.homyakin.zakupki.models._223fz.purchaseplan.PurchasePlan;
import ru.homyakin.zakupki.models._223fz.purchaseplan.PurchasePlanDataItemType;
import ru.homyakin.zakupki.models._223fz.purchaseplan.PurchasePlanDataType;

import javax.sql.DataSource;

@Component
public class PurchasePlanRepository {
    private final JdbcTemplate jdbcTemplate;
    private final CustomerRepository customerRepository;
    private final PlanItemRepository planItemRepository;
    private final RepositoryService repositoryService;
    private static final Logger logger = LoggerFactory.getLogger(PurchasePlanRepository.class);

    public PurchasePlanRepository(
        DataSource dataSource,
        CustomerRepository customerRepository,
        PlanItemRepository planItemRepository,
        RepositoryService repositoryService
    ) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        this.customerRepository = customerRepository;
        this.planItemRepository = planItemRepository;
        this.repositoryService = repositoryService;
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
        String customerInn = null;
        if (purchasePlanData.getCustomer() != null) {
            customerRepository.insert(purchasePlanData.getCustomer().getMainInfo());
            customerInn = purchasePlanData.getCustomer().getMainInfo().getInn();
        }
        customerRepository.insert(purchasePlanData.getPlacer().getMainInfo()); //required field
        try {
            String planStatus = purchasePlanData.getStatus() != null ? purchasePlanData.getStatus().value() : null;
            String planType = purchasePlanData.getPlanType() != null ? purchasePlanData.getPlanType().value() : null;
            jdbcTemplate.update(sql,
                purchasePlanData.getGuid(),
                repositoryService.removeExtraSpaces(customerInn),
                repositoryService.removeExtraSpaces(purchasePlanData.getPlacer().getMainInfo().getInn()),
                repositoryService.removeExtraSpaces(planType),
                repositoryService.convertBoolean(purchasePlanData.isIsUploadComplete()),
                repositoryService.convertFromXMLGregorianCalendarToLocalDateTime(purchasePlanData.getCreateDateTime()),
                repositoryService.removeExtraSpaces(purchasePlanData.getUrlEIS()),
                repositoryService.removeExtraSpaces(purchasePlanData.getUrlVSRZ()),
                repositoryService.removeExtraSpaces(purchasePlanData.getUrlKisRmis()),
                repositoryService.removeExtraSpaces(purchasePlanData.getRegistrationNumber()),
                repositoryService.removeExtraSpaces(purchasePlanData.getName()),
                repositoryService.removeExtraSpaces(purchasePlanData.getAdditionalInfo()),
                repositoryService.convertFromXMLGregorianCalendarToLocalDate(purchasePlanData.getStartDate()),
                repositoryService.convertFromXMLGregorianCalendarToLocalDate(purchasePlanData.getEndDate()),
                repositoryService.convertFromXMLGregorianCalendarToLocalDate(purchasePlanData.getApproveDate()),
                repositoryService.convertFromXMLGregorianCalendarToLocalDateTime(purchasePlanData.getPublicationDateTime()),
                repositoryService.convertBoolean(purchasePlanData.isIsDigitForm()),
                repositoryService.convertBoolean(purchasePlanData.isSummSizeCh15()),
                repositoryService.convertBoolean(purchasePlanData.isIsImportedFromVSRZ()),
                repositoryService.removeExtraSpaces(planStatus),
                purchasePlanData.getVersion(),
                repositoryService.removeExtraSpaces(purchasePlanData.getModificationDescription()),
                repositoryService.convertBoolean(purchasePlanData.isUseNewClassifiers()),
                purchasePlanData.getExcludeVolume(),
                purchasePlanData.getVolumeSMB(),
                purchasePlanData.getAnnualVolume(),
                purchasePlanData.getPercentSMB(),
                repositoryService.convertBoolean(purchasePlanData.isSmbPartitionChanged()),
                repositoryService.convertBoolean(purchasePlanData.isAnnualVolumeSMBLess18Percent()),
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
                for (PurchasePlanDataItemType i : purchasePlanData.getPurchasePlanItems().getPurchasePlanItem()) {
                    planItemRepository.insert(i, false, purchasePlanData.getGuid());
                }
            }
            if (purchasePlanData.getInnovationPlanItems() != null) {
                for (InnovationPlanDataItemType i : purchasePlanData.getInnovationPlanItems().getInnovationPlanItem()) {
                    planItemRepository.insert(i, false, purchasePlanData.getGuid());
                }
            }
            if (purchasePlanData.getPurchasePlanItemsSMB() != null) {
                for (PurchasePlanDataItemType i : purchasePlanData.getPurchasePlanItemsSMB().getPurchasePlanItem()) {
                    planItemRepository.insert(i, true, purchasePlanData.getGuid());
                }
            }
            if (purchasePlanData.getInnovationPlanItemsSMB() != null) {
                for (InnovationPlanDataItemType i : purchasePlanData.getInnovationPlanItemsSMB().getInnovationPlanItem()) {
                    planItemRepository.insert(i, true, purchasePlanData.getGuid());
                }
            }
        } catch (Exception e) {
            logger.error("Internal database error", e);
        }
    }
}
