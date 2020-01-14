package ru.homyakin.zakupki.database;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.homyakin.zakupki.documentsinfo._223fz.types.OkdpProductType;

import javax.sql.DataSource;
import ru.homyakin.zakupki.documentsinfo._223fz.types.OkeiProductType;
import ru.homyakin.zakupki.documentsinfo._223fz.types.Okpd2ProductType;
import ru.homyakin.zakupki.documentsinfo._223fz.types.Okved2ProductType;
import ru.homyakin.zakupki.documentsinfo._223fz.types.OkvedProductType;

@Component
public class ClassifierRepository {
    private static final Logger logger = LoggerFactory.getLogger(ClassifierRepository.class);
    private final JdbcTemplate jdbcTemplate;

    public ClassifierRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
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
        code = code.toLowerCase();
        name = name.toLowerCase();
        String selectByCode = "SELECT code, name FROM " + table + " WHERE code = ?";
        String selectByName = "SELECT code, name FROM " + table + " WHERE name = ?";
        List<Classifier> resultCode = jdbcTemplate.query(selectByCode,
            new Object[]{code},
            (rs, rowNum) ->
                new Classifier(rs.getString("code"), rs.getString("name"))
        );
        List<Classifier> resultName = jdbcTemplate.query(selectByName,
            new Object[]{name},
            (rs, rowNum) ->
                new Classifier(rs.getString("code"), rs.getString("name"))
        );
        if (resultCode.size() == 0) {
            logger.warn("{}: incorrect code {}", table, code);
            if (resultName.size() == 0) {
                logger.warn("{}: incorrect code and name: {}; {}", table, code, name);
                return new Classifier(
                    null,
                    "Incorrect code and name: " + code + "; " + name
                );
            } else if (resultName.size() > 1) {
                logger.warn("{}: several variants for name {}", table, name);
                return new Classifier(
                    null,
                    "Several codes for name: " + name
                );
            } else {
                return new Classifier(
                    resultName.get(0).getCode(),
                    name
                );
            }
        } else {
            if (resultCode.get(0).getName().equals(name)) {
                return new Classifier(
                    code,
                    name
                );
            } else {
                if (resultName.size() == 0) {
                    logger.warn("{}: name {} doesn't exist for code {}", table, name, code);
                    return new Classifier(
                        code,
                        "Name doesn't exist: " + name
                    );
                } else {
                    logger.warn("{}: code and name don't match: {}; {}", table, code, name);
                    return new Classifier(
                        null,
                        "Code and name don't match: " + code + "; " + name
                    );
                }
            }
        }
    }

    public static class Classifier {
        private final String code;
        private final String name;

        public Classifier(String code, String name) {
            this.code = code;
            this.name = name;
        }

        public String getCode() {
            return code;
        }

        public String getName() {
            return name;
        }
    }
}
