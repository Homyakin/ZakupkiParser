package ru.homyakin.zakupki.database.contract_performance;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.homyakin.zakupki.database.CustomerRepository;
import ru.homyakin.zakupki.models._223fz.contract.ContractCompletingInfoExportType;
import ru.homyakin.zakupki.models._223fz.contract.ContractCompletingStatusType;
import ru.homyakin.zakupki.utils.RepositoryUtils;

@Component
public class ContractPerformanceRepository {
    private final static Logger logger = LoggerFactory.getLogger(ContractPerformanceRepository.class);
    private final static String INSERT =
        """
        insert into contract_performance (guid, registration_number, contract_registration_number, status,
         publication_date, placer_inn, is_complete)
         values (?, ?, ?, ?, ?, ?, ?)
        """;
    private final JdbcTemplate jdbcTemplate;
    private final CustomerRepository customerRepository;

    public ContractPerformanceRepository(
        DataSource dataSource,
        CustomerRepository customerRepository
    ) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        this.customerRepository = customerRepository;
    }

    public void insert(ContractCompletingInfoExportType contractCompletingInfo, boolean isComplete) {
        logger.info("Inserting contract_performance with guid {}", contractCompletingInfo.getGuid());
        try {
            var checkResult = checkContractPerformance(contractCompletingInfo.getGuid());
            if (checkResult.isPresent()) {
                var newStatus = RepositoryUtils.convertBoolean(isComplete);
                var oldStatus = checkResult.get();
                if (!Objects.equals(newStatus, oldStatus)) {
                    logger.warn("contract_performance is in status {} but get {}", oldStatus, newStatus);
                }
                return;
            }
            customerRepository.insert(contractCompletingInfo.getPlacer());
            jdbcTemplate.update(
                INSERT,
                contractCompletingInfo.getGuid(),
                contractCompletingInfo.getRegistrationNumber(),
                contractCompletingInfo.getContractRegistrationNumber(),
                mapContractCompletingStatusTypeToString(contractCompletingInfo.getStatus()),
                RepositoryUtils.convertFromXMLGregorianCalendarToLocalDateTime(contractCompletingInfo.getPublicationDate()),
                Optional.ofNullable(contractCompletingInfo.getPlacer())
                    .map(ContractCompletingInfoExportType.Placer::getInn)
                    .orElse(null),
                RepositoryUtils.convertBoolean(isComplete)
            );
        } catch (Exception e) {
            logger.error("Error during inserting purchase protocol {}", contractCompletingInfo.getGuid(), e);
        }
    }

    private String mapContractCompletingStatusTypeToString(ContractCompletingStatusType status) {
        if (status == null) return null;
        return switch (status) {
            case F -> "Редактирование";
            case P -> "Размещено";
            case I -> "Недействительно";
            case M -> "Изменение";
        };
    }

    private Optional<Integer> checkContractPerformance(String guid) {
        String sql = "SELECT is_complete FROM contract_performance WHERE guid = ?";
        List<Integer> result = jdbcTemplate.query(sql, (rs, rowNum) -> rs.getInt("is_complete"), guid);
        return result.stream().findFirst();
    }
}
