package ru.homyakin.zakupki;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import ru.homyakin.zakupki.utils.CommonUtils;
import ru.homyakin.zakupki.utils.TestUtils;

@RunWith(JUnit4.class)
public class ExtractRegionFromFilePathTest {
    private final CommonUtils commonUtils = new CommonUtils();

    @Test
    public void successExtractOneWord() {
        var filePath = TestUtils.getFilePath(
            "test_files/contract/contract_Moskva_20150104_000000_20150104_235959_daily_001.xml"
        );
        Assert.assertEquals("Moskva", commonUtils.extractRegionFromFilePath(filePath));
    }

    @Test
    public void successExtractTwoUnderlinings() {
        var filePath = TestUtils.getFilePath(
            "test_files/contract/contract_Altayskii__krai_20191217_000000_20191217_235959_daily_001.xml"
        );
        Assert.assertEquals("Altayskii__krai", commonUtils.extractRegionFromFilePath(filePath));
    }

    @Test
    public void successExtractManyWords() {
        var filePath = TestUtils.getFilePath(
            "test_files/contract/contract_Severnaia_Osetiya_Alaniia_Resp_20150101_000000_20150131_235959_001.xml"
        );
        Assert.assertEquals("Severnaia_Osetiya_Alaniia_Resp", commonUtils.extractRegionFromFilePath(filePath));
    }
}
