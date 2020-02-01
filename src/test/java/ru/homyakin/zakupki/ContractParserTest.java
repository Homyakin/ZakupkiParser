package ru.homyakin.zakupki;

import java.nio.file.Files;
import java.nio.file.Paths;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import ru.homyakin.zakupki.models._223fz.contract.Contract;
import ru.homyakin.zakupki.models._223fz.contract.ContractDataType;
import ru.homyakin.zakupki.service.parser.ContractParser;


@RunWith(JUnit4.class)
public class ContractParserTest {

    private final String GUID = "5244776b-ebf6-415d-b58c-c8d5f7faefda";

    private String getFilePath(String fileName) {
        return getClass().getClassLoader().getResource(fileName).getFile();
    }

    @Test
    public void contractParserTest() {
        try {
            Contract contract = ContractParser.parse(
                getFilePath("test_files/contract/contract_without_xmlns.xml")
            ).orElseThrow(() -> new IllegalArgumentException());
            ContractDataType contractData = contract.getBody().getItem().getContractData();
            Contract contract2 = ContractParser.parse(
                getFilePath("test_files/contract/contract_test2.xml")
            ).orElseThrow(() -> new IllegalArgumentException());
            ContractDataType contractData2 = contract2.getBody().getItem().getContractData();
            Assert.assertNotNull(contract);
            Assert.assertEquals(GUID, contractData.getGuid());
            String f1 = new String(Files.readAllBytes(Paths.get(getFilePath("test_files/contract/contract_without_xmlns.xml"))));
            String f2 = new String(Files.readAllBytes(Paths.get(getFilePath("test_files/contract/contract_test2.xml"))));
            //Assert.assertEquals(f1, f2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}