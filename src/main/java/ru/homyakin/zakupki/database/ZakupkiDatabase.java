package ru.homyakin.zakupki.database;

import ru.homyakin.zakupki.documentsinfo.ContractInfo;
import ru.homyakin.zakupki.documentsinfo._223fz.contract.PositionType;
import ru.homyakin.zakupki.documentsinfo._223fz.contract.SupplierMainType;
import ru.homyakin.zakupki.documentsinfo._223fz.types.CurrencyType;
import ru.homyakin.zakupki.documentsinfo._223fz.types.CustomerMainInfoType;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class ZakupkiDatabase {
    private final String USER = "root";
    private final String PSSWD = "12345";
    private final String HOST_NAME = "localhost";
    private final String DB_NAME = "zakupki";
    private Connection conn;

    public ZakupkiDatabase() {
        String connectionURL = "jdbc:mysql://" + HOST_NAME + ":3306/" + DB_NAME + "?useUnicode=true&serverTimezone=UTC";
        try {
            conn = DriverManager.getConnection(connectionURL, USER, PSSWD);
        } catch (SQLException e) {
            //TODO add log and custom RTE
            throw new RuntimeException();
        }
    }

    private void insertCustomer(CustomerMainInfoType customer) {
        String sql = "INSERT INTO customers (customer_INN, customer_OGRN, customer_KPP, customer_fullName,"
                + "customer_shortName) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setString(1, customer.getInn());
            statement.setString(2, customer.getOgrn());
            statement.setString(3, customer.getKpp());
            if (customer.getFullName() != null) {
                statement.setString(4, customer.getFullName());
            } else {
                statement.setNull(4, Types.VARCHAR);
            }
            if (customer.getShortName() != null) {
                statement.setString(5, customer.getShortName());
            } else {
                statement.setNull(5, Types.VARCHAR);
            }

            statement.executeUpdate();
        } catch (SQLException e) {
            // TODO add custom exception and log
            if (!e.getMessage().contains("Duplicate entry")) {
                e.printStackTrace();
            }
        }
    }

    private void insertCurrency(CurrencyType currency) {
        String sql = "INSERT INTO currencies (currency_code, currency_digitalCode, currency_name) VALUES (?, ?, ?)";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setString(1, currency.getLetterCode());
            if (currency.getDigitalCode() != null) {
                statement.setString(2, currency.getDigitalCode());
            } else {
                if (currency.getCode() != null) {
                    statement.setString(2, currency.getCode());
                }
                statement.setNull(2, Types.VARCHAR);
            }

            statement.setString(3, currency.getName());

            statement.executeUpdate();
        } catch (SQLException e) {
            // TODO add custom exception and log
            if (!e.getMessage().contains("Duplicate entry")) {
                e.printStackTrace();
            }
        }
    }

    private void insertSupplier(SupplierMainType supplier) {
        //TODO replace hashName to auto generated id
        String sql = "INSERT INTO suppliers (supplier_hashName, type, provider, nonResident, supplier_INN, "
                + "supplier_shortName, supplier_name) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, supplier.getName().hashCode());
            statement.setString(2, supplier.getType().value()); //TODO fix enum
            statement.setInt(3, supplier.isProvider() ? 1 : 0);
            statement.setInt(4, supplier.isNonResident() ? 1 : 0);
            if (supplier.getInn() != null) {
                statement.setString(5, supplier.getInn());
            } else {
                statement.setNull(5, Types.VARCHAR);
            }
            if (supplier.getShortName() != null) {
                statement.setString(6, supplier.getShortName());
            } else {
                statement.setNull(6, Types.VARCHAR);
            }

            statement.setString(7, supplier.getName());

            statement.executeUpdate();
        } catch (SQLException e) {
            if (!e.getMessage().contains("Duplicate entry")) {
                e.printStackTrace();
            }
        }
    }

    private void insertSupplierToContract(ContractInfo contract) {
        //TODO replace hashName to auto generated id
        String sql = "INSERT INTO suppliers_to_contracts (supplier_hashName, contract_GUID)"
                + " VALUES (?, ?)";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            for (SupplierMainType supplier : contract.getSupplier()) {
                statement.setInt(1, supplier.getName().hashCode());
                statement.setString(2, contract.getGUID());
            }

            statement.executeUpdate();
        } catch (SQLException e) {
            if (!e.getMessage().contains("Duplicate entry")) {
                e.printStackTrace();
            }
        }
    }

    private void insertOK(PositionType position) {
        //TODO make different tables for each OK
        String sql = "INSERT INTO ok_info (ok_code, ok_type, ok_name) VALUES(?, ?, ?)";
        if (position.getOkdp() != null) {
            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setString(1, position.getOkdp().getCode());
                statement.setString(2, "OKDP");
                statement.setString(3, position.getOkdp().getName());
                statement.executeUpdate();
            } catch (SQLException e) {
                if (!e.getMessage().contains("Duplicate entry")) {
                    e.printStackTrace();
                }
            } catch (NullPointerException ignored) {

            }
        }

        if (position.getOkpd() != null) {
            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setString(1, position.getOkpd().getCode());
                statement.setString(2, "OKDP");
                statement.setString(3, position.getOkpd().getName());
                statement.executeUpdate();
            } catch (SQLException e) {
                if (!e.getMessage().contains("Duplicate entry")) {
                    e.printStackTrace();
                }
            } catch (NullPointerException ignored) {

            }
        }

        if (position.getOkpd2() != null) {
            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setString(1, position.getOkpd2().getCode());
                statement.setString(2, "OKDP");
                statement.setString(3, position.getOkpd2().getName());
                statement.executeUpdate();
            } catch (SQLException e) {
                if (!e.getMessage().contains("Duplicate entry")) {
                    e.printStackTrace();
                }
            } catch (NullPointerException ignored) {

            }
        }

        if (position.getOkei() != null) {
            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setString(1, position.getOkei().getCode());
                statement.setString(2, "OKDP");
                statement.setString(3, position.getOkei().getName());
                statement.executeUpdate();
            } catch (SQLException e) {
                if (!e.getMessage().contains("Duplicate entry")) {
                    e.printStackTrace();
                }
            } catch (NullPointerException ignored) {

            }
        }

    }

    private void insertContractPositions(List<PositionType> contractPositions, String contractGUID) {
        String sql = "INSERT INTO contract_positions (contract_GUID, ordinalNumber, position_GUID, position_name, OKDP_code,"
                + "OKPD_code, OKPD2_code, OKEI_code, qty) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        for (PositionType position : contractPositions) {

            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setString(1, contractGUID);
                statement.setInt(2, position.getOrdinalNumber());
                if (position.getGuid() != null) {
                    statement.setString(3, position.getGuid());
                } else {
                    statement.setNull(3, Types.VARCHAR);
                }
                if (position.getName() != null) {
                    statement.setString(4, position.getName());
                } else {
                    statement.setNull(4, Types.VARCHAR);
                }
                if (position.getOkdp() != null) {
                    statement.setString(5, position.getOkdp().getCode());
                } else {
                    statement.setNull(5, Types.VARCHAR);
                }
                if (position.getOkpd() != null) {
                    statement.setString(6, position.getOkdp().getCode());
                } else {
                    statement.setNull(6, Types.VARCHAR);
                }
                if (position.getOkpd2() != null) {
                    statement.setString(7, position.getOkpd2().getCode());
                } else {
                    statement.setNull(7, Types.VARCHAR);
                }
                if (position.getOkei() != null) {
                    statement.setString(8, position.getOkei().getCode());
                } else {
                    statement.setNull(8, Types.VARCHAR);
                }
                if (position.getQty() != null) {
                    statement.setBigDecimal(9, position.getQty());
                } else {
                    statement.setNull(9, Types.DECIMAL);
                }


                statement.executeUpdate();
            } catch (SQLException e) {
                if (!e.getMessage().contains("Duplicate entry")) {
                    e.printStackTrace();
                }
            }
            insertOK(position);
        }
    }

    public void insertContract(ContractInfo contract) {
        insertCustomer(contract.getCustomer());
        insertCurrency(contract.getCurrency());

        String sql = "INSERT INTO contracts (contract_GUID, createDateTime, contractDate, startExecutionDate,"
                + "endExecutionDate, customer_INN, purchaseType_code, purchaseType_name, price, rubPrice, "
                + "currency_code) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setString(1, contract.getGUID());
            statement.setTimestamp(2, Timestamp.valueOf(LocalDateTime.of(contract.getCreateDateTime(),
                    LocalTime.MIDNIGHT))); //TODO fix Timestamp in database
            statement.setDate(3, Date.valueOf(contract.getContractDate()));
            if (contract.getStartExecutionDate() != null) {
                statement.setDate(4, Date.valueOf(contract.getStartExecutionDate()));
            } else {
                statement.setNull(4, Types.DATE);
            }
            if (contract.getEndExecutionDate() != null) {
                statement.setDate(5, Date.valueOf(contract.getEndExecutionDate()));
            } else {
                statement.setNull(5, Types.DATE);
            }
            statement.setString(6, contract.getCustomer().getInn());
            statement.setString(7, contract.getPurchaseType().getCode());
            if (contract.getPurchaseType().getName() != null) {
                //TODO add table to PurchaseType
                statement.setString(8, contract.getPurchaseType().getName());
            } else {
                statement.setNull(8, Types.VARCHAR);
            }

            statement.setBigDecimal(9, contract.getPrice());
            if (contract.getRubPrice().isPresent()) {
                statement.setBigDecimal(10, contract.getRubPrice().get());
            } else {
                statement.setNull(10, Types.DECIMAL);
            }

            statement.setString(11, contract.getCurrency().getLetterCode());

            statement.executeUpdate();
        } catch (SQLException e) {
            if (!e.getMessage().contains("Duplicate entry")) {
                e.printStackTrace();
            }
        }
        insertContractPositions(contract.getPositions(), contract.getGUID());
        for (SupplierMainType supplier : contract.getSupplier()) {
            insertSupplier(supplier);
        }
        insertSupplierToContract(contract);
    }

}
