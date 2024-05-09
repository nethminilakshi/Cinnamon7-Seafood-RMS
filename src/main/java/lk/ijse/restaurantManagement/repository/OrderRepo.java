package lk.ijse.restaurantManagement.repository;

import com.mysql.cj.xdevapi.Session;
import lk.ijse.restaurantManagement.db.DbConnection;
import lk.ijse.restaurantManagement.model.Customer;
import lk.ijse.restaurantManagement.model.Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderRepo {
    public static String currentId() throws SQLException {
        String sql = "SELECT orderId FROM orders ORDER BY orderId desc LIMIT 1";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        if(resultSet.next()) {
            return resultSet.getString(1);
        }
        return null;
    }

    public static boolean save(Order order) throws SQLException {
        String sql = "INSERT INTO orders VALUES(?, ?, ?,?)";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);
        pstm.setString(1, order.getOrderId());
        pstm.setString(2, order.getOrderType());
        pstm.setString(3, order.getCusId());
        pstm.setString(4, order.getDate());

        return pstm.executeUpdate() > 0;
    }


    public static Order searchById(String cusId) throws SQLException {
        String sql = "SELECT * FROM Orders WHERE cusId = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, cusId);
        ResultSet resultSet = pstm.executeQuery();

        Order orders = null;

        if (resultSet.next()) {
            String orderId = resultSet.getString(1);
            String orderType = resultSet.getString(2);
            String CusId = resultSet.getString(3);
            String date = resultSet.getString(4);

            orders = new Order(orderId, orderType, CusId,date);
        }
        return orders;

    }
}
