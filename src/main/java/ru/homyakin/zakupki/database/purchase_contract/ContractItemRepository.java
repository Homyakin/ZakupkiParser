package ru.homyakin.zakupki.database.purchase_contract;

import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.homyakin.zakupki.database.ClassifierService;
import ru.homyakin.zakupki.database.DeliveryPlaceRepository;
import ru.homyakin.zakupki.models._223fz.types.ContractItemType;

@Component
public class ContractItemRepository {
    private final static Logger logger = LoggerFactory.getLogger(ContractItemRepository.class);
    private final JdbcTemplate jdbcTemplate;
    private final ClassifierService classifierService;
    private final DeliveryPlaceRepository deliveryPlaceRepository;

    public ContractItemRepository(
        DataSource dataSource,
        ClassifierService classifierService,
        DeliveryPlaceRepository deliveryPlaceRepository
    ) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        this.classifierService = classifierService;
        this.deliveryPlaceRepository = deliveryPlaceRepository;
    }

    public void insert(ContractItemType contractItem, String purchaseContractGuid) {
        var sql = "INSERT INTO zakupki.contract_item (purchase_contract_guid, ordinal_number, okdp_code, okdp_name," +
            "okpd2_code, okpd2_name, okved_code, okved_name, okved2_code, okved2_name, okei_code, okei_name, qty," +
            "delivery_place_guid, additional_info) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            jdbcTemplate.update(
                sql,
                purchaseContractGuid,
                contractItem.getOrdinalNumber(),
                classifierService.getClassifierCode(contractItem.getOkdp()),
                classifierService.getClassifierName(contractItem.getOkdp()),
                classifierService.getClassifierCode(contractItem.getOkpd2()),
                classifierService.getClassifierName(contractItem.getOkpd2()),
                classifierService.getClassifierCode(contractItem.getOkved()),
                classifierService.getClassifierName(contractItem.getOkved()),
                classifierService.getClassifierCode(contractItem.getOkved2()),
                classifierService.getClassifierName(contractItem.getOkved2()),
                classifierService.getClassifierCode(contractItem.getOkei()),
                classifierService.getClassifierName(contractItem.getOkei()),
                contractItem.getQty(),
                deliveryPlaceRepository.insert(contractItem.getDeliveryPlace()).orElse(null),
                contractItem.getAdditionalInfo()
            );
        } catch (Exception e) {
            logger.error("Error during inserting in contract_item", e);
        }
    }
}
