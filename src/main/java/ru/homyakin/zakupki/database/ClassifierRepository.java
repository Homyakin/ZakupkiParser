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
    private static final Logger logger = LoggerFactory.getLogger(CustomerRepository.class);
    private final JdbcTemplate jdbcTemplate;

    public ClassifierRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Classifier getClassifier(OkdpProductType okdp) {
        if(okdp == null) return null;
        return getClassifier("okdp", okdp.getCode(), okdp.getName());
    }

    public Classifier getClassifier(Okpd2ProductType okpd2) {
        if(okpd2 == null) return null;
        return getClassifier("okpd2", okpd2.getCode(), okpd2.getName());
    }

    public Classifier getClassifier(OkvedProductType okved) {
        if(okved == null) return null;
        return getClassifier("okved", okved.getCode(), okved.getName());
    }

    public Classifier getClassifier(Okved2ProductType okved2) {
        if(okved2 == null) return null;
        return getClassifier("okved2", okved2.getCode(), okved2.getName());
    }

    public Classifier getClassifier(OkeiProductType okei) {
        if(okei == null) return null;
        return getClassifier("okei", okei.getCode(), okei.getName());
    }

    private Classifier getClassifier(String table, String code, String name) {
        String sql = "SELECT code, name FROM " + table + " WHERE code = ? or name = ?";
        List<Classifier> result = jdbcTemplate.query(sql,
            new Object[]{code, name},
            (rs, rowNum) ->
                new Classifier(rs.getString("code"), rs.getString("name"))
        );
        if (result.size() > 1) {
            logger.warn("Code and name don't match: {}; {}", code, name);
            return new Classifier(
                null,
                "Code and name don't match: " + code + "; " + name
            );
        } else if (result.size() == 0) {
            logger.warn("Incorrect code and name: {}; {}", code, name);
            return new Classifier(
                null,
                "Incorrect code and name: " + code + "; " + name
            );
        } else {
            if (code.equals(result.get(0).getCode())) {
                if (name.equals(result.get(0).getName())) {
                    return result.get(0);
                } else {
                    logger.warn("Name {} doesn't match for code {}", name, code);
                    return new Classifier(
                        code,
                        "Name doesn't match: " + name
                    );
                }
            } else {
                logger.warn("Code {} doesn't match for name {}", code, name);
                return new Classifier(
                    result.get(0).getCode(),
                    name
                );
            }
        }
    }

    public class Classifier {
        private String code;
        private String name;

        public Classifier(String code, String name) {
            this.code = code;
            this.name = name;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
