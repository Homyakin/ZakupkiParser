package ru.homyakin.zakupki;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import ru.homyakin.zakupki.service.parser.ContractParser;


@RunWith(JUnit4.class)
public class ContractParserTest {

    private String getFilePath(String fileName) {
        return getClass().getClassLoader().getResource(fileName).getFile();
    }

    @Test
    public void contractWithoutXmlNsParserTest() {
        var contract = ContractParser.parse(getFilePath("test_files/contract/contract_without_xmlns.xml")).get();
        var contractData = contract.getBody().getItem().getContractData();
        Assert.assertNotNull(contractData.getCustomer().getMainInfo());
    }

    @Test
    public void contractBrokenXmlNsParserTest() {
        var contract = ContractParser.parse(getFilePath("test_files/contract/contract_broken_xmlns.xml")).get();
        var contractData = contract.getBody().getItem().getContractData();
        Assert.assertNotNull(contractData.getCustomer().getMainInfo());
    }
}