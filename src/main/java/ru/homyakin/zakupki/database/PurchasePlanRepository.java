package ru.homyakin.zakupki.database;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.homyakin.zakupki.documentsinfo._223fz.purchaseplan.InnovationPlanDataItemType;
import ru.homyakin.zakupki.documentsinfo._223fz.purchaseplan.PurchasePlan;
import ru.homyakin.zakupki.documentsinfo._223fz.purchaseplan.PurchasePlanDataItemType;
import ru.homyakin.zakupki.documentsinfo._223fz.purchaseplan.PurchasePlanDataType;

import javax.sql.DataSource;

@Component
public class PurchasePlanRepository {
    private final JdbcTemplate jdbcTemplate;
    private final CustomerRepository customerRepository;
    private final PlanItemRepository planItemRepository;
    private static final Logger logger = LoggerFactory.getLogger(CustomerRepository.class);
    public PurchasePlanRepository(DataSource dataSource,
                                  CustomerRepository customerRepository,
                                  PlanItemRepository planItemRepository) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        this.customerRepository = customerRepository;
        this.planItemRepository = planItemRepository;
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
        customerRepository.insert(purchasePlanData.getCustomer().getMainInfo());
        customerRepository.insert(purchasePlanData.getPlacer().getMainInfo());
        try {
            jdbcTemplate.update(sql,
                purchasePlanData.getGuid(),
                purchasePlanData.getCustomer().getMainInfo().getInn(),
                purchasePlanData.getPlacer().getMainInfo().getInn(),
                purchasePlanData.getPlanType().value(),
                RepositoryService.convertBoolean(purchasePlanData.isIsUploadComplete()),
                purchasePlanData.getCreateDateTime(),
                purchasePlanData.getUrlEIS(),
                purchasePlanData.getUrlVSRZ(),
                purchasePlanData.getUrlKisRmis(),
                purchasePlanData.getRegistrationNumber(),
                purchasePlanData.getName(),
                purchasePlanData.getAdditionalInfo(),
                purchasePlanData.getStartDate(),
                purchasePlanData.getEndDate(),
                purchasePlanData.getApproveDate(),
                purchasePlanData.getPublicationDateTime(),
                RepositoryService.convertBoolean(purchasePlanData.isIsDigitForm()),
                RepositoryService.convertBoolean(purchasePlanData.isSummSizeCh15()),
                RepositoryService.convertBoolean(purchasePlanData.isIsImportedFromVSRZ()),
                purchasePlanData.getStatus().value(),
                purchasePlanData.getVersion(),
                purchasePlanData.getModificationDescription(),
                RepositoryService.convertBoolean(purchasePlanData.isUseNewClassifiers()),
                purchasePlanData.getExcludeVolume(),
                purchasePlanData.getVolumeSMB(),
                purchasePlanData.getAnnualVolume(),
                purchasePlanData.getPercentSMB(),
                RepositoryService.convertBoolean(purchasePlanData.isSmbPartitionChanged()),
                RepositoryService.convertBoolean(purchasePlanData.isAnnualVolumeSMBLess18Percent()),
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
        } catch (Exception e) {
            logger.error("Eternal error", e);
        }
        for (PurchasePlanDataItemType i : purchasePlanData.getPurchasePlanItems().getPurchasePlanItem()) {
            planItemRepository.insert(i, false, purchasePlanData.getGuid());
        }
        for (InnovationPlanDataItemType i : purchasePlanData.getInnovationPlanItems().getInnovationPlanItem()) {
            planItemRepository.insert(i, false, purchasePlanData.getGuid());
        }
        for (PurchasePlanDataItemType i : purchasePlanData.getPurchasePlanItemsSMB().getPurchasePlanItem()) {
            planItemRepository.insert(i, true, purchasePlanData.getGuid());
        }
        for (InnovationPlanDataItemType i : purchasePlanData.getInnovationPlanItemsSMB().getInnovationPlanItem()) {
            planItemRepository.insert(i, true, purchasePlanData.getGuid());
        }
    }
}
