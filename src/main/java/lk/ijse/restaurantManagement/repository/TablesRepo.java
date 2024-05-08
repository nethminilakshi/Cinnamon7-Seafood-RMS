package lk.ijse.restaurantManagement.repository;

import lk.ijse.restaurantManagement.db.DbConnection;
import lk.ijse.restaurantManagement.model.Item;
import lk.ijse.restaurantManagement.model.Tables;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TablesRepo {
    public static boolean save(Tables tables) throws SQLException {
        String sql = "INSERT INTO Tables VALUES(?, ?)";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setObject(1, tables.getTableId());
        pstm.setObject(2, tables.getDescription());



        return pstm.executeUpdate() > 0;
    }

    public static boolean update(Tables tables) throws SQLException {
        String sql = "UPDATE Tables SET description = ? WHERE tableId = ?";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, tables.getTableId());
        pstm.setObject(2, tables.getDescription());

        return pstm.executeUpdate() > 0;
    }

    public static boolean delete(String description) throws SQLException {
        String sql = "DELETE FROM Tables WHERE description = ?";

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
            String tablesId = resultSet.getString(1);
            String description = resultSet.getString(2);

            Tables tables = new Tables(tablesId, description);
            tablesList.add(tables);
        }
        return tablesList;
    }

    public static Tables searchByDescription(String description) throws SQLException {
        String sql = "SELECT * FROM Tables WHERE description = ?";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, description);
        ResultSet resultSet = pstm.executeQuery();

        Tables tables = null;

        if (resultSet.next()) {
            String tableId = resultSet.getString(1);
            String Description = resultSet.getString(2);


            tables = new Tables(tableId, Description);
        }
        return tables;
    }
}
