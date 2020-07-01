package ru.homyakin.zakupki;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import ru.homyakin.zakupki.models.ContractInfo;
import ru.homyakin.zakupki.service.parser.ContractParser;

import java.math.BigDecimal;

@RunWith(JUnit4.class)
public class ParserTest {

    private final String GUID = "b5d4d0e4-ae7f-4c87-93db-a224c503e2e4";
    private final BigDecimal price = new BigDecimal("2014000");

    private String getFilePath(String fileName) {
        return getClass().getClassLoader().getResource(fileName).getFile();
    }

    @Test
    public void contractParserTest() {
        try {
            ContractInfo contractInfo = new ContractInfo(ContractParser.parse(
                    getFilePath("test_files/contract/contract_test.xml")
            ).orElseThrow(() -> new IllegalArgumentException()));
            Assert.assertNotNull(contractInfo);
            Assert.assertEquals(GUID, contractInfo.getGUID());
            Assert.assertEquals(price, contractInfo.getPrice());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testParsingEmptyFile() {
        ContractParser.parse(getFilePath("test_files/contract/contract_empty.xml"));
    }
}