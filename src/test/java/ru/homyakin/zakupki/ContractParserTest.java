package ru.homyakin.zakupki;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import ru.homyakin.zakupki.models._223fz.contract.Contract;
import ru.homyakin.zakupki.service.parser.ContractParser;
import ru.homyakin.zakupki.utils.TestUtils;


@RunWith(JUnit4.class)
public class ContractParserTest {

    @Test
    public void contractWithoutXmlNsParserTest() {
        var object = ContractParser.parse(
            TestUtils.getFilePath("test_files/contract/contract_without_xmlns.xml")
        ).get();
        if (object instanceof Contract contract) {
            var contractData = contract.getBody().getItem().getContractData();
            Assert.assertNotNull(contractData.getCustomer().getMainInfo());
        } else {
            throw new RuntimeException();
        }
    }

    @Test
    public void contractBrokenXmlNsParserTest() {
        var object = ContractParser.parse(
            TestUtils.getFilePath("test_files/contract/contract_broken_xmlns.xml")
        ).get();
        if (object instanceof Contract contract) {
            var contractData = contract.getBody().getItem().getContractData();
            Assert.assertNotNull(contractData.getCustomer().getMainInfo());
        } else throw new RuntimeException();
    }
}