package ru.homyakin.zakupki.database;

import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.homyakin.zakupki.models._223fz.contract.Contract;
import ru.homyakin.zakupki.models._223fz.contract.ContractDataType;
import ru.homyakin.zakupki.models._223fz.contract.ContractStatusType;

@Component
public class ContractRepository extends BaseRepository<Contract> {
    private static final Logger logger = LoggerFactory.getLogger(ContractRepository.class);
    private final JdbcTemplate jdbcTemplate;
    private final PlanPositionRepository planPositionRepository;
    private final CustomerRepository customerRepository;
    private final RepositoryService repositoryService;

    public ContractRepository(
        DataSource dataSource,
        PlanPositionRepository planPositionRepository,
        CustomerRepository customerRepository,
        RepositoryService repositoryService
    ) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        this.planPositionRepository = planPositionRepository;
        this.customerRepository = customerRepository;
        this.repositoryService = repositoryService;
    }

    @Override
    public void insert(Contract contract) {
        String sql = "INSERT INTO zakupki.contract (guid, registration_number, notice_44fz, " +
            "notice_not_placed_by_fz223p5s4, notice_44_num, lot_44_num, termination, extension," +
            "prolongation, customer_appeale_or_needs_approval, customer_approval_or_antimonopoly_descision_date," +
            "start_execution_term, end_execution_term, plan_position_guid, url_eis, url_vsrz, url_kis_rmis," +
            "create_date_time, customer_inn, placer_inn, detached_org_inn, publication_date, contract_status_code," +
            "version, modification_description, digital_purchase, digital_purchase_code, provider, provider_code," +
            "change_contract, contract_reg_number, name, contract_date, approve_date, purchase_notice_info_guid," +
            "purchase_notice_info_number, lotGuid, subject_contract, purchase_type_code, resume_date, " +
            "has_subcontractor, has_subcontractor_code, subcontractors_total, has_good_info, additional_info," +
            "price, exchange_rate, exchange_rate_date, rub_price, currency_code, start_execution_date," +
            "end_execution_date, has_okpd_and_okdp_rows, has_okpd2_rows, is_electronic_place, electronic_place_name," +
            "electronic_place_url, electronic_place_publish_date, electronic_place_guid)" +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?," +
            " ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        ContractDataType contractData = contract.getBody().getItem().getContractData();
        planPositionRepository.insert(contractData.getPlanPosition());
        customerRepository.insert(contractData.getCustomer().getMainInfo());
        customerRepository.insert(contractData.getPlacer().getMainInfo());
        String detachedOrgInn = null;
        if (contractData.getDetachedOrg() != null) {
            customerRepository.insert(contractData.getDetachedOrg().getMainInfo());
            detachedOrgInn = contractData.getDetachedOrg().getMainInfo().getInn();
        }
        jdbcTemplate.update(sql,
            contractData.getGuid(),
            contractData.getRegistrationNumber(),
            repositoryService.convertBoolean(contractData.isNotice44()),
            repositoryService.convertBoolean(contractData.isNoticeNotPlacedByFz223P5S4()),
            contractData.getNotice44Num(),
            contractData.getLot44Num(),
            repositoryService.convertBoolean(contractData.isTermination()),
            repositoryService.convertBoolean(contractData.isExtension()),
            repositoryService.convertBoolean(contractData.isProlongation()),
            repositoryService.convertBoolean(contractData.isCustomerAppealedOrNeedsApproval()),
            repositoryService.convertFromXMLGregorianCalendarToLocalDateTime(contractData.getCustomerApprovalOrAntimonopolyDescisionDate()),
            repositoryService.removeExtraSpaces(contractData.getStartExecutionTerm()),
            repositoryService.removeExtraSpaces(contractData.getEndExecutionTerm()),
            contractData.getPlanPosition().getPositionGuid(),
            contractData.getUrlEIS(),
            contractData.getUrlVSRZ(),
            contractData.getUrlKisRmis(),
            repositoryService.convertFromXMLGregorianCalendarToLocalDateTime(contractData.getCreateDateTime()),
            contractData.getCustomer().getMainInfo().getInn(),
            contractData.getPlacer().getMainInfo().getInn(),
            detachedOrgInn,
            repositoryService.convertFromXMLGregorianCalendarToLocalDateTime(contractData.getPublicationDate()),
            getContractStatusCode(contractData.getStatus()),
            contractData.getVersion(),
            contractData.getModificationDescription(),
            repositoryService.convertBoolean(contractData.isDigitalPurchase()),
            contractData.getDigitalPurchaseCode(),
            repositoryService.convertBoolean(contractData.isProvider()),
            contractData.getProviderCode(),
            repositoryService.convertBoolean(contractData.isChangeContract()),
            contractData.getContractRegNumber(),
            contractData.getName(),
            repositoryService.convertFromXMLGregorianCalendarToLocalDate(contractData.getContractDate()),
            repositoryService.convertFromXMLGregorianCalendarToLocalDate(contractData.getApproveDate())
        );
    }

    private String getContractStatusCode(ContractStatusType contractStatus) {
        if (contractStatus == null) return null;
        else return contractStatus.value();
    }
}
