package lk.ijse.restaurantManagement.repository;

import javafx.scene.control.Alert;
import lk.ijse.restaurantManagement.db.DbConnection;
import lk.ijse.restaurantManagement.model.Customer;
import lk.ijse.restaurantManagement.model.Item;
import lk.ijse.restaurantManagement.model.OrderDetail;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemRepo {
    public static List<String> getIds() throws SQLException {
        String sql = "SELECT id FROM Item";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        List<String> idList = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();

        while (resultSet.next()) {
            idList.add(resultSet.getString(1));
        }
        return idList;
    }

    public static Item searchByDescription(String description) throws SQLException {
        String sql = "SELECT * FROM Item WHERE description = ?";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, description);
        ResultSet resultSet = pstm.executeQuery();

        Item item = null;

        if (resultSet.next()) {
            String code = resultSet.getString(1);
            String name = resultSet.getString(2);
            String qtyOnHand = resultSet.getString(3);
            String unitPrice = resultSet.getString(4);
            String status = resultSet.getString(5);

            item = new Item(code, name, qtyOnHand, unitPrice, status);
        }
        return item;
    }


    private static boolean updateQty(OrderDetail od) throws SQLException {
        String sql = "UPDATE Item SET qtyOnHand = qtyOnHand - ? WHERE id = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setInt(1, od.getQty());
        pstm.setString(2, od.getItemId());

        return pstm.executeUpdate() > 0;
    }

    public static boolean update(Item item) throws SQLException {
        String sql = "UPDATE Item SET description = ?, qtyOnHand = ?, unitPrice = ?,status = ? WHERE id = ?";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, item.getDescription());
        pstm.setObject(2, item.getQtyOnHand());
        pstm.setObject(3, item.getUnitPrice());
        pstm.setObject(4, item.getStatus());
        pstm.setObject(5, item.getId());


        return pstm.executeUpdate() > 0;
    }

    public static boolean updateQty(List<OrderDetail> odList) throws SQLException {
        for (OrderDetail od : odList) {
            if (!updateQty(od)) {
                return false;
            }
        }
        return true;
    }

    public static List<Item> getAll() throws SQLException {
        String sql = "SELECT * FROM Item";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<Item> itemList = new ArrayList<>();
        while (resultSet.next()) {
            String id = resultSet.getString(1);
            String description = resultSet.getString(2);
            String qtyOnHand = resultSet.getString(3);
            String unitPrice = resultSet.getString(4);
            String status = resultSet.getString(5);

            Item item = new Item(id, description, qtyOnHand, unitPrice, status);
            itemList.add(item);
        }
        return itemList;
    }

    public static boolean delete(String description) throws SQLException {
        String sql = "DELETE FROM Item WHERE description = ?";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);
        pstm.setObject(1, description);

        return pstm.executeUpdate() > 0;
    }

    public static boolean save(Item item) throws SQLException {

        String sql = "INSERT INTO Item VALUES(?, ?, ?, ?,?)";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setObject(1, item.getId());
        pstm.setObject(2, item.getDescription());
        pstm.setObject(3, item.getQtyOnHand());
        pstm.setObject(4, item.getUnitPrice());
        pstm.setObject(5,item.getStatus());

        return pstm.executeUpdate()>0;
    }

    public static int getItemCount() throws SQLException {
        String sql = "SELECT COUNT(*) AS item_count FROM Item";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        int itemCount = 0;
        if(resultSet.next()) {
            itemCount = resultSet.getInt("item_count");
        }
        return itemCount;
    }

    public static Item searchByCode(String id) throws SQLException {
        String sql = "SELECT * FROM Item WHERE Id = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, id);
        ResultSet resultSet = pstm.executeQuery();

        Item item = null;

        if (resultSet.next()) {
            String Id = resultSet.getString(1);
            String  description = resultSet.getString(2);
            String qtyOnHand = resultSet.getString(3);
            String unitPrice= resultSet.getString(4);
            String  status= resultSet.getString(4);

            item = new Item(Id, description, qtyOnHand, unitPrice,status);
        }
        return item;
    }

    public String autoGenarateItemCode() throws SQLException {
        String sql = "SELECT id from Item order by id desc limit 1";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);


        ResultSet resultSet = pstm.executeQuery();

        if (resultSet.next()) {
            String id = resultSet.getString("id");
            String numericPart = id.replaceAll("\\D+","");
            int newId = Integer.parseInt(numericPart) + 1;
            return String.format("P%03d",newId);

        }else {
            return "p001";
        }
    }
}