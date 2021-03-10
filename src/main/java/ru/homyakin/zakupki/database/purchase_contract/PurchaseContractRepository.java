package ru.homyakin.zakupki.database.purchase_contract;

import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.homyakin.zakupki.database.CustomerRepository;
import ru.homyakin.zakupki.database.DeliveryPlaceRepository;
import ru.homyakin.zakupki.models._223fz.purchase.PurchaseContract;
import ru.homyakin.zakupki.utils.RepositoryUtils;

@Component
public class PurchaseContractRepository {
    private final static Logger logger = LoggerFactory.getLogger(PurchaseContractRepository.class);
    private final JdbcTemplate jdbcTemplate;
    private final RepositoryUtils repositoryUtils;
    private final DeliveryPlaceRepository deliveryPlaceRepository;
    private final NonResidentInfoRepository nonResidentInfoRepository;
    private final ContractItemRepository contractItemRepository;
    private final PurchaseContractLotRepository purchaseContractLotRepository;
    private final PurchaseInfoRepository purchaseInfoRepository;
    private final CustomerRepository customerRepository;
    private final PurchaseContractSupplierRepository purchaseContractSupplierRepository;

    public PurchaseContractRepository(
        DataSource dataSource,
        RepositoryUtils repositoryUtils,
        DeliveryPlaceRepository deliveryPlaceRepository,
        NonResidentInfoRepository nonResidentInfoRepository,
        ContractItemRepository contractItemRepository,
        PurchaseContractLotRepository purchaseContractLotRepository,
        PurchaseInfoRepository purchaseInfoRepository,
        CustomerRepository customerRepository,
        PurchaseContractSupplierRepository purchaseContractSupplierRepository
    ) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        this.repositoryUtils = repositoryUtils;
        this.deliveryPlaceRepository = deliveryPlaceRepository;
        this.nonResidentInfoRepository = nonResidentInfoRepository;
        this.contractItemRepository = contractItemRepository;
        this.purchaseContractLotRepository = purchaseContractLotRepository;
        this.purchaseInfoRepository = purchaseInfoRepository;
        this.customerRepository = customerRepository;
        this.purchaseContractSupplierRepository = purchaseContractSupplierRepository;
    }

    public void insert(PurchaseContract purchaseContract) {
        String sql = "INSERT INTO zakupki.purchase_contract (guid, registration_number, create_date_time," +
            "contract_create_date, lot_guid, currency_code, sum, purchase_info_guid, placer_inn, customer_inn," +
            "supplier_guid, non_resident_info_guid, delivery_place_guid, delivery_place_indication_code, type, name," +
            "additional_info, publication_date_time, contract_status_code, version, modification_description)" +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        String supplierGuid = null;
        try {
            var data = purchaseContract.getBody().getItem().getPurchaseContractData();
            purchaseContractLotRepository.insert(data.getLot());
            customerRepository.insert(data.getCustomerInfo());
            customerRepository.insert(data.getPlacer().getMainInfo());

            if (data.getSupplier() != null) {
                supplierGuid = purchaseContractSupplierRepository.insert(data.getSupplier().getMainInfo()).orElse(null);
            }
            jdbcTemplate.update(
                sql,
                data.getGuid(),
                data.getRegistrationNumber(),
                repositoryUtils.convertFromXMLGregorianCalendarToLocalDateTime(data.getCreateDateTime()),
                repositoryUtils.convertFromXMLGregorianCalendarToLocalDate(data.getContractCreateDate()),
                data.getLot().getGuid(),
                repositoryUtils.getCurrencyCode(data.getCurrency()),
                data.getSum(),
                purchaseInfoRepository.insert(data.getPurchaseInfo()).orElse(null),
                data.getPlacer().getMainInfo().getInn(),
                data.getCustomerInfo().getInn(),
                supplierGuid,
                nonResidentInfoRepository.insert(data.getNonResidentInfo()).orElse(null),
                deliveryPlaceRepository.insert(data.getDeliveryPlace()).orElse(null),
                data.getDeliveryPlaceIndication().value(),
                data.getType().value(),
                data.getName(),
                data.getAdditionalInfo(),
                repositoryUtils.convertFromXMLGregorianCalendarToLocalDateTime(data.getPublicationDateTime()),
                data.getStatus().value(),
                data.getVersion(),
                data.getModificationDescription()
            );
            if (data.getContractItems() != null) {
                for (var item : data.getContractItems().getContractItem()) {
                    contractItemRepository.insert(item, data.getGuid());
                }
            }
        } catch (DuplicateKeyException ignore) {
        } catch (Exception e) {
            logger.error("Error during inserting in purchase_contract", e);
        }
    }
}
