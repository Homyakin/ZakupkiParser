package ru.homyakin.zakupki.database.contract_performance;

import org.springframework.stereotype.Component;
import ru.homyakin.zakupki.models.Folder;
import ru.homyakin.zakupki.models._223fz.contract.ContractCancellationInformation;
import ru.homyakin.zakupki.models._223fz.contract.PerformanceContractInformation;

@Component
public class ContractPerformanceProxy {
    private final ContractPerformanceRepository contractPerformanceRepository;

    public ContractPerformanceProxy(ContractPerformanceRepository contractPerformanceRepository) {
        this.contractPerformanceRepository = contractPerformanceRepository;
    }
    
    public void insert(Object parsedObject) {
        if (parsedObject instanceof ContractCancellationInformation contractCancellationInformation) {
            for (var contractCompletingInfo: contractCancellationInformation.getContractCancellation()) {
                contractPerformanceRepository.insert(contractCompletingInfo, false);
            }
        } else if (parsedObject instanceof PerformanceContractInformation performanceContractInformation) {
            for (var contractCompletingInfo: performanceContractInformation.getPerformanceContract()) {
                contractPerformanceRepository.insert(contractCompletingInfo, true);
            }
        }
    }
}
