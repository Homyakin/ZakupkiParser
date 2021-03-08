package ru.homyakin.zakupki.database.contract;

import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.homyakin.zakupki.database.BaseRepository;
import ru.homyakin.zakupki.database.CustomerRepository;
import ru.homyakin.zakupki.exceptions.NoXmlnsException;
import ru.homyakin.zakupki.models._223fz.contract.Contract;
import ru.homyakin.zakupki.models._223fz.contract.ContractDataType;
import ru.homyakin.zakupki.models._223fz.contract.ContractStatusType;
import ru.homyakin.zakupki.models._223fz.contract.PurchaseNoticeInfoType;
import ru.homyakin.zakupki.models._223fz.types.ElectronicPlaceInfoType;
import ru.homyakin.zakupki.utils.RepositoryUtils;

@Component
public class ContractRepository extends BaseRepository<Contract> {
    private static final Logger logger = LoggerFactory.getLogger(ContractRepository.class);
    private final JdbcTemplate jdbcTemplate;
    private final PlanPositionRepository planPositionRepository;
    private final CustomerRepository customerRepository;
    private final PurchaseNoticeInfoRepository purchaseNoticeInfoRepository;
    private final ContractPositionRepository contractPositionRepository;
    private final SupplierRepository supplierRepository;
    private final RepositoryUtils repositoryUtils;

    public ContractRepository(
        DataSource dataSource,
        PlanPositionRepository planPositionRepository,
        CustomerRepository customerRepository,
        PurchaseNoticeInfoRepository purchaseNoticeInfoRepository,
        ContractPositionRepository contractPositionRepository,
        SupplierRepository supplierRepository,
        RepositoryUtils repositoryUtils
    ) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        this.planPositionRepository = planPositionRepository;
        this.customerRepository = customerRepository;
        this.purchaseNoticeInfoRepository = purchaseNoticeInfoRepository;
        this.contractPositionRepository = contractPositionRepository;
        this.supplierRepository = supplierRepository;
        this.repositoryUtils = repositoryUtils;
    }

    @Override
    public void insert(Contract contract) {
        String sql = "INSERT INTO zakupki.contract (guid, registration_number, notice_44fz, " +
            "notice_not_placed_by_fz223p5s4, notice_44_num, lot_44_num, termination, extension," +
            "prolongation, customer_appeale_or_needs_approval, customer_approval_or_antimonopoly_descision_date," +
            "start_execution_term, end_execution_term, plan_position_guid, url_eis, url_vsrz, url_kis_rmis," +
            "create_date_time, customer_inn, placer_inn, detached_org_inn, publication_date, contract_status_code," +
            "version, modification_description, digital_purchase, digital_purchase_code, emergency, provider, provider_code," +
            "change_contract, contract_reg_number, name, contract_date, approve_date, purchase_notice_info_guid," +
            "purchase_notice_info_number, lotGuid, subject_contract, purchase_type_code, resume_date, " +
            "has_subcontractor, has_subcontractor_code, subcontractors_total, has_good_info, additional_info," +
            "price, exchange_rate, exchange_rate_date, rub_price, currency_code, start_execution_date," +
            "end_execution_date, has_okpd_and_okdp_rows, has_okpd2_rows, is_electronic_place, electronic_place_name," +
            "electronic_place_url, electronic_place_publish_date, electronic_place_guid)" +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?," +
            " ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        ContractDataType contractData = contract.getBody().getItem().getContractData();
        logger.info("Inserting contract with guid: {}", contractData.getGuid());
        try {
            String planPositionGuid = null;
            if (contractData.getPlanPosition() != null) {
                planPositionRepository.insert(contractData.getPlanPosition());
                planPositionGuid = contractData.getPlanPosition().getPositionGuid();
            }
            if (contractData.getCustomer().getMainInfo() == null) {
                throw new NoXmlnsException("There is no xmlns in xml");
            }
            customerRepository.insert(contractData.getCustomer().getMainInfo());
            customerRepository.insert(contractData.getPlacer().getMainInfo());
            String detachedOrgInn = null;
            if (contractData.getDetachedOrg() != null) {
                if (contractData.getDetachedOrg().getMainInfo() == null) logger.warn("NO MAIN INFO");
                detachedOrgInn = contractData.getDetachedOrg().getMainInfo().getInn();
            }
            purchaseNoticeInfoRepository.insert(contractData.getPurchaseNoticeInfo());

            jdbcTemplate.update(
                sql,
                contractData.getGuid(),
                contractData.getRegistrationNumber(),
                repositoryUtils.convertBoolean(contractData.isNotice44()),
                repositoryUtils.convertBoolean(contractData.isNoticeNotPlacedByFz223P5S4()),
                checkNotice44NumLength(contractData.getNotice44Num()),
                contractData.getLot44Num(),
                repositoryUtils.convertBoolean(contractData.isTermination()),
                repositoryUtils.convertBoolean(contractData.isExtension()),
                repositoryUtils.convertBoolean(contractData.isProlongation()),
                repositoryUtils.convertBoolean(contractData.isCustomerAppealedOrNeedsApproval()),
                repositoryUtils.convertFromXMLGregorianCalendarToLocalDateTime(contractData.getCustomerApprovalOrAntimonopolyDescisionDate()),
                repositoryUtils.removeExtraSpaces(contractData.getStartExecutionTerm()),
                repositoryUtils.removeExtraSpaces(contractData.getEndExecutionTerm()),
                planPositionGuid,
                contractData.getUrlEIS(),
                contractData.getUrlVSRZ(),
                contractData.getUrlKisRmis(),
                repositoryUtils.convertFromXMLGregorianCalendarToLocalDateTime(contractData.getCreateDateTime()),
                contractData.getCustomer().getMainInfo().getInn(),
                contractData.getPlacer().getMainInfo().getInn(),
                detachedOrgInn,
                repositoryUtils.convertFromXMLGregorianCalendarToLocalDateTime(contractData.getPublicationDate()),
                getContractStatusCode(contractData.getStatus()),
                contractData.getVersion(),
                contractData.getModificationDescription(),
                repositoryUtils.convertBoolean(contractData.isDigitalPurchase()),
                contractData.getDigitalPurchaseCode(),
                repositoryUtils.convertBoolean(contractData.isEmergency()),
                repositoryUtils.convertBoolean(contractData.isProvider()),
                contractData.getProviderCode(),
                repositoryUtils.convertBoolean(contractData.isChangeContract()),
                contractData.getContractRegNumber(),
                contractData.getName(),
                repositoryUtils.convertFromXMLGregorianCalendarToLocalDate(contractData.getContractDate()),
                repositoryUtils.convertFromXMLGregorianCalendarToLocalDate(contractData.getApproveDate()),
                getPurchaseNoticeInfoTypeGuid(contractData.getPurchaseNoticeInfo()),
                getPurchaseNoticeInfoTypeNumber(contractData.getPurchaseNoticeInfo()),
                contractData.getLotGuid(),
                repositoryUtils.removeExtraSpaces(contractData.getSubjectContract()),
                contractData.getPurchaseTypeInfo().getCode(),
                repositoryUtils.convertFromXMLGregorianCalendarToLocalDate(contractData.getResumeDate()),
                repositoryUtils.convertBoolean(contractData.isHasSubcontractor()),
                contractData.getHasSubcontractorCode(),
                contractData.getSubcontractorsTotal(),
                repositoryUtils.convertBoolean(contractData.isHasGoodInfo()),
                repositoryUtils.removeExtraSpaces(contractData.getAdditionalInfo()),
                contractData.getPrice(),
                contractData.getExchangeRate(),
                repositoryUtils.convertFromXMLGregorianCalendarToLocalDate(contractData.getExchangeRateDate()),
                contractData.getRubPrice(),
                repositoryUtils.getCurrencyCode(contractData.getCurrency()),
                repositoryUtils.convertFromXMLGregorianCalendarToLocalDate(contractData.getStartExecutionDate()),
                repositoryUtils.convertFromXMLGregorianCalendarToLocalDate(contractData.getEndExecutionDate()),
                repositoryUtils.convertBoolean(contractData.isHasOkpdAndOkdpRows()),
                repositoryUtils.convertBoolean(contractData.isHasOkpd2Rows()),
                repositoryUtils.convertBoolean(contractData.isIsElectronicPlace()),
                repositoryUtils.removeExtraSpaces(getElectronicPlaceInfoName(contractData.getElectronicPlaceInfo())),
                repositoryUtils.removeExtraSpaces(getElectronicPlaceInfoUrl(contractData.getElectronicPlaceInfo())),
                repositoryUtils.convertFromXMLGregorianCalendarToLocalDateTime(contractData.getElectorincPlacePublishDate()),
                contractData.getElectorincPlaceGuid()
            );
            if (contractData.getContractPositions() != null) {
                for (var position : contractData.getContractPositions().getContractPosition()) {
                    contractPositionRepository.insert(position, contractData.getGuid());
                }
            }
            for (var supplier : contractData.getSupplierInfo()) {
                supplierRepository.insert(supplier, contractData.getGuid());
            }
        } catch (NoXmlnsException e) {
            logger.error("No xmlns in xml (http://zakupki.gov.ru/223fz/types/1)");
            //throw e;
            //TODO add move file
        } catch (DuplicateKeyException ignored) {
            //Same file
            //TODO add check to queue of existing file
        } catch (RuntimeException e) {
            logger.error("Internal database error", e);
        }
    }

    private String checkNotice44NumLength(String s) {
        if (s == null) return null;
        if (s.length() > 500) {
            logger.warn("Length of notice 44 num is too long");
            return "Data too long";
        }
        return s;
    }

    private String getPurchaseNoticeInfoTypeGuid(PurchaseNoticeInfoType purchaseNoticeInfo) {
        if (purchaseNoticeInfo == null) return null;
        else return purchaseNoticeInfo.getGuid();
    }

    private String getPurchaseNoticeInfoTypeNumber(PurchaseNoticeInfoType purchaseNoticeInfo) {
        if (purchaseNoticeInfo == null) return null;
        else return purchaseNoticeInfo.getPurchaseNoticeNumber();
    }

    private String getContractStatusCode(ContractStatusType contractStatus) {
        if (contractStatus == null) return null;
        else return contractStatus.value();
    }

    private String getElectronicPlaceInfoName(ElectronicPlaceInfoType electronicPlaceInfo) {
        if (electronicPlaceInfo == null) return null;
        else return electronicPlaceInfo.getName();
    }

    private String getElectronicPlaceInfoUrl(ElectronicPlaceInfoType electronicPlaceInfo) {
        if (electronicPlaceInfo == null) return null;
        else return electronicPlaceInfo.getUrl();
    }
}

