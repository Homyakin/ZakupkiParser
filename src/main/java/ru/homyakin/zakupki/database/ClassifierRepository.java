package ru.homyakin.zakupki.database;

import java.util.List;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class ClassifierRepository {
    private static final Logger logger = LoggerFactory.getLogger(ClassifierRepository.class);
    private final JdbcTemplate jdbcTemplate;

    public ClassifierRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public String getOkatoCode(String code) {
        if (code == null) return null;
        String sql = "SELECT code FROM okato WHERE code = ?";
        List<String> result = jdbcTemplate.query(
            sql,
            new Object[]{code},
            (rs, rowNum) -> rs.getString("code")
        );
        if (result.size() == 0) {
            logger.warn("okato: invalid code: {}", code);
            return null;
        } else return code;
    }

    public Classifier getOktmo(String code, String name) {
        if (name != null) return getClassifier("oktmo", code, name);
        String sql = "SELECT code FROM oktmo WHERE code = ?";
        List<String> result = jdbcTemplate.query(
            sql, new Object[]{code},
            (rs, rowNum) ->
                rs.getString("code")
        );
        if (result.size() == 0) {
            logger.warn("oktmo: invalid code: {}", code);
            return null;
        } else return new Classifier(code, null);
    }

    public Classifier getClassifier(String table, String code, String name) {
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
                logger.error("{}: incorrect code and name: {}; {}", table, code, name);
                return new Classifier(
                    null,
                    "Incorrect code and name: " + code + "; " + name
                );
            } else if (resultName.size() > 1) {
                logger.error("{}: several variants for name {}", table, name);
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
                    logger.error("{}: code and name don't match: {}; {}", table, code, name);
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
