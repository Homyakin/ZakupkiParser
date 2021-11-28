package ru.homyakin.zakupki;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import ru.homyakin.zakupki.models.ParseFile;
import ru.homyakin.zakupki.models._223fz.contract.Contract;
import ru.homyakin.zakupki.service.parser.ContractParser;
import ru.homyakin.zakupki.service.parser.MainXmlParser;
import ru.homyakin.zakupki.service.storage.ParseFileQueue;
import ru.homyakin.zakupki.service.storage.Queue;
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