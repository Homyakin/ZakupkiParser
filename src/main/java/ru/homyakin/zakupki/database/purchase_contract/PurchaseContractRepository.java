package ru.homyakin.zakupki.database.purchase_contract;

import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.homyakin.zakupki.database.CustomerRepository;
import ru.homyakin.zakupki.database.DeliveryPlaceRepository;
import ru.homyakin.zakupki.database.NonResidentInfoRepository;
import ru.homyakin.zakupki.database.SupplierInfoRepository;
import ru.homyakin.zakupki.models._223fz.purchase.PurchaseContract;
import ru.homyakin.zakupki.utils.RepositoryUtils;

@Component
public class PurchaseContractRepository {
    private final static Logger logger = LoggerFactory.getLogger(PurchaseContractRepository.class);
    private final JdbcTemplate jdbcTemplate;
    private final DeliveryPlaceRepository deliveryPlaceRepository;
    private final NonResidentInfoRepository nonResidentInfoRepository;
    private final ContractItemRepository contractItemRepository;
    private final PurchaseContractLotRepository purchaseContractLotRepository;
    private final PurchaseInfoRepository purchaseInfoRepository;
    private final CustomerRepository customerRepository;
    private final SupplierInfoRepository supplierInfoRepository;

    public PurchaseContractRepository(
        DataSource dataSource,
        DeliveryPlaceRepository deliveryPlaceRepository,
        NonResidentInfoRepository nonResidentInfoRepository,
        ContractItemRepository contractItemRepository,
        PurchaseContractLotRepository purchaseContractLotRepository,
        PurchaseInfoRepository purchaseInfoRepository,
        CustomerRepository customerRepository,
        SupplierInfoRepository supplierInfoRepository
    ) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        this.deliveryPlaceRepository = deliveryPlaceRepository;
        this.nonResidentInfoRepository = nonResidentInfoRepository;
        this.contractItemRepository = contractItemRepository;
        this.purchaseContractLotRepository = purchaseContractLotRepository;
        this.purchaseInfoRepository = purchaseInfoRepository;
        this.customerRepository = customerRepository;
        this.supplierInfoRepository = supplierInfoRepository;
    }

    public void insert(PurchaseContract purchaseContract, String region) {
        String sql = "INSERT INTO zakupki.purchase_contract (guid, registration_number, create_date_time," +
            "contract_create_date, lot_guid, currency_code, sum, purchase_info_guid, placer_inn, customer_inn," +
            "supplier_guid, non_resident_info_guid, delivery_place_guid, delivery_place_indication_code, type, name," +
            "additional_info, publication_date_time, contract_status_code, version, modification_description, region_name)" +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            var data = purchaseContract.getBody().getItem().getPurchaseContractData();
            purchaseContractLotRepository.insert(data.getLot());
            customerRepository.insert(data.getCustomerInfo());
            customerRepository.insert(data.getPlacer().getMainInfo());
            String supplierGuid = null;
            if (data.getSupplier() != null) {
                supplierGuid = supplierInfoRepository.insert(data.getSupplier().getMainInfo()).orElse(null);
            }
            jdbcTemplate.update(
                sql,
                data.getGuid(),
                data.getRegistrationNumber(),
                RepositoryUtils.convertFromXMLGregorianCalendarToLocalDateTime(data.getCreateDateTime()),
                RepositoryUtils.convertFromXMLGregorianCalendarToLocalDate(data.getContractCreateDate()),
                data.getLot().getGuid(),
                RepositoryUtils.getCurrencyCode(data.getCurrency()),
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
                RepositoryUtils.convertFromXMLGregorianCalendarToLocalDateTime(data.getPublicationDateTime()),
                data.getStatus().value(),
                data.getVersion(),
                data.getModificationDescription(),
                region
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
