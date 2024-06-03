package lk.ijse.restaurantManagement.repository;

import lk.ijse.restaurantManagement.db.DbConnection;
import lk.ijse.restaurantManagement.model.Item;
import lk.ijse.restaurantManagement.model.OrderDetail;
import lk.ijse.restaurantManagement.model.Tables;
import lk.ijse.restaurantManagement.model.reservationDetails;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TablesRepo {
    public static boolean save(Tables tables) throws SQLException {
        String sql = "INSERT INTO Tables VALUES(?, ?, ?, ?)";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setObject(1, tables.getTableId());
        pstm.setObject(2, tables.getDescription());
        pstm.setObject(3,tables.getNoOfTables());
        pstm.setObject(4,tables.getNoOfSeats());

        return pstm.executeUpdate() > 0;
    }

    public static boolean update(Tables tables) throws SQLException {
        String sql = "UPDATE Tables SET description = ?, noOfTables = ?, noOfSeats = ? WHERE tableId = ?";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, tables.getDescription());
        pstm.setObject(2, tables.getNoOfTables());
        pstm.setObject(3, tables.getNoOfSeats());
        pstm.setObject(4, tables.getTableId());

        return pstm.executeUpdate() > 0;
    }

    public static boolean delete(String description) throws SQLException {
        String sql = "DELETE FROM Tables WHERE tableId = ?";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);
        pstm.setObject(1, description);

        return pstm.executeUpdate() > 0;
    }

    public static List<Tables> getAll() throws SQLException {
        String sql = "SELECT * FROM Tables";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<Tables> tablesList = new ArrayList<>();
        while (resultSet.next()) {
            String tableId = resultSet.getString(1);
            String description = resultSet.getString(2);
            int noOfTables = resultSet.getInt(3);
            int noOfSeats = resultSet.getInt(4);

            Tables tables = new Tables(tableId, description,noOfTables, noOfSeats);
            tablesList.add(tables);
        }
        return tablesList;
    }

    public static Tables searchById(String description) throws SQLException {
        String sql = "SELECT * FROM Tables WHERE tableId = ?";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, description);
        ResultSet resultSet = pstm.executeQuery();

        Tables tables = null;

        if (resultSet.next()) {
            String tableId = resultSet.getString(1);
            String Description = resultSet.getString(2);
            int noOfTables = resultSet.getInt(3);
            int noOfseats = resultSet.getInt(4);


            tables = new Tables(tableId, Description, noOfTables, noOfseats);
        }
        return tables;
    }

    public static List<String> getIds() throws SQLException {
        String sql = "SELECT tableId FROM Tables";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        List<String> codeList = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();

        while(resultSet.next()) {
            codeList.add(resultSet.getString(1));
        }
        return codeList;

    }

    public static boolean updateQty(reservationDetails rd) throws SQLException {
        String sql = "UPDATE Tables SET noOfTables = noOfTables - ? WHERE tableId = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setInt(1, rd.getReqTablesQty());
        pstm.setString(2, rd.getTableId());
        return pstm.executeUpdate() > 0;
    }

    public static boolean updateQty(List<reservationDetails> reservationDetailsList) throws SQLException {
        for (reservationDetails rd : reservationDetailsList) {
            if (!updateQty(rd)) {
                return false;
            }
        }
        return true;
    }
}
