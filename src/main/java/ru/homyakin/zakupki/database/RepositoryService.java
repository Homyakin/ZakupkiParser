package ru.homyakin.zakupki.database;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.homyakin.zakupki.documentsinfo._223fz.types.CurrencyType;
import ru.homyakin.zakupki.documentsinfo._223fz.types.OkdpProductType;
import ru.homyakin.zakupki.documentsinfo._223fz.types.OkeiProductType;
import ru.homyakin.zakupki.documentsinfo._223fz.types.Okpd2ProductType;
import ru.homyakin.zakupki.documentsinfo._223fz.types.Okved2ProductType;
import ru.homyakin.zakupki.documentsinfo._223fz.types.OkvedProductType;
import ru.homyakin.zakupki.database.ClassifierRepository.Classifier;

import javax.xml.datatype.XMLGregorianCalendar;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class RepositoryService {
    private final static Logger logger = LoggerFactory.getLogger(RepositoryService.class);
    private final ClassifierRepository classifierRepository;
    private final PurchaseCategoryRepository purchaseCategoryRepository;

    public RepositoryService(
        ClassifierRepository classifierRepository,
        PurchaseCategoryRepository purchaseCategoryRepository
    ) {
        this.classifierRepository = classifierRepository;
        this.purchaseCategoryRepository = purchaseCategoryRepository;
    }

    public Integer convertBoolean(Boolean statement) {
        if (statement == null) return null;
        else return statement ? 1 : 0;
    }

    public String getCurrencyCode(CurrencyType currency) {
        if (currency == null) return null;
        if (currency.getCode() != null) return removeExtraSpaces(currency.getCode());
        else return removeExtraSpaces(currency.getLetterCode());
    }

    public LocalDate convertFromXMLGregorianCalendarToLocalDate(XMLGregorianCalendar xmlTime) {
        if (xmlTime == null) return null;
        return LocalDate.of(
            xmlTime.getYear(),
            xmlTime.getMonth(),
            xmlTime.getDay()
        );
    }

    public LocalDateTime convertFromXMLGregorianCalendarToLocalDateTime(XMLGregorianCalendar xmlTime) {
        if (xmlTime == null) return null;
        return LocalDateTime.of(
            xmlTime.getYear(),
            xmlTime.getMonth(),
            xmlTime.getDay(),
            xmlTime.getHour(),
            xmlTime.getMinute(),
            xmlTime.getSecond()
        );
    }

    public String validateOkpo(String okpo) {
        okpo = removeExtraSpaces(okpo);
        if (okpo == null) return null;
        if (okpo.length() > 8) {
            logger.warn("okpo: invalid length: {}", okpo);
            return null;
        }
        return okpo;
    }

    public String removeExtraSpaces(String s) {
        if (s == null) return null;
        return s.trim().replaceAll(" +", " ");
    }

    public String getOkatoCode(String code) {
        return getOkatoCode(code);
    }

    public Classifier getClassifier(OkdpProductType okdp) {
        if (okdp == null) return null;
        return getClassifier("okdp", okdp.getCode(), okdp.getName());
    }

    public Classifier getClassifier(Okpd2ProductType okpd2) {
        if (okpd2 == null) return null;
        return getClassifier("okpd2", okpd2.getCode(), okpd2.getName());
    }

    public Classifier getClassifier(OkvedProductType okved) {
        if (okved == null) return null;
        return getClassifier("okved", okved.getCode(), okved.getName());
    }

    public Classifier getClassifier(Okved2ProductType okved2) {
        if (okved2 == null) return null;
        return getClassifier("okved2", okved2.getCode(), okved2.getName());
    }

    public Classifier getClassifier(OkeiProductType okei) {
        if (okei == null) return null;
        return getClassifier("okei", okei.getCode(), okei.getName());
    }

    private Classifier getClassifier(String table, String code, String name) {
        return classifierRepository.getClassifier(table, removeExtraSpaces(code), removeExtraSpaces(name));
    }

    public Long getCategoryCode(Long purchaseCategory) {
        return purchaseCategoryRepository.getCategoryCode(purchaseCategory);
    }
}
