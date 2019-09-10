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
import java.sql.Types;
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
            //TODO custom exception
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
            // TODO add custom exception and log
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
            // TODO add custom exception and log
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
            if (supplier.getINN().isPresent()) {
                statement.setString(5, supplier.getINN().get());
            } else {
                statement.setNull(5, Types.VARCHAR);
            }
            if (supplier.getShortName().isPresent()) {
                statement.setString(6, supplier.getShortName().get());
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
            if (contract.getSupplier().isPresent()) {
                statement.setInt(1, contract.getSupplier().get().getName().hashCode());
            }
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
        if (position.getOKDP().isPresent()) {
            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setString(1, position.getOKDP().get().getCode());
                statement.setString(2, "OKDP");
                statement.setString(3, position.getOKDP().get().getName());
                statement.executeUpdate();
            } catch (SQLException e) {
                if (!e.getMessage().contains("Duplicate entry")) {
                    e.printStackTrace();
                }
            } catch (NullPointerException ignored) {

            }
        }

        if (position.getOKPD().isPresent()) {
            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setString(1, position.getOKPD().get().getCode());
                statement.setString(2, "OKPD");
                statement.setString(3, position.getOKPD().get().getName());
                statement.executeUpdate();
            } catch (SQLException e) {
                if (!e.getMessage().contains("Duplicate entry")) {
                    e.printStackTrace();
                }
            } catch (NullPointerException ignored) {

            }
        }

        if (position.getOKPD2().isPresent()) {
            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setString(1, position.getOKPD2().get().getCode());
                statement.setString(2, "OKPD2");
                statement.setString(3, position.getOKPD2().get().getName());
                statement.executeUpdate();
            } catch (SQLException e) {
                if (!e.getMessage().contains("Duplicate entry")) {
                    e.printStackTrace();
                }
            } catch (NullPointerException ignored) {

            }
        }

        if (position.getOKEI().isPresent()) {
            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setString(1, position.getOKEI().get().getCode());
                statement.setString(2, "OKEI");
                statement.setString(3, position.getOKEI().get().getName());
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
                if (position.getGUID().isPresent()) {
                    statement.setString(3, position.getGUID().get());
                } else {
                    statement.setNull(3, Types.VARCHAR);
                }
                if (position.getName().isPresent()) {
                    statement.setString(4, position.getName().get());
                } else {
                    statement.setNull(4, Types.VARCHAR);
                }
                if (position.getOKDP().isPresent()) {
                    statement.setString(5, position.getOKDP().get().getCode());
                } else {
                    statement.setNull(5, Types.VARCHAR);
                }
                if (position.getOKPD().isPresent()) {
                    statement.setString(6, position.getOKPD().get().getCode());
                } else {
                    statement.setNull(6, Types.VARCHAR);
                }
                if (position.getOKPD2().isPresent()) {
                    statement.setString(7, position.getOKPD2().get().getCode());
                } else {
                    statement.setNull(7, Types.VARCHAR);
                }
                if (position.getOKEI().isPresent()) {
                    statement.setString(8, position.getOKEI().get().getCode());
                } else {
                    statement.setNull(8, Types.VARCHAR);
                }
                if (position.getQty().isPresent()) {
                    statement.setBigDecimal(9, position.getQty().get());
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
            statement.setTimestamp(2, Timestamp.valueOf(contract.getCreateDateTime()));
            statement.setDate(3, Date.valueOf(contract.getContractDate()));
            if (contract.getStartExecutionDate().isPresent()) {
                statement.setDate(4, Date.valueOf(contract.getStartExecutionDate().get()));
            } else {
                statement.setNull(4, Types.DATE);
            }
            if (contract.getEndExecutionDate().isPresent()) {
                statement.setDate(5, Date.valueOf(contract.getEndExecutionDate().get()));
            } else {
                statement.setNull(5, Types.DATE);
            }
            statement.setString(6, contract.getCustomer().getINN());
            statement.setString(7, contract.getPurchaseType().getCode());
            if(contract.getPurchaseType().getName().isPresent()) {
                //TODO add table to PurchaseType
                statement.setString(8, contract.getPurchaseType().getName().get());
            } else {
                statement.setNull(8, Types.VARCHAR);
            }

            statement.setBigDecimal(9, contract.getPrice());
            if (contract.getRubPrice().isPresent()) {
                statement.setBigDecimal(10, contract.getRubPrice().get());
            } else {
                statement.setNull(10, Types.DECIMAL);
            }

            statement.setString(11, contract.getCurrency().getCode() != null ? contract.getCurrency().getCode() :
                    contract.getCurrency().getLetterCode());

            statement.executeUpdate();
        } catch (SQLException e) {
            if (!e.getMessage().contains("Duplicate entry")) {
                e.printStackTrace();
            }
        }
        insertContractPositions(contract.getPositions(), contract.getGUID());
        if (contract.getSupplier().isPresent()) {
            insertSupplier(contract.getSupplier().get());
            insertSupplierToContract(contract);
        }
    }

}
