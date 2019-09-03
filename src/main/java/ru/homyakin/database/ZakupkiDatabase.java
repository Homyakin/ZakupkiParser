package ru.homyakin.database;

import ru.homyakin.documentsinfo.ContractInfo;
import ru.homyakin.documentsinfo.subdocumentsinfo.ContractPositionInfo;
import ru.homyakin.documentsinfo.subdocumentsinfo.CurrencyInfo;
import ru.homyakin.documentsinfo.subdocumentsinfo.CustomerInfo;
import ru.homyakin.documentsinfo.subdocumentsinfo.SupplierInfo;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
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
            // TODO Auto-generated catch block
            throw new RuntimeException();
        }
    }

    private void insertCustomer(CustomerInfo customer) {
        String sql = "INSERT INTO customers (customer_INN, customer_OGRN, customer_KPP, customer_fullName,"
                + "customer_shortName) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setString(1, customer.getINN());
            statement.setString(2, customer.getOGRN());
            statement.setString(3, customer.getKPP());
            statement.setString(4, customer.getName());
            statement.setString(5, customer.getShortName());

            statement.executeUpdate();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            if (!e.getMessage().contains("Duplicate entry")) {
                e.printStackTrace();
            }
        }
    }

    private void insertCurrency(CurrencyInfo currency) {
        String sql = "INSERT INTO currencies (currency_code, currency_digitalCode, currency_name) VALUES (?, ?, ?)";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setString(1, currency.getCode() != null ? currency.getCode() :
                    currency.getLetterCode());
            statement.setString(2, currency.getDigitalCode());
            statement.setString(3, currency.getName());

            statement.executeUpdate();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            if (!e.getMessage().contains("Duplicate entry")) {
                e.printStackTrace();
            }
        }
    }

    private void insertSupplier(SupplierInfo supplier) {
        //TODO replace hashName to auto generated id
        String sql = "INSERT INTO suppliers (supplier_hashName, type, provider, nonResident, supplier_INN, "
                + "supplier_shortName, supplier_name) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, supplier.getName().hashCode());
            statement.setString(2, supplier.getType());
            statement.setInt(3, supplier.isProvider() ? 1 : 0);
            statement.setInt(4, supplier.isNonResident() ? 1 : 0);
            statement.setString(5, supplier.getINN());
            statement.setString(6, supplier.getShortName());
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

            statement.setInt(1, contract.getSupplier().getName().hashCode());
            statement.setString(2, contract.getGUID());

            statement.executeUpdate();
        } catch (SQLException e) {
            if (!e.getMessage().contains("Duplicate entry")) {
                e.printStackTrace();
            }
        }
    }

    private void insertOK(ContractPositionInfo position) {
        //TODO make different tables for each OK
        String sql = "INSERT INTO ok_info (ok_code, ok_type, ok_name) VALUES(?, ?, ?)";
        if (position.getOKDP().getCode() != null) {
            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setString(1, position.getOKDP().getCode());
                statement.setString(2, "ОКДП");
                statement.setString(3, position.getOKDP().getName());
                statement.executeUpdate();
            } catch (SQLException e) {
                if (!e.getMessage().contains("Duplicate entry")) {
                    e.printStackTrace();
                }
            } catch (NullPointerException ignored) {

            }
        }

        if (position.getOKPD().getCode() != null) {
            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setString(1, position.getOKPD().getCode());
                statement.setString(2, "ОКПД");
                statement.setString(3, position.getOKPD().getName());
                statement.executeUpdate();
            } catch (SQLException e) {
                if (!e.getMessage().contains("Duplicate entry")) {
                    e.printStackTrace();
                }
            } catch (NullPointerException ignored) {

            }
        }

        if (position.getOKPD2().getCode() != null) {
            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setString(1, position.getOKPD2().getCode());
                statement.setString(2, "ОКПД2");
                statement.setString(3, position.getOKPD2().getName());
                statement.executeUpdate();
            } catch (SQLException e) {
                if (!e.getMessage().contains("Duplicate entry")) {
                    e.printStackTrace();
                }
            } catch (NullPointerException ignored) {

            }
        }

        if (position.getOKEI().getCode() != null) {
            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setString(1, position.getOKEI().getCode());
                statement.setString(2, "����");
                statement.setString(3, position.getOKEI().getName());
                statement.executeUpdate();
            } catch (SQLException e) {
                if (!e.getMessage().contains("Duplicate entry")) {
                    e.printStackTrace();
                }
            } catch (NullPointerException ignored) {

            }
        }
    }

    private void insertContractPositions(List<ContractPositionInfo> contractPositions, String contractGUID) {
        String sql = "INSERT INTO contract_positions (contract_GUID, ordinalNumber, position_GUID, position_name, OKDP_code,"
                + "OKPD_code, OKPD2_code, OKEI_code, qty) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        for (ContractPositionInfo position : contractPositions) {

            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setString(1, contractGUID);
                statement.setInt(2, position.getOrdinalNumber());
                statement.setString(3, position.getGUID());
                statement.setString(4, position.getName());
                statement.setString(5, position.getOKDP().getCode());
                statement.setString(6, position.getOKPD().getCode());
                statement.setString(7, position.getOKPD2().getCode());
                statement.setString(8, position.getOKEI().getCode());
                statement.setBigDecimal(9, position.getQty());

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
            statement.setTimestamp(2, Timestamp.valueOf(contract.getCreateDateTime()));
            statement.setDate(3, Date.valueOf(contract.getContractDate()));
            statement.setDate(4, Date.valueOf(contract.getStartExecutionDate()));
            statement.setDate(5, Date.valueOf(contract.getEndExecutionDate()));
            statement.setString(6, contract.getCustomer().getINN());
            statement.setString(7, contract.getPurchaseType().getCode());
            statement.setString(8, contract.getPurchaseType().getName());
            statement.setBigDecimal(9, contract.getPrice());
            statement.setBigDecimal(10, contract.getRubPrice());
            statement.setString(11, contract.getCurrency().getCode() != null ? contract.getCurrency().getCode() :
                    contract.getCurrency().getLetterCode());

            statement.executeUpdate();
        } catch (SQLException e) {
            if (!e.getMessage().contains("Duplicate entry")) {
                e.printStackTrace();
            }
        }
        insertContractPositions(contract.getPositions(), contract.getGUID());
        insertSupplier(contract.getSupplier());
        insertSupplierToContract(contract);
    }

}
