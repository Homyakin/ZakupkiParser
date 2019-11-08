package ru.homyakin.zakupki;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import ru.homyakin.documentsinfo.ContractInfo;
import ru.homyakin.documentsinfo._223fz.contract._1.Contract;
import ru.homyakin.service.parser.ContractParser;

import java.math.BigDecimal;

@RunWith(JUnit4.class)
public class ContractParserTest{

    @Test
    public void test(){
        try {
            ContractParser parser = new ContractParser();
            ContractInfo contractInfo = (ContractInfo) parser.parse("./test_files/contract_test.xml");
            if(contractInfo != null) {
                Assert.assertEquals(contractInfo.getGUID(), "b5d4d0e4-ae7f-4c87-93db-a224c503e2e4");
                Assert.assertEquals(contractInfo.getPrice(), new BigDecimal("2014000"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}